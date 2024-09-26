package com.udb.m1.projet.web.xml.controller;

import com.udb.m1.projet.web.xml.model.Classe;
import com.udb.m1.projet.web.xml.model.Filiere;
import com.udb.m1.projet.web.xml.service.ClasseServiceImpl;
import com.udb.m1.projet.web.xml.service.FiliereServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

        if (classes.isEmpty()) {
            model.addAttribute("noClasseFound",
                    "Aucune classe trouvée !");
        }

        model.addAttribute("classes", classes);
        return "classe/index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("classe", new Classe());

        // Récupérer la liste des filières disponibles
        List<Filiere> filieres = filiereService.getAllFilieres();
        if (filieres.isEmpty()) {
            model.addAttribute("warningMessage",
                    "Aucune filiere trouvée. " +
                            "Veuillez ajouter au moins une filiere !");
        }
        model.addAttribute("filieres", filieres);
        return "classe/add-classe";
    }

    @PostMapping
    public String create(@ModelAttribute Classe classe,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (classe.getFiliere().getId() == 0) {
            model.addAttribute("errorMessage",
                    "Vous devez specifier une filiere");
            return "classe/add-classe";
        }
        try {
            classeService.addClasseToFiliere(classe.getFiliere().getId(), classe);
            redirectAttributes.addFlashAttribute("successMessage",
                    "CLasse ajoutée avec succés.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Erreur lors de l'ajout de la classe.");
        }
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
    public String update(@PathVariable Long id,
                         @ModelAttribute Classe classe,
                         RedirectAttributes redirectAttributes) {
        try {
            classeService.updateClasse(id, classe);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Classe mise à jour avec succés.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Erreur lors de la mise à jour.");

        }
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
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            classeService.deleteClasse(id);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Classe supprimée avec succès.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Erreur lors de la suppression de la classe");
        }
        return "redirect:/classes";
    }
}
