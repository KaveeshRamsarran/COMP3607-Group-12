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
        JsonNode questionsNode = rootNode.get("questions");
        
        if (questionsNode != null && questionsNode.isArray()) {
            for (JsonNode questionNode : questionsNode) {
                String category = questionNode.get("category").asText();
                int value = questionNode.get("value").asInt();
                String questionText = questionNode.get("question").asText();
                
                Map<String, String> options = new HashMap<>();
                JsonNode optionsNode = questionNode.get("options");
                options.put("A", optionsNode.get("A").asText());
                options.put("B", optionsNode.get("B").asText());
                options.put("C", optionsNode.get("C").asText());
                options.put("D", optionsNode.get("D").asText());
                
                String correctAnswer = questionNode.get("correctAnswer").asText().toUpperCase();
                
                questions.add(new Question(category, value, questionText, options, correctAnswer));
            }
        }
        
        return questions;
    }
}
