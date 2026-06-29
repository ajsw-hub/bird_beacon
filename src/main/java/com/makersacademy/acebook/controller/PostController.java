package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.PostRepository;
import com.makersacademy.acebook.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@RestController
public class PostController {
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/new_sighting")
    public RedirectView create (@ModelAttribute Post post, @RequestParam("user_bird") MultipartFile user_bird, HttpSession session) throws IOException {
            //get the username of the user from the current session
            String username = (String) session.getAttribute("username");
            //use that username to search the database in order to get the userid from db and assign results to findingUser
            Optional<User> findingUser = userRepository.findUserByUsername(username);
            //findingUser should only have one User instance in the optional type object so lets assign it to a single User type instance and make that User Instance the currentUser
            User currentUser = findingUser.get();
            // now we are using the id we just got in user to assign it to the post, now we know who posted this post.
            post.setPosterId(currentUser.getId());

            //setting the upload directory to uploads/postimg
            Path uploadDir = Paths.get("uploads/postimg");
            //if that directory doesent exist create it, if it does no worries.
            Files.createDirectories(uploadDir);
            //if the name of the user_image from the form is not empty...
            if (!user_bird.isEmpty()){
                //create a filename using 'uuid-theOriginalNameOfTheUploadedFile'
                String filename = UUID.randomUUID() + "-" + user_bird.getOriginalFilename();
                //this line is joining the filepath of uploads/postimg with the name of the file we created above.
                Path filePath = uploadDir.resolve(filename);
                //this line is gettign the actual image file, setting the filepath we defined with the location we want it to go and the file name and copy it in there and replace it if it exists with the same name.
                Files.copy(
                        user_bird.getInputStream(),
                        filePath,
                        StandardCopyOption.REPLACE_EXISTING
                );
                //simply setting the file name we created with uuid-original uploaded file name to the post under user_iamge column
                post.setUser_img(filename);

            }

            postRepository.save(post);
        return new RedirectView("/");
    }
}
