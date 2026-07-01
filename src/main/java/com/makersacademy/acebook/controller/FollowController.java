package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Follow;
import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.FollowRepository;
import com.makersacademy.acebook.repository.PostRepository;
import com.makersacademy.acebook.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@RestController
public class FollowController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    FollowRepository followRepository;

    @PostMapping("/follow-unfollow/{followingId}")
    public ModelAndView followUnfollow(HttpSession session, @PathVariable Long followingId) {

        String username = session.getAttribute("username").toString();
        User currentUser = userRepository.findUserByUsername(username).get();

        Follow followCheck = new Follow();

        Optional<Follow> isFollowing = followRepository.findAllByFolloweridAndFollowingid(currentUser.getId(), followingId);

        if (isFollowing.isPresent()) {
            System.out.println("INFO: ");
            System.out.println(currentUser.getId().getClass());
            System.out.println(followingId.getClass());
            followRepository.deleteByFolloweridAndFollowingid(currentUser.getId(), followingId);
        } else {
            followCheck.setFollowerid(currentUser.getId());
            followCheck.setFollowingid(followingId);
            followRepository.save(followCheck);
        }

        return new ModelAndView("redirect:/their-aviary/" + followingId);
    }


}
