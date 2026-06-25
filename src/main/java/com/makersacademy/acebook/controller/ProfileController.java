package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.PostRepository;
import com.makersacademy.acebook.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RestController
public class ProfileController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/profile")
    public ModelAndView showMyPostHistory(HttpSession session){

        String username = session.getAttribute("username").toString();

        Optional<User> findingUser = userRepository.findUserByUsername(username);
        User currentUser = findingUser.get();

        List<Post> userPosts = postRepository.findByPosterid(currentUser.getId());

        ModelAndView modelAndView = new ModelAndView("/profile");

        modelAndView.addObject("userPosts", userPosts);

        return modelAndView;

    }

}
