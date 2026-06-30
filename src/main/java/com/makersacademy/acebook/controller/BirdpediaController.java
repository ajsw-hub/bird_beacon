package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Bird;
import com.makersacademy.acebook.repository.BirdRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.w3c.dom.ranges.Range;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
public class BirdpediaController {

    @Autowired
    BirdRepository birdRepository;

    @GetMapping("/birdpedia")
    public ModelAndView birdpedia(HttpSession session){

        Iterable<Bird> allBirds = birdRepository.findAllOrderByName();
        Integer countOfBirds = birdRepository.countBirds();

        ModelAndView birdpedia = new ModelAndView("birdpedia");

        ArrayList<Bird> theBirds;

        String[] alphabet = new String[26];
        alphabet[0] = "A";
        alphabet[1] = "B";
        alphabet[2] = "C";
        alphabet[3] = "D";
        alphabet[4] = "E";
        alphabet[5] = "F";
        alphabet[6] = "G";
        alphabet[7] = "H";
        alphabet[8] = "I";
        alphabet[9] = "J";
        alphabet[10] = "K";
        alphabet[11] = "L";
        alphabet[12] = "M";
        alphabet[13] = "N";
        alphabet[14] = "O";
        alphabet[15] = "P";
        alphabet[16] = "Q";
        alphabet[17] = "R";
        alphabet[18] = "S";
        alphabet[19] = "T";
        alphabet[20] = "U";
        alphabet[21] = "V";
        alphabet[22] = "W";
        alphabet[23] = "X";
        alphabet[24] = "Y";
        alphabet[25] = "Z";



        List<List<Bird>> alphabeticalBirdsList = new ArrayList<>();

        ArrayList<Long> htmlBirdIds = new ArrayList<Long>();

//
//
//
//
//
//
//
//
//
//
//

        ArrayList<String> expression1 = new ArrayList<String>();
        ArrayList<String> expression2 = new ArrayList<String>();
        ArrayList<String> expression3 = new ArrayList<String>();
        ArrayList<String> expression4 = new ArrayList<String>();


//
//
//
//
//
//
//
//
//
//
//

        for(String letter : alphabet){

            List<Bird> listofBirds = new ArrayList<>();

            for(Bird bird : allBirds){


                if(Objects.equals(bird.getName().charAt(0), letter.charAt(0))){

                    listofBirds.add(bird);
                    htmlBirdIds.add((Long) bird.getId());

                    expression1.add("const bird_" + bird.getId() + "_hover"+ " = document.getElementById(\"bird_" + bird.getId() + "\");" );
                    expression2.add("const bird_" + bird.getId() + "_popup"+ " = document.getElementById(\"bird_" + bird.getId() + "_popup\");" );
                    expression3.add("bird_" + bird.getId() + "_hover" +".addEventListener" +"('mouseenter',() => {" + "bird_" + bird.getId() + "_popup" +".style.display = 'block'; });");
                    expression4.add("bird_" + bird.getId() + "_hover" +".addEventListener" +"('mouseleave',() => {" + "bird_" + bird.getId() + "_popup" +".style.display = 'none'; });");


                }


            }

            alphabeticalBirdsList.add(listofBirds);

        }

        List<Integer> countOfBirdsList = IntStream.range(0,countOfBirds).boxed().toList();

        System.out.println(expression1.get(2));
        System.out.println(expression2.get(2));
        System.out.println(expression3.get(2));
        System.out.println(expression4.get(2));

        String bird_81 = "bird_81";
        String bird_22 = "bird_22";

        birdpedia.addObject("allBirds", allBirds);
        birdpedia.addObject("alphabet", alphabet);
        birdpedia.addObject("alphabeticalBirdsList", alphabeticalBirdsList);

        birdpedia.addObject("bird_81", bird_81);
        birdpedia.addObject("bird_22", bird_22);

        birdpedia.addObject("countOfBirdsList",countOfBirdsList);
        birdpedia.addObject("expression1",expression1);
        birdpedia.addObject("expression2",expression2);
        birdpedia.addObject("expression3",expression3);
        birdpedia.addObject("expression4",expression4);



        birdpedia.addObject("htmlBirdIds", htmlBirdIds);




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

}
