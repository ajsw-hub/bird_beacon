package com.makersacademy.acebook.controller;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UsersController {

    @GetMapping("/users/after-login")
    public ModelAndView afterLogin() {
        DefaultOidcUser principal = (DefaultOidcUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        ModelAndView afterLogin = new ModelAndView("/birdfeed");

        return afterLogin;
    }
}