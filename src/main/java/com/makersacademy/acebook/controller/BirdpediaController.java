package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Bird;
import com.makersacademy.acebook.repository.BirdRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class BirdpediaController {

    @Autowired
    BirdRepository birdRepository;

    @GetMapping("/birdpedia")
    public ModelAndView birdpedia(HttpSession session){

        Iterable<Bird> allBirds = birdRepository.findAllOrderByName();

        ModelAndView birdpedia = new ModelAndView("birdpedia");

        birdpedia.addObject("allBirds", allBirds);

        for (Bird bird : allBirds) {
            System.out.println(bird.getName());
        }

        return birdpedia;

    }

}
