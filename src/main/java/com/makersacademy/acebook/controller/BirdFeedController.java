package com.makersacademy.acebook.controller;
import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.BirdRepository;
import com.makersacademy.acebook.repository.PostRepository;
import com.makersacademy.acebook.controller.DTOs.PostView;
import com.makersacademy.acebook.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
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
    @Autowired
    UserRepository userRepository;


    @GetMapping("/birdfeed")
    public ModelAndView index(HttpSession session) {
        ModelAndView birdFeed = new ModelAndView("/birdfeed");

        List<PostView> listOfPosts = postRepository.postsJoinBird();
        String username = (String) session.getAttribute("username");
        User currentUser = userRepository.findUserByUsername(username)
                .orElse(null);

        birdFeed.addObject("listOfPosts", listOfPosts);
        birdFeed.addObject("post", new Post());
        birdFeed.addObject("currentUser", currentUser);
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

