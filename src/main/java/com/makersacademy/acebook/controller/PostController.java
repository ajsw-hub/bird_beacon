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
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@RestController
public class PostController {
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/new_sighting")
    public RedirectView create (@ModelAttribute Post post, HttpSession session, @RequestParam String redirect){

            String username = (String) session.getAttribute("username");

            Optional<User> findingUser = userRepository.findUserByUsername(username);
            User currentUser = findingUser.get();
            System.out.println("currentUser: " + currentUser);

            post.setPosterid(currentUser.getId());

            postRepository.save(post);

        return new RedirectView(redirect);
    }
}
