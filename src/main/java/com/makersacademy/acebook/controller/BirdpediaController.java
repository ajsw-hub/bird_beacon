package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Bird;
import com.makersacademy.acebook.repository.BirdRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
public class BirdpediaController {

    @Autowired
    BirdRepository birdRepository;

    @GetMapping("/birdpedia")
    public ModelAndView birdpedia(HttpSession session){
        Iterable<Bird> allBirds = birdRepository.findAllOrderByName();
        ModelAndView birdpedia = new ModelAndView("birdpedia");
        birdpedia.addObject("allBirds", allBirds);
        return birdpedia;

    }

    @GetMapping("/birdpedia/")
    public RedirectView birdpedia(){
        return new RedirectView("/birdpedia");
    }

    @GetMapping("/birdpedia/{birdId}")
    public ModelAndView chosenBirdPage(HttpSession session, @PathVariable String birdId){
        Optional<Bird> chosenBird;

        try {
            chosenBird = birdRepository.findById((Long.valueOf(birdId)));
        } catch (Exception e) {
            if (birdId.toLowerCase().contains("drop") || birdId.toLowerCase().contains("delete") || birdId.toLowerCase().contains("insert") || birdId.toLowerCase().contains("update")){
                return new ModelAndView("/sql");
            }
            birdId = birdId.replaceAll("-", " ");
            chosenBird = birdRepository.findByName(birdId);
        }

        if(chosenBird.isPresent()){
            String birdRarity = "";

            switch (chosenBird.get().getRarity()){
                case 1:
                    birdRarity = "Common";
                    break;
                case 2:
                    birdRarity = "Uncommon";
                    break;
                case 3:
                    birdRarity = "Rare";
                    break;
                case 4:
                    birdRarity = "Epic";
                    break;
                case 5:
                    birdRarity = "Legendary";
                    break;
            }

            ModelAndView birdPage = new ModelAndView("/birdpage");
            birdPage.addObject("chosenBird", chosenBird.get());
            birdPage.addObject("birdRarity", birdRarity);
            return birdPage;
        } else {
            return new ModelAndView("/birdNotFound");
        }
    }
    @GetMapping("/birds/search")
    @ResponseBody
    public List<Bird> searchBirds(@RequestParam String q) {
        return birdRepository.findTop5ByNameContainingIgnoreCaseOrderByNameAsc(q);

    }
}