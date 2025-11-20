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
            
            // Try both tag names: "question" and "QuestionItem"
            NodeList nodeList = document.getElementsByTagName("question");
            if (nodeList.getLength() == 0) {
                nodeList = document.getElementsByTagName("QuestionItem");
            }
            
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                
                // Support both lowercase and capitalized tag names
                String category = getElementText(element, "category", "Category");
                int value = Integer.parseInt(getElementText(element, "value", "Value"));
                String questionText = getElementText(element, "questionText", "QuestionText");
                
                Map<String, String> options = new HashMap<>();
                // Try both tag names for options container
                Element optionsElement = (Element) element.getElementsByTagName("options").item(0);
                if (optionsElement == null) {
                    optionsElement = (Element) element.getElementsByTagName("Options").item(0);
                }
                
                if (optionsElement != null) {
                    options.put("A", getElementText(optionsElement, "A", "OptionA"));
                    options.put("B", getElementText(optionsElement, "B", "OptionB"));
                    options.put("C", getElementText(optionsElement, "C", "OptionC"));
                    options.put("D", getElementText(optionsElement, "D", "OptionD"));
                }
                
                String correctAnswer = getElementText(element, "correctAnswer", "CorrectAnswer").toUpperCase();
                
                questions.add(new Question(category, value, questionText, options, correctAnswer));
            }
        } catch (javax.xml.parsers.ParserConfigurationException | org.xml.sax.SAXException e) {
            throw new IOException("Error parsing XML file: " + e.getMessage(), e);
        } catch (NumberFormatException e) {
            throw new IOException("Invalid number format in XML file: " + e.getMessage(), e);
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
    
    private String getElementText(Element parent, String... tagNames) {
        for (String tagName : tagNames) {
            NodeList nodeList = parent.getElementsByTagName(tagName);
            if (nodeList.getLength() > 0) {
                return nodeList.item(0).getTextContent().trim();
            }
        }
        return "";
    }
}
