package com.udb.m1.projet.web.xml.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@XmlRootElement(name = "filieres")
public class Filieres {

    private List<Filiere> filieres;

    public Filieres() {
        this.filieres = new ArrayList<>();
    }

    @XmlElement(name = "filiere")
    public List<Filiere> getFilieres() {
        return filieres;
    }

    public void setFilieres(List<Filiere> filieres) {
        this.filieres = filieres;
    }
}
