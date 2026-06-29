package com.makersacademy.acebook.controller;

import org.springframework.ui.Model;
import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.PostRepository;
import com.makersacademy.acebook.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/profile")
    public ModelAndView showMyPostHistory(HttpSession session) {

        String username = session.getAttribute("username").toString();
        User user = userRepository.findUserByUsername(username).get();

        List<Post> userPosts = postRepository.findByPosteridOrderByIdDesc(user.getId());

        ModelAndView modelAndView = new ModelAndView("/profile");
        modelAndView.addObject("userPosts", userPosts);
        modelAndView.addObject("user", user);

        return modelAndView;
    }

    @GetMapping("/profile/edit-page")
    public String showMyEditPage(Model model, HttpSession session) {

        String username = session.getAttribute("username").toString();
        User user = userRepository.findUserByUsername(username).get();

        model.addAttribute("user", user);

        return "edit-page";
    }

    @PostMapping("/profile/edit-page")
    public RedirectView editUser(@ModelAttribute("user") User formUser,
                                 @RequestParam("imageFile") MultipartFile image,
                                 HttpSession session) throws IOException {

        String username = session.getAttribute("username").toString();
        User currentUser = userRepository.findUserByUsername(username).get();

        currentUser.setBio(formUser.getBio());
        currentUser.setDateofbirth(formUser.getDateofbirth());

        Path uploadDir = Paths.get("images");
        Files.createDirectories(uploadDir);

        if (!image.isEmpty()) {
            String filename = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            Path filePath = uploadDir.resolve(filename);

            Files.copy(
                    image.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            currentUser.setProfilepicture(filename);
        }

        userRepository.save(currentUser);

        session.setAttribute("user", currentUser);

        return new RedirectView("/profile");
    }
}
