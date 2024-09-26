package com.udb.m1.projet.web.xml.controller;

import com.udb.m1.projet.web.xml.model.Filiere;
import com.udb.m1.projet.web.xml.service.FiliereServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        if (filieres.isEmpty()) {
            model.addAttribute("noFiliereFound",
                    "Aucune filiere trouvée");
        }
        model.addAttribute("filieres", filieres);
        return "filiere/index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("filiere", new Filiere());
        return "filiere/add-filiere";
    }

    @PostMapping
    public String create(@ModelAttribute Filiere filiere,
                         RedirectAttributes redirectAttributes) {
        try {
            filiereService.addFiliere(filiere);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Filière ajoutée avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Erreur lors de l'ajout de la filière.");
        }
        return "redirect:/filieres";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Filiere filiere = filiereService.getFiliere(id);
        model.addAttribute("filiere", filiere);
        return "filiere/edit-filiere";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute Filiere filiere,
                         RedirectAttributes redirectAttributes) {
        try {
            filiereService.updateFiliere(id, filiere);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Filiere mise à jour avec succés.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Erreur lors de la mise à jour de la filiere.");
        }
        return "redirect:/filieres";
    }

    @GetMapping("/view/{id}")
    public String show(@PathVariable Long id, Model model) {
        Filiere filiere = filiereService.getFiliere(id);
        model.addAttribute("filiere", filiere);
        return "filiere/show-filiere";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            filiereService.deleteFiliere(id);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Filiere supprimée avec succes.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Erreur lors de la suprresion de la filiere.");
        }
        return "redirect:/filieres";
    }
}
