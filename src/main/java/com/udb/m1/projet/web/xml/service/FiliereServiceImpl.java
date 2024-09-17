package com.udb.m1.projet.web.xml.service;

import com.udb.m1.projet.web.xml.model.Classe;
import com.udb.m1.projet.web.xml.model.Filiere;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

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
    // Lister les filieres
    public List<Filiere> getAllFilieres() {

        List<Filiere> filieres = new ArrayList<>();
        try {
            Document document = xmlService.getDocument();
            NodeList filiereNodes = document.getElementsByTagName("filiere");

            for (int i = 0; i < filiereNodes.getLength(); i++) {
                Element filiereElement = (Element) filiereNodes.item(i);
                Long id = Long.parseLong(filiereElement.getAttribute("id"));
                String libelle =filiereElement.getElementsByTagName("libelle")
                        .item(0).getTextContent();

                // Créer une nouvelle instance de Filiere
                Filiere filiere = new Filiere(id, libelle, new ArrayList<>());

                // Ajouter à la liste
                filieres.add(filiere);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return filieres;

    }

    @Override
    public void addFiliere(Filiere filiere) {
        String _filiere = "filiere";

        try {
            Document document = xmlService.getDocument();
            Element root = document.getDocumentElement();

            Element filiereElement = document.createElement(_filiere);
            filiereElement.setAttribute(
                    "id", String.valueOf(generatorService.generateId(_filiere))
            );

            Element libelleElement = document.createElement("libelle");
            libelleElement.setTextContent(filiere.getLibelle());
            filiereElement.appendChild(libelleElement);

            Element classesElement = document.createElement("classes");
            filiereElement.appendChild(classesElement);

            root.appendChild(filiereElement);

            xmlService.saveDocument(document);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    // Mettre à jour une filiere
    public void updateFiliere(Long filiereId, Filiere newFiliere) {

        try {
            Document document = xmlService.getDocument();
            NodeList filieres = document.getElementsByTagName("filiere");

            for (int i = 0; i < filieres.getLength(); i++) {
                Element filiere = (Element) filieres.item(i);
                if (Long.parseLong(filiere.getAttribute("id")) == filiereId) {
                    Element libelleElement = (Element) filiere.getElementsByTagName("libelle")
                            .item(0);
                    libelleElement.setTextContent(newFiliere.getLibelle());
                    break;
                }
            }

            xmlService.saveDocument(document);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    // Recuperer une filiere
    public Filiere getFiliere(Long filiereId) {

        try {
            Document document = xmlService.getDocument();
            NodeList filiereNodes = document.getElementsByTagName("filiere");

            for (int i = 0; i < filiereNodes.getLength(); i++) {
                Element filiereElement = (Element) filiereNodes.item(i);
                if (Long.parseLong(filiereElement.getAttribute("id")) == filiereId) {
                    String libelle = filiereElement.getElementsByTagName("libelle")
                            .item(0).getTextContent();
                    Filiere filiere = new Filiere(filiereId, libelle, new ArrayList<>());
                    // optionel - recuperer les classes

                    return filiere;
                }
            }
            return null; // Filière non trouvée
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }


    // Supprimer une filiere
    @Override
    public boolean deleteFiliere(Long filiereId) {

        try {
            Document document = xmlService.getDocument();
            NodeList filiereNodes = document.getElementsByTagName("filiere");

            for (int i = 0; i < filiereNodes.getLength(); i++) {
                Element filiereElement = (Element) filiereNodes.item(i);
                if (Long.parseLong(filiereElement.getAttribute("id")) == filiereId) {
                    filiereElement.getParentNode().removeChild(filiereElement);
                    xmlService.saveDocument(document);
                    return true; // Filière supprimée
                }
            }
            return false; // Filière non trouvée
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
