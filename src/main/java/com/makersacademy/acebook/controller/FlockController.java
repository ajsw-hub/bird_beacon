package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Follow;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.FollowRepository;
import com.makersacademy.acebook.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FlockController {

    @Autowired
    FollowRepository followRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/my-flock")
    public ModelAndView myFlock(HttpSession session) {

        ModelAndView myFlockView = new ModelAndView("/my-flock");
        String username = session.getAttribute("username").toString();
        User currentUser = userRepository.findUserByUsername(username).get();

        System.out.println("My flock: " + followRepository.findFollowingIdsByFollowerId(currentUser.getId()));
        List<Long> myFlock = followRepository.findFollowingIdsByFollowerId(currentUser.getId());

        ArrayList<User> myFlockUsers = new ArrayList<User>();
        for (Long user: myFlock) {
            myFlockUsers.add(userRepository.findById(user).get());
        }

        myFlockView.addObject("myFlockUsers", myFlockUsers);
        System.out.println("My flock: " + myFlockUsers);

        return myFlockView;
    }
}
