package com.udb.m1.projet.web.xml.service;

import org.springframework.stereotype.Component;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

@Component
public class IDGeneratorService {

    private static final String FILE_PATH = "id_generator.xml";

    public int generateId(String type) throws Exception {
        Document document = loadDocument();
        NodeList idList = document.getElementsByTagName(type);
        int currentId = Integer.parseInt(idList.item(0).getTextContent());
        int newId = currentId + 1;

        idList.item(0).setTextContent(String.valueOf(newId));
        saveDocument(document);

        return newId;
    }

    // loadDocument et saveDocument methodes
    private Document loadDocument() throws Exception {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        return documentBuilder.parse(new File(FILE_PATH));
    }

    private void saveDocument(Document document) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(FILE_PATH));
        transformer.transform(domSource, streamResult);
    }
}
