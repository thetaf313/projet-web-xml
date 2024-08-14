package com.udb.m1.projet.web.xml.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/filieres")
public class FiliereController {

    @GetMapping
    public String index(Model model) {
        // call services
        return "filiere/index";
    }
}
