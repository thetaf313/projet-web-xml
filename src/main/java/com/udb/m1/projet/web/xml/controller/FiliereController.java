package com.udb.m1.projet.web.xml.controller;

import com.udb.m1.projet.web.xml.model.Filiere;
import com.udb.m1.projet.web.xml.service.FiliereServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/filieres")
public class FiliereController {

    private final FiliereServiceImpl filiereService;

    public FiliereController(FiliereServiceImpl filiereService) {
        this.filiereService = filiereService;
    }


    @GetMapping
    public String index(Model model) {
        List<Filiere> filieres = filiereService.getAllFilieres();
        model.addAttribute("filieres", filieres);
        return "filiere/index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("filiere", new Filiere());
        return "filiere/add-filiere";
    }

    @PostMapping
    public String create(@ModelAttribute Filiere filiere) {
        filiereService.addFiliere(filiere);
        return "redirect:/filieres";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Filiere filiere = filiereService.getFiliere(id);
        model.addAttribute("filiere", filiere);
        return "filiere/edit-filiere";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Filiere filiere) {
        filiereService.updateFiliere(id, filiere);
        return "redirect:/filieres";
    }

    @GetMapping("/view/{id}")
    public String show(@PathVariable Long id, Model model) {
        Filiere filiere = filiereService.getFiliere(id);
        model.addAttribute("filiere", filiere);
        return "filiere/show-filiere";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        filiereService.deleteFiliere(id);
        return "redirect:/filieres";
    }
}
