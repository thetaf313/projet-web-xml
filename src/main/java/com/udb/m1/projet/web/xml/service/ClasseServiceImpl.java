package com.udb.m1.projet.web.xml.service;

import com.udb.m1.projet.web.xml.model.Classe;
import com.udb.m1.projet.web.xml.model.Filiere;
import com.udb.m1.projet.web.xml.model.Filieres;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ClasseServiceImpl implements ClasseService {

    private final XMLService xmlService;
    private final IDGeneratorService generatorService;

    public ClasseServiceImpl(XMLService xmlService, IDGeneratorService generatorService) {
        this.xmlService = xmlService;
        this.generatorService = generatorService;
    }

    @Override
    public List<Classe> getAllClasses() {
        Filieres listFilieres = xmlService.load();

        if (listFilieres == null || listFilieres.getFilieres() == null || listFilieres.getFilieres().isEmpty()) {
            throw new RuntimeException("Erreur lors de la récupération des données");
        }

        List<Classe> classes = new ArrayList<>();
        for (Filiere filiere : listFilieres.getFilieres()) {
            if (filiere.getClasses() != null && !filiere.getClasses().isEmpty()) {
                // Assure que chaque classe garde bien la référence à sa filière
                for (Classe classe : filiere.getClasses()) {
                    classe.setFiliere(filiere);
                }
                classes.addAll(filiere.getClasses());
            }
        }
        return classes;
    }


    @Override
    public Classe getClasseById(Long classeId) {
        Filieres listFilieres = xmlService.load();
        if (listFilieres == null || listFilieres.getFilieres() == null) {
            throw new RuntimeException("Erreur lors de la récupération des données");
        }

        for (Filiere filiere : listFilieres.getFilieres()) {
            if (filiere.getClasses() != null) {
                for (Classe classe : filiere.getClasses()) {
                    if (classe.getId() == classeId) {
                        classe.setFiliere(filiere); // Associe la filière à la classe
                        return classe;
                    }
                }
            }
        }
        throw new RuntimeException("Classe avec l'ID " + classeId + " introuvable");
    }

    @Override
    public List<Classe> getAllClassesByFiliere(Long filiereId) {
        Filieres listFilieres = xmlService.load();
        if (listFilieres == null || listFilieres.getFilieres() == null) {
            throw new RuntimeException("Erreur lors de la récupération des données");
        }

        for (Filiere filiere : listFilieres.getFilieres()) {
            if (filiere.getId() == filiereId) {
                List<Classe> classes = filiere.getClasses();
                if (classes != null && !classes.isEmpty()) {
                    for (Classe classe : classes) {
                        classe.setFiliere(filiere); // Associe la filière à chaque classe
                    }
                    return classes;
                } else {
                    return new ArrayList<>(); // Retourne une liste vide si aucune classe
                }
            }
        }

        throw new RuntimeException("Filière avec l'ID " + filiereId + " introuvable");
    }

    @Override
    public void addClasseToFiliere(Long filiereId, Classe classe) {
        Filieres listFilieres = xmlService.load();
        if (listFilieres == null) {
            throw new RuntimeException("Erreur lors de la recuperation des données");
        }

        for (Filiere filiere : listFilieres.getFilieres()) {
            if (filiere.getId() == filiereId) {
                // Generer l'ID de la classe
                int newId = generatorService.generateId("classe");
                if (newId != -1) {
                    classe.setId(newId);
                    // Optionnel - Associe la classe à la filière
                    classe.setFiliere(filiere);

                    // Ajoute la classe à la liste de classes de la filière
                    filiere.getClasses().add(classe);

                    // Sauvegarde les modifications dans le fichier XML
                    xmlService.save(listFilieres);
                    break;
                }
                // else erreur lors de la generation de l'ID
            }
        }
    }

    @Override
    public void updateClasse(Long classeId, Classe newClasse) {
        Filieres listFilieres = xmlService.load();
        if (listFilieres == null || listFilieres.getFilieres() == null) {
            throw new RuntimeException("Erreur lors de la récupération des données");
        }

        for (Filiere filiere : listFilieres.getFilieres()) {
            List<Classe> classes = filiere.getClasses();
            if (classes != null) {
                for (Classe classe : classes) {
                    if (classe.getId() == classeId) {
                        // Mise à jour des informations de la classe
                        classe.setLibelle(newClasse.getLibelle());
                        classe.setCode(newClasse.getCode());
                        classe.setFraisInscription(newClasse.getFraisInscription());
                        classe.setMensualite(newClasse.getMensualite());

                        // Sauvegarde les modifications dans le fichier XML
                        xmlService.save(listFilieres);
                        return;
                    }
                }
            }
        }

        throw new RuntimeException("Classe avec l'ID " + classeId + " introuvable");
    }


    @Override
    public void deleteClasse(Long classeId) {
        // Code pour la suppression d'une classe
        Filieres listFilieres = xmlService.load();
        if (listFilieres == null || listFilieres.getFilieres() == null) {
            throw new RuntimeException("Erreur lors de la récupération des données");
        }

        for (Filiere filiere : listFilieres.getFilieres()) {
            List<Classe> classes = filiere.getClasses();
            if (classes != null) {
                Classe classeToRemove = null;
                for (Classe classe : classes) {
                    if (classe.getId() == classeId) {
                        classeToRemove = classe;
                        break;
                    }
                }

                if (classeToRemove != null) {
                    classes.remove(classeToRemove);
                    // Sauvegarde les modifications
                    xmlService.save(listFilieres);
                    return;
                }
            }
        }

        throw new RuntimeException("Classe avec l'ID " + classeId + " introuvable");
    }
}
