package com.makersacademy.acebook.controller;


import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@RestController
public class UsersController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/users/after-login")
    public RedirectView afterLogin(HttpSession session) {
        DefaultOidcUser principal = (DefaultOidcUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        System.out.println("PRINCIPAL: " + principal);

        String username = (String) principal.getAttributes().get("https://birdbeacon.com/username");
        String email = (String) principal.getAttributes().get("email");

        System.out.println("username: " + username);
        System.out.println("email: " + email);

        userRepository
                .findUserByUsername(username)
                .orElseGet(() -> userRepository.save(new User(username, email)));

        Optional<User> findingUser = userRepository.findUserByUsername(username);
        User currentUser = findingUser.get();

        session.setAttribute("currentUser", currentUser);
        session.setAttribute("username", username);

        return new RedirectView("/birdfeed");
    }
}