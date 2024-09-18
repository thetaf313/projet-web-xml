package com.udb.m1.projet.web.xml.model;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "filiere")
@XmlType(propOrder = { "libelle", "classes" })  // Spécifie l'ordre des éléments
public class Filiere {

    private long id;
    private String libelle;
    private List<Classe> classes = new ArrayList<>();


    @XmlAttribute
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @XmlElement
    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @XmlElementWrapper(name = "classes")  // Crée une balise <classes>
    @XmlElement(name = "classe")  // Pour chaque classe, une balise <classe> sera générée
    public List<Classe> getClasses() {
        return classes;
    }

    public void setClasses(List<Classe> classes) {
        this.classes = classes;
    }

    public void addClasse(Classe classe) {
        this.classes.add(classe);
        classe.setFiliere(this); // Maintient la relation bidirectionnelle côté Java sans l'exporter
    }
}
