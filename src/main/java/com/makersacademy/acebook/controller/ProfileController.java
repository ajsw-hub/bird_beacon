package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Follow;
import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.FollowRepository;
import com.makersacademy.acebook.repository.PostRepository;
import com.makersacademy.acebook.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @Autowired
    FollowRepository followRepository;

    @GetMapping("/my-aviary")
    public ModelAndView showMyPostHistory(HttpSession session){

        String username = session.getAttribute("username").toString();

//        Optional<User> findingUser = userRepository.findUserByUsername(username);
        User user = userRepository.findUserByUsername(username).get();

//        User currentUser = user.get();
        System.out.println("currentUser: " + user);

        List<Post> userPosts = postRepository.findByPosteridOrderByIdDesc(user.getId());

        ModelAndView modelAndView = new ModelAndView("/my-aviary");

        modelAndView.addObject("userPosts", userPosts);
        modelAndView.addObject("user", user);
        modelAndView.addObject("post", new Post());

        return modelAndView;

    }

    @GetMapping("/their-aviary/{userId}")
    public ModelAndView selectedUser(@PathVariable String userId, HttpSession session) {


        Optional<User> chosenUser;

        try {
            chosenUser = userRepository.findById(Long.valueOf(userId));
        } catch(Exception e) {
            if (userId.toLowerCase().contains("drop") ||
                    userId.toLowerCase().contains("delete") ||
                    userId.toLowerCase().contains("insert") ||
                    userId.toLowerCase().contains("update")){
                return new ModelAndView("/sql");
            }
            chosenUser = userRepository.findUserByUsername(userId);
        }

        Boolean isFollowingBool = false;
        Boolean mutualFollowBool = false;

        if (chosenUser.isPresent()) {
            User user = (User) chosenUser.get();

            String username = session.getAttribute("username").toString();
            User currentUser = userRepository.findUserByUsername(username).get();

            if (user.getId() == currentUser.getId()) {
                return new ModelAndView("redirect:/my-aviary");
            }

            Optional<Follow> isFollowing = followRepository.findAllByFolloweridAndFollowingid(currentUser.getId(), user.getId());
            Optional<Follow> mutualFollow = followRepository.findAllByFolloweridAndFollowingid(user.getId(), currentUser.getId());
            if (isFollowing.isPresent()) {
                 isFollowingBool = true;
                 if (mutualFollow.isPresent()) {
                     mutualFollowBool = true;
                 }
            }

            ModelAndView theirAviary = new ModelAndView("their-aviary");
            theirAviary.addObject("user", user);
            theirAviary.addObject("isFollowingBool", isFollowingBool);
            theirAviary.addObject("mutualFollowBool", mutualFollowBool);

            return theirAviary;
        } else {
            return new ModelAndView("/userNotFound");
        }




    }




}
