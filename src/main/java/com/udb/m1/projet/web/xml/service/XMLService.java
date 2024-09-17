package com.udb.m1.projet.web.xml.service;

import com.udb.m1.projet.web.xml.model.Classe;
import com.udb.m1.projet.web.xml.model.Filiere;
import org.springframework.stereotype.Component;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class XMLService {

    private static final String FILE_PATH = "filiere_classe.xml";

    public Document getDocument() throws Exception {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element root = document.createElement("filieres");
            document.appendChild(root);
            saveDocument(document);
        }
        return loadDocument();
    }

    public Document loadDocument() throws Exception {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        return documentBuilder.parse(new File(FILE_PATH));
    }

    public void saveDocument(Document document) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        // Activer l'indentation
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        // Spécifier la taille de l'indentation (nombre d'espaces)
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(FILE_PATH));
        transformer.transform(domSource, streamResult);
    }

}

