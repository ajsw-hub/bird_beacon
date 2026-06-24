package com.makersacademy.acebook.controller;


import ch.qos.logback.core.model.Model;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.userRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RestController
public class UsersController {
    @Autowired
    userRepository userRepository;

    @GetMapping("/users/after-login")
    public ModelAndView afterLogin(HttpSession session) {
        DefaultOidcUser principal = (DefaultOidcUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        String username = (String) principal.getAttributes().get("https://birdbeacon.com/username");
        String email = (String) principal.getAttributes().get("email");
        userRepository
                .findUserByUsername(username)
                .orElseGet(() -> userRepository.save(new User(username, email)));
        Optional<User> findingUser = userRepository.findUserByUsername(username);
        User currentUser = findingUser.get();
        session.setAttribute("currentUser", currentUser);
        ModelAndView afterLogin = new ModelAndView("index");

        return afterLogin;
    }
}