package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.FollowRepository;
import com.makersacademy.acebook.repository.PostRepository;
import com.makersacademy.acebook.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@RestController
public class BirdFeedController {
    @Autowired
    PostRepository postRepository;
    @Autowired
    FollowRepository followRepository;
    @Autowired
    UserRepository userRepository;
    @GetMapping("/birdfeed")
    public ModelAndView index(HttpSession session) {

        String username = (String) session.getAttribute("username");

        Optional<User> findingUser = userRepository.findUserByUsername(username);
        User currentUser = findingUser.get();
        Long userId = currentUser.getId();

        ModelAndView birdFeed = new ModelAndView("/birdfeed");

        List<Post> listOfPosts = postRepository.findAllOrderByIdDesc();
        List<Long> followedIds = followRepository.findFollowingIdsByFollowerId(userId);

        birdFeed.addObject("userId", userId);
        birdFeed.addObject("followedIds", followedIds);
        birdFeed.addObject("listOfPosts", listOfPosts);
        birdFeed.addObject("post", new Post());
        return birdFeed;
    }
    @GetMapping("/")
    public RedirectView birdfeed() {
        return new RedirectView("/birdfeed");
    }
}