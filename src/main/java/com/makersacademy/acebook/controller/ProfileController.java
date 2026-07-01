package com.makersacademy.acebook.controller;

import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@RestController
public class ProfileController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/my-aviary")
    public ModelAndView showMyPostHistory(HttpSession session){

        String username = session.getAttribute("username").toString();

//        Optional<User> findingUser = userRepository.findUserByUsername(username);
        User user = userRepository.findUserByUsername(username).get();

        List<Post> userPosts = postRepository.findByPosterId(user.getId());

        ModelAndView modelAndView = new ModelAndView("/my-aviary");

        modelAndView.addObject("userPosts", userPosts);
        modelAndView.addObject("user", user);
        modelAndView.addObject("post", new Post());

        return modelAndView;
    }

    @GetMapping("/my-aviary/edit-page")
    public ModelAndView showMyEditPage(Model model, HttpSession session) {
        ModelAndView editPage = new ModelAndView("edit-page");
        String username = session.getAttribute("username").toString();
        User user = userRepository.findUserByUsername(username).get();
        editPage.addObject("user", user);
        return editPage;
    }

    @PostMapping("/my-aviary/edit-page")
    public RedirectView editUser(@ModelAttribute("user") User formUser,
                                 @RequestParam("imageFile") MultipartFile image,
                                 HttpSession session) throws IOException {

        String username = session.getAttribute("username").toString();
        User currentUser = userRepository.findUserByUsername(username).get();

        if (formUser.getBio() != null && formUser.getBio().length() > 300) {
            return new RedirectView("/my-aviary/edit-page?error=bioLength");
        }

        if (formUser.getDateofbirth() != null &&
                formUser.getDateofbirth().isAfter(LocalDate.now())) {
            return new RedirectView("/my-aviary/edit-page?error=dob");
        }

        currentUser.setBio(formUser.getBio());

        if (formUser.getDateofbirth() != null) {
            currentUser.setDateofbirth(formUser.getDateofbirth());
        }

        Path uploadDir = Paths.get("/profileimg");
        Files.createDirectories(uploadDir);

        if (!image.isEmpty()) {
            String contentType = image.getContentType();

            if (contentType == null ||
                    (!contentType.equals("image/png") &&
                            !contentType.equals("image/jpeg") &&
                            !contentType.equals("image/jpg"))) {

                return new RedirectView("/my-aviary/edit-page?error=invalidImage");
            }

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

        return new RedirectView("/my-aviary");
    }
}
