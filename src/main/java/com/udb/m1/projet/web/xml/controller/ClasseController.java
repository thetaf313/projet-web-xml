package com.udb.m1.projet.web.xml.controller;

import com.udb.m1.projet.web.xml.model.Classe;
import com.udb.m1.projet.web.xml.model.Filiere;
import com.udb.m1.projet.web.xml.service.ClasseServiceImpl;
import com.udb.m1.projet.web.xml.service.FiliereServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/classes")
public class ClasseController {

    private final ClasseServiceImpl classeService;
    private final FiliereServiceImpl filiereService;

    public ClasseController(ClasseServiceImpl classeService, FiliereServiceImpl filiereService) {
        this.classeService = classeService;
        this.filiereService = filiereService;
    }


    @GetMapping
    public String index(Model model) {
        List<Classe> classes = classeService.getAllClasses();
        model.addAttribute("classes", classes);
        return "classe/index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("classe", new Classe());

        // Récupérer la liste des filières disponibles
        List<Filiere> filieres = filiereService.getAllFilieres();
        model.addAttribute("filieres", filieres);
        return "classe/add-classe";
    }

    @PostMapping
    public String create(@ModelAttribute Classe classe) {
        classeService.addClasseToFiliere(classe.getFiliere().getId(), classe);
        return "redirect:/classes";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Classe classe = classeService.getClasseById(id);
        Filiere filiere = filiereService.getFiliere(classe.getFiliere().getId());
        List<Filiere> filieres = filiereService.getAllFilieres();
        model.addAttribute("classe", classe);
        model.addAttribute("filiere", filiere);
        model.addAttribute("filieres", filieres);

        return "classe/edit-classe";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Classe classe) {
        classeService.updateClasse(id, classe);
        return "redirect:/classes";
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        Classe classe = classeService.getClasseById(id);
        Filiere filiere = filiereService.getFiliere(classe.getFiliere().getId());

        model.addAttribute("classe", classe);
        model.addAttribute("filiere", filiere);

        return "classe/show-classe";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        classeService.deleteClasse(id);
        return "redirect:/classes";
    }
}
