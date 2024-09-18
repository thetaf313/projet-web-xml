package com.udb.m1.projet.web.xml.service;

import com.udb.m1.projet.web.xml.model.Sequence;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

@Component
public class IDGeneratorService {

    private static final String FILE_PATH = "scolarite_sequence.xml";

    public int generateId(String element) {
        Sequence sequence = loadSequence();

        try {
            // Récupérer la classe Sequence
            Class<?> sequenceClass = sequence.getClass();

            // Accéder à l'attribut correspondant à l'élément choisi
            Field field = sequenceClass.getDeclaredField(element);
            field.setAccessible(true);

            // Récupérer la valeur actuelle de l'ID pour l'élément
            int currentId = (int) field.get(sequence);

            // Incrémenter l'ID
            int newId = currentId + 1;

            // Mettre à jour la nouvelle valeur dans l'objet Sequence
            field.set(sequence, newId);

            // Sauvegarder les changements dans le fichier XML
            saveSequence(sequence);

            return newId;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return -1;  // Retourner une valeur par défaut si une erreur survient
    }

    // Lire depuis le fichier XML
    public Sequence loadSequence() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                // Si le fichier n'existe pas,
                // créer un fichier avec des sequences initialisées
                createEmptySequenceFile();
            }

            JAXBContext context = JAXBContext.newInstance(Sequence.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (Sequence) unmarshaller.unmarshal(file);
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }
        // Retourner un objet Sequence vide si le fichier est absent ou en cas d'erreur
        return new Sequence();
    }

    // Écrire dans le fichier XML
    public void saveSequence(Sequence sequence) {
        try {
            JAXBContext context = JAXBContext.newInstance(Sequence.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(sequence, new File(FILE_PATH));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    // Créer un fichier de séquence vide avec des valeurs initialisées
    private void createEmptySequenceFile() throws JAXBException, IOException {
        Sequence sequence = new Sequence();
        saveSequence(sequence);
    }
}
