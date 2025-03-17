package de.uniba.spring.firestoreexample.controller;

import de.uniba.spring.firestoreexample.firestore.FireStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping(value = "/")
public class MainController {

    private final FireStoreService fireStoreService;

    @Autowired
    MainController(FireStoreService fireStoreService) {
        this.fireStoreService = fireStoreService;
    }

    @GetMapping
    public String getHome(Model model) {
        return "index";
    }

    @PostMapping
    public String processRequest(Model model) {

        log.info("Process Firestore Request!");
        fireStoreService.fireStoreAction();

        return "redirect:/";
    }
}
