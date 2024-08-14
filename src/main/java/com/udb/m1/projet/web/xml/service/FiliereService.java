package com.udb.m1.projet.web.xml.service;

import com.udb.m1.projet.web.xml.model.Filiere;
import org.springframework.stereotype.Service;

import java.util.List;


public interface FiliereService {

    Filiere addFiliere(Filiere filiere);
    List<Filiere> getFilieres();
    Filiere updateFiliere(Filiere filiere);
    void deleteFiliere(Filiere filiere);
}
