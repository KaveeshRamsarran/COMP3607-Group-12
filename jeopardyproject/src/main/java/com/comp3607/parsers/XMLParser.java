package com.comp3607.parsers;

import com.comp3607.Question;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * XML File Parser - Strategy Pattern Implementation
 * Parses XML files with question elements
 */
public class XMLParser implements FileParser {
    
    @Override
    public List<Question> parseFile(String filePath) throws IOException {
        List<Question> questions = new ArrayList<>();
        
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filePath));
            document.getDocumentElement().normalize();
            
            NodeList nodeList = document.getElementsByTagName("question");
            
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                
                String category = getElementTextContent(element, "category");
                int value = Integer.parseInt(getElementTextContent(element, "value"));
                String questionText = getElementTextContent(element, "questionText");
                
                Map<String, String> options = new HashMap<>();
                Element optionsElement = (Element) element.getElementsByTagName("options").item(0);
                options.put("A", getElementTextContent(optionsElement, "A"));
                options.put("B", getElementTextContent(optionsElement, "B"));
                options.put("C", getElementTextContent(optionsElement, "C"));
                options.put("D", getElementTextContent(optionsElement, "D"));
                
                String correctAnswer = getElementTextContent(element, "correctAnswer").toUpperCase();
                
                questions.add(new Question(category, value, questionText, options, correctAnswer));
            }
        } catch (Exception e) {
            throw new IOException("Error parsing XML file: " + e.getMessage(), e);
        }
        
        return questions;
    }
    
    private String getElementTextContent(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent().trim();
        }
        return "";
    }
}
