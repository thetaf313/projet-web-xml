package com.udb.m1.projet.web.xml.service;

import com.udb.m1.projet.web.xml.model.Classe;

import java.util.List;

public interface ClasseService {
    List<Classe> getAllClasses();
    Classe getClasseById(Long classeId);
    List<Classe> getAllClassesByFiliere(Long filiereId);
    void addClasseToFiliere(Long filiereId, Classe classe);
    void updateClasse(Long filiereId, Classe newClasse);
    boolean deleteClasse(Long classeId);
}
