package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
public class BirdFeedController {
    @Autowired
    PostRepository postRepository;
    @GetMapping("/birdfeed")
    public ModelAndView index() {
        ModelAndView birdFeed = new ModelAndView("/birdfeed");
        Iterable<Post> listOfPosts = postRepository.findAll();
        birdFeed.addObject("listOfPosts", listOfPosts);
        birdFeed.addObject("post", new Post());
        return birdFeed;
    }
    @GetMapping("/")
    public RedirectView birdfeed() {
        return new RedirectView("/birdfeed");
    }

    @GetMapping("/map")
    public ModelAndView map() {
        ModelAndView map = new ModelAndView("maptest");
        return map;

    }
}

