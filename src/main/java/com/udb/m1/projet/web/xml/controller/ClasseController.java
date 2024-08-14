package com.udb.m1.projet.web.xml.controller;

import com.udb.m1.projet.web.xml.model.Classe;
import com.udb.m1.projet.web.xml.model.Filiere;
import com.udb.m1.projet.web.xml.model.Filieres;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/classes")
public class ClasseController {

    private static final String XML_FILE = "filiere-classe.xml";

    @GetMapping
    public String index(Model model) {
        // call services
        return "classe/index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("classe", new Classe());
        return "classe/addClasse";
    }

    @PostMapping
    public String create(@ModelAttribute Classe classe) {
        //
        Filiere f = new Filiere(Long.parseLong(classe.getFiliere().getLibelle().split("-")[0]),
                classe.getFiliere().getLibelle().split("-")[1], null);
        classe.setFiliere(f);

        // test
        List<Filiere> filieres = new ArrayList<>();
        filieres.add(f);

        Filieres rootFilieres = new Filieres();
        rootFilieres.setFilieres(filieres);

        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(Filieres.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(rootFilieres, System.out);
            jaxbMarshaller.marshal(rootFilieres, new File(XML_FILE));

        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        return "classe/addClasse";
    }
}
