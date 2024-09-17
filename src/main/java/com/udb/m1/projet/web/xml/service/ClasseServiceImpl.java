package com.udb.m1.projet.web.xml.service;

import com.udb.m1.projet.web.xml.model.Classe;
import com.udb.m1.projet.web.xml.model.Filiere;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

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
        // Code existant
        List<Classe> classes = new ArrayList<>();
        try {
            Document document = xmlService.getDocument();
            NodeList filiereNodes = document.getElementsByTagName("filiere");

            for (int i = 0; i < filiereNodes.getLength(); i++) {
                Element filiereElement = (Element) filiereNodes.item(i);
                Long filiereId = Long.parseLong(filiereElement.getAttribute("id"));
                String filiereLibelle = filiereElement.getElementsByTagName("libelle")
                        .item(0).getTextContent();
                Filiere filiere = new Filiere(filiereId, filiereLibelle, new ArrayList<>());

                NodeList classeNodes = filiereElement.getElementsByTagName("classe");

                for (int j = 0; j < classeNodes.getLength(); j++) {
                    Element classeElement = (Element) classeNodes.item(j);
                    Long id = Long.parseLong(classeElement.getAttribute("id"));
                    String libelle = classeElement.getElementsByTagName("libelle")
                            .item(0).getTextContent();
                    String code = classeElement.getElementsByTagName("code")
                            .item(0).getTextContent();
                    int fraisInscription = Integer.parseInt(
                            classeElement.getElementsByTagName("fraisInscription")
                                    .item(0).getTextContent()
                    );
                    int mensualite = Integer.parseInt(
                            classeElement.getElementsByTagName("mensualite")
                                    .item(0).getTextContent()
                    );

                    // Créer une nouvelle instance de Classe avec la Filiere associée
                    Classe classe = new Classe(
                            id, libelle, code, fraisInscription, mensualite, filiere
                    );

                    // Ajouter la classe à la liste globale
                    classes.add(classe);
                }
            }
            return classes;

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // Recuperer une classe
    public Classe getClasseById(Long classeId) {

        try {
            Document document = xmlService.getDocument();
            NodeList filiereNodes = document.getElementsByTagName("filiere");

            for (int i = 0; i < filiereNodes.getLength(); i++) {
                Element filiereElement = (Element) filiereNodes.item(i);
                Long filiereId = Long.parseLong(filiereElement.getAttribute("id"));
                String filiereLibelle = filiereElement.getElementsByTagName("libelle")
                        .item(0).getTextContent();
                Filiere filiere = new Filiere(filiereId, filiereLibelle, new ArrayList<>());

                NodeList classeNodes = filiereElement.getElementsByTagName("classe");

                for (int j = 0; j < classeNodes.getLength(); j++) {
                    Element classeElement = (Element) classeNodes.item(j);
                    if (Long.parseLong(classeElement.getAttribute("id")) == classeId) {
                        String libelle = classeElement.getElementsByTagName("libelle")
                                .item(0).getTextContent();
                        String code = classeElement.getElementsByTagName("code")
                                .item(0).getTextContent();
                        int fraisInscription = Integer.parseInt(
                                classeElement.getElementsByTagName("fraisInscription")
                                        .item(0).getTextContent()
                        );
                        int mensualite = Integer.parseInt(
                                classeElement.getElementsByTagName("mensualite")
                                        .item(0).getTextContent()
                        );

                        Classe classe = new Classe(
                                classeId, libelle, code, fraisInscription, mensualite, filiere
                        );
                        return classe;
                    }
                }
            }
            return null; // Classe non trouvée

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    // Lister les classes par filiere
    public List<Classe> getAllClassesByFiliere(Long filiereId) {

        try {
            List<Classe> classes = new ArrayList<>();
            Document document = xmlService.getDocument();
            NodeList filiereNodes = document.getElementsByTagName("filiere");

            for (int i = 0; i < filiereNodes.getLength(); i++) {
                Element filiereElement = (Element) filiereNodes.item(i);
                if (Long.parseLong(filiereElement.getAttribute("id")) == filiereId) {
                    String filiereLibelle = filiereElement.getElementsByTagName("libelle")
                            .item(0).getTextContent();
                    Filiere filiere = new Filiere(filiereId, filiereLibelle, new ArrayList<>());

                    NodeList classeNodes = filiereElement.getElementsByTagName("classe");

                    for (int j = 0; j < classeNodes.getLength(); j++) {
                        Element classeElement = (Element) classeNodes.item(j);
                        Long id = Long.parseLong(classeElement.getAttribute("id"));
                        String libelle = classeElement.getElementsByTagName("libelle")
                                .item(0).getTextContent();
                        String code = classeElement.getElementsByTagName("code")
                                .item(0).getTextContent();
                        int fraisInscription = Integer.parseInt(
                                classeElement.getElementsByTagName("fraisInscription")
                                        .item(0).getTextContent()
                        );
                        int mensualite = Integer.parseInt(
                                classeElement.getElementsByTagName("mensualite")
                                        .item(0).getTextContent()
                        );

                        // Créer une nouvelle instance de Classe avec la Filiere associée
                        Classe classe = new Classe(
                                id, libelle, code, fraisInscription, mensualite, filiere
                        );

                        // Ajouter la classe à la liste
                        classes.add(classe);
                    }
                    break;
                }
            }
            return classes;

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public void addClasseToFiliere(Long filiereId, Classe classe) {
        System.out.println("filiereId: " + filiereId);
        System.out.println("classe: " + classe.toString());

        // Code existant
        try {
            Document document = xmlService.getDocument();
            NodeList filieres = document.getElementsByTagName("filiere");
            String _classe = "classe";

            for (int i = 0; i < filieres.getLength(); i++) {

                Element filiere = (Element) filieres.item(i);
                if (Long.parseLong(filiere.getAttribute("id")) == filiereId) {
                    Element classesElement = (Element) filiere.getElementsByTagName("classes")
                            .item(0);

                    Element classeElement = document.createElement("classe");
                    classeElement.setAttribute(
                            "id", String.valueOf(generatorService.generateId(_classe))
                    );

                    Element libelleElement = document.createElement("libelle");
                    libelleElement.setTextContent(classe.getLibelle());
                    classeElement.appendChild(libelleElement);

                    Element codeElement = document.createElement("code");
                    codeElement.setTextContent(classe.getCode());
                    classeElement.appendChild(codeElement);

                    Element fraisInscriptionElement =
                            document.createElement("fraisInscription");
                    fraisInscriptionElement.setTextContent(
                            String.valueOf(classe.getFraisInscription())
                    );
                    classeElement.appendChild(fraisInscriptionElement);

                    Element mensualiteElement = document.createElement("mensualite");
                    mensualiteElement.setTextContent(String.valueOf(classe.getMensualite()));
                    classeElement.appendChild(mensualiteElement);

                    classesElement.appendChild(classeElement);
                    break;
                }
            }

            xmlService.saveDocument(document);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    // Mettre à jour une classe
    public void updateClasse(Long filiereId, Classe newClasse) {

        try {
            Document document = xmlService.getDocument();
            NodeList filieres = document.getElementsByTagName("filiere");

            for (int i = 0; i < filieres.getLength(); i++) {
                Element filiere = (Element) filieres.item(i);
                if (Long.parseLong(filiere.getAttribute("id")) == filiereId) {
                    NodeList classes = filiere.getElementsByTagName("classe");
                    for (int j = 0; j < classes.getLength(); j++) {
                        Element classe = (Element) classes.item(j);
                        if (Long.parseLong(classe.getAttribute("id")) == newClasse.getId()) {
                            // Met à jour le libellé
                            if (newClasse.getLibelle() != null) {
                                Element libelleElement = (Element) classe
                                        .getElementsByTagName("libelle").item(0);
                                libelleElement.setTextContent(newClasse.getLibelle());
                            }
                            // Met à jour le code
                            if (newClasse.getCode() != null) {
                                Element codeElement = (Element) classe
                                        .getElementsByTagName("code").item(0);
                                codeElement.setTextContent(newClasse.getCode());
                            }

                            // Met à jour les frais d'inscription
                            Element fraisInscriptionElement = (Element) classe
                                    .getElementsByTagName("fraisInscription").item(0);
                            fraisInscriptionElement.setTextContent(
                                    String.valueOf(newClasse.getFraisInscription())
                            );

                            // Met à jour la mensualite
                            Element mensualiteElement = (Element) classe
                                    .getElementsByTagName("mensualite").item(0);
                            mensualiteElement.setTextContent(
                                    String.valueOf(newClasse.getMensualite())
                            );

                            break;
                        }
                    }
                    break;
                }
            }
            xmlService.saveDocument(document);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    // Supprimer une classe
    public boolean deleteClasse(Long classeId) {

        try {
            Document document = xmlService.getDocument();
            NodeList filiereNodes = document.getElementsByTagName("filiere");

            for (int i = 0; i < filiereNodes.getLength(); i++) {
                Element filiereElement = (Element) filiereNodes.item(i);

                NodeList classeNodes = filiereElement.getElementsByTagName("classe");

                for (int j = 0; j < classeNodes.getLength(); j++) {
                    Element classeElement = (Element) classeNodes.item(j);
                    if (Long.parseLong(classeElement.getAttribute("id")) == classeId) {
                        classeElement.getParentNode().removeChild(classeElement);
                        xmlService.saveDocument(document);
                        return true; // Classe supprimée
                    }
                }
            }
            return false; // Classe non trouvée

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
