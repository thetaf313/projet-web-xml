package com.udb.m1.projet.web.xml.service;

import com.udb.m1.projet.web.xml.model.Filieres;
import jakarta.annotation.PostConstruct;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class XMLService {
    private static final String FILE_PATH = "scolarite.xml";

    /**
     * Vérifie si le fichier XML existe, sinon en crée un nouveau.
     * Ou Charge les données des filières depuis le fichier XML.
     * @return une instance de {@link Filieres} ou null en cas d'erreur.
     */
    public Filieres load() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                System.out.println("Fichier scolarite.xml introuvable. Création d'un nouveau fichier...");
                createXMLFile();
            }

            JAXBContext jaxbContext = JAXBContext.newInstance(Filieres.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (Filieres) unmarshaller.unmarshal(file);
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors du chargement des données depuis le fichier XML", e);
        }
    }

    /**
     * Enregistre les données de filières dans le fichier XML.
     * @param filieres les données à enregistrer
     */
    public void save(Filieres filieres) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Filieres.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  // Formatage pour une sortie lisible
            marshaller.marshal(filieres, new File(FILE_PATH));  // Enregistrement dans le fichier
        } catch (JAXBException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'enregistrement des données dans le fichier XML", e);
        }
    }

    /**
     * Crée un fichier XML avec une structure vide de filières.
     */
    public void createXMLFile() throws JAXBException, IOException {
        Filieres filieres = new Filieres();  // Crée une structure de filières vide
        save(filieres);  // Enregistre cette structure vide dans le fichier
    }
}
