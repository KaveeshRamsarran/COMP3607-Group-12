package com.comp3607.parsers;

import com.comp3607.Question;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JSON File Parser - Strategy Pattern Implementation
 * Parses JSON files with array of question objects
 */
public class JSONParser implements FileParser {
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public List<Question> parseFile(String filePath) throws IOException {
        List<Question> questions = new ArrayList<>();
        
        JsonNode rootNode = objectMapper.readTree(new File(filePath));
        
        // Handle both formats: root array or object with "questions" array
        JsonNode questionsNode = rootNode.isArray() ? rootNode : rootNode.get("questions");
        
        if (questionsNode != null && questionsNode.isArray()) {
            for (JsonNode questionNode : questionsNode) {
                // Support both lowercase and capitalized field names
                String category = getTextValue(questionNode, "category", "Category");
                int value = getIntValue(questionNode, "value", "Value");
                String questionText = getTextValue(questionNode, "question", "Question", "QuestionText");
                
                Map<String, String> options = new HashMap<>();
                JsonNode optionsNode = questionNode.has("options") ? questionNode.get("options") : questionNode.get("Options");
                if (optionsNode != null) {
                    options.put("A", optionsNode.get("A").asText());
                    options.put("B", optionsNode.get("B").asText());
                    options.put("C", optionsNode.get("C").asText());
                    options.put("D", optionsNode.get("D").asText());
                }
                
                String correctAnswer = getTextValue(questionNode, "correctAnswer", "CorrectAnswer").toUpperCase();
                
                questions.add(new Question(category, value, questionText, options, correctAnswer));
            }
        }
        
        return questions;
    }
    
    private String getTextValue(JsonNode node, String... fieldNames) {
        for (String fieldName : fieldNames) {
            if (node.has(fieldName)) {
                return node.get(fieldName).asText();
            }
        }
        return "";
    }
    
    private int getIntValue(JsonNode node, String... fieldNames) {
        for (String fieldName : fieldNames) {
            if (node.has(fieldName)) {
                return node.get(fieldName).asInt();
            }
        }
        return 0;
    }
}
