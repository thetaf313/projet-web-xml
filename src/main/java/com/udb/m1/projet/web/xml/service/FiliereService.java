package com.udb.m1.projet.web.xml.service;

import com.udb.m1.projet.web.xml.model.Filiere;

import java.util.List;

public interface FiliereService {
    List<Filiere> getAllFilieres();
    Filiere getFiliere(Long filiereId);
    void addFiliere(Filiere filiere);
    void updateFiliere(Long filiereId, Filiere newFiliere);
    boolean deleteFiliere(Long filiereId);
}
