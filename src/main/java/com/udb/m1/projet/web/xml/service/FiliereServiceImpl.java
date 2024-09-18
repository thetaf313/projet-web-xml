package com.udb.m1.projet.web.xml.service;

import com.udb.m1.projet.web.xml.model.Classe;
import com.udb.m1.projet.web.xml.model.Filiere;
import com.udb.m1.projet.web.xml.model.Filieres;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FiliereServiceImpl implements FiliereService {

    private final XMLService xmlService;
    private final IDGeneratorService generatorService;

    public FiliereServiceImpl(XMLService xmlService, IDGeneratorService generatorService) {
        this.xmlService = xmlService;
        this.generatorService = generatorService;
    }

    @Override
    public List<Filiere> getAllFilieres() {
        // Récupérer toutes les filières depuis le fichier xml
        Filieres filieres = xmlService.load();

        if (filieres.getFilieres() == null) {
            filieres.setFilieres(new ArrayList<>());
        }

        return filieres.getFilieres();
    }

    @Override
    public Filiere getFiliere(Long filiereId) {
        // Récupérer toutes les filières depuis le fichier xml
        Filieres filieres = xmlService.load();

        if (filieres == null || filieres.getFilieres() == null) {
            throw new RuntimeException("Erreur lors de la récupération des données !");
        }

        return filieres.getFilieres().stream()
                .filter(filiere -> filiere.getId() == filiereId)  // Utiliser equals() pour comparer les Long
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "Aucune filière trouvée avec l'id " + filiereId)
                );
    }

    @Override
    public void addFiliere(Filiere filiere) {
        // Récupérer toutes les filières depuis le fichier xml
        Filieres filieres = xmlService.load();

        // Si le fichier est vide, initialiser Filieres
        if (filieres == null) {
            filieres = new Filieres();
        }

        // Initialiser la liste des filières
        if (filieres.getFilieres() == null) {
            filieres.setFilieres(new ArrayList<>());
        }

        // Générer un nouvel ID pour la filière
        int newId = generatorService.generateId("filiere");
        if (newId != -1) {
            filiere.setId(newId);
            filieres.getFilieres().add(filiere);  // Ajouter la nouvelle filière
        } else {
            throw new RuntimeException("Erreur lors de la génération de l'id pour filière");
        }

        xmlService.save(filieres);
    }

    @Override
    public void updateFiliere(Long filiereId, Filiere newFiliere) {
        // Récupérer toutes les filières depuis le fichier xml
        Filieres listFilieres = xmlService.load();

        if (listFilieres == null || listFilieres.getFilieres() == null) {
            throw new RuntimeException("Erreur lors de la récupération des données !");
        }

        for (Filiere filiere : listFilieres.getFilieres()) {
            if (filiere.getId() == filiereId) {
                // Mettre à jour les informations de la filière
                filiere.setLibelle(newFiliere.getLibelle());
                break;
            }
        }

        // Sauvegarder
        xmlService.save(listFilieres);
    }

    @Override
    public void deleteFiliere(Long filiereId) {
        // Récupérer toutes les filières depuis le fichier xml
        Filieres filieres = xmlService.load();

        if (filieres == null || filieres.getFilieres() == null) {
            throw new RuntimeException("Erreur lors de la récupération des données !");
        }

        // Supprimer la filière correspondante
        boolean removed = filieres.getFilieres()
                .removeIf(filiere -> filiere.getId() == filiereId);

        // Vérifier si la suppression a réussi
        if (!removed) {
            throw new RuntimeException("Aucune filière trouvée avec l'id " + filiereId);
        }

        // Sauvegarder les modifications
        xmlService.save(filieres);
    }
}
