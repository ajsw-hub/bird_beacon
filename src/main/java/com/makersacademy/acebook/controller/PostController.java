package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class PostController {
    @Autowired
    PostRepository postRepository;

    @PostMapping("/new_sighting")
    public RedirectView create (@ModelAttribute Post post){
        return new RedirectView("/");
    }




}
