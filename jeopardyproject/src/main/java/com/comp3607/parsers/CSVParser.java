package com.comp3607.parsers;

import com.comp3607.Question;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CSV File Parser - Strategy Pattern Implementation
 * Parses CSV files with format: Category, Value, Question, OptionA, OptionB, OptionC, OptionD, CorrectAnswer
 */
public class CSVParser implements FileParser {
    
    @Override
    public List<Question> parseFile(String filePath) throws IOException {
        List<Question> questions = new ArrayList<>();
        
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> records = reader.readAll();
            
            // Skip header row if present
            boolean firstRow = true;
            for (String[] record : records) {
                if (firstRow && record[0].equalsIgnoreCase("Category")) {
                    firstRow = false;
                    continue;
                }
                
                if (record.length >= 8) {
                    String category = record[0].trim();
                    int value = Integer.parseInt(record[1].trim());
                    String questionText = record[2].trim();
                    
                    Map<String, String> options = new HashMap<>();
                    options.put("A", record[3].trim());
                    options.put("B", record[4].trim());
                    options.put("C", record[5].trim());
                    options.put("D", record[6].trim());
                    
                    String correctAnswer = record[7].trim().toUpperCase();
                    
                    questions.add(new Question(category, value, questionText, options, correctAnswer));
                }
            }
        } catch (CsvException e) {
            throw new IOException("Error parsing CSV file: " + e.getMessage(), e);
        }
        
        return questions;
    }
}
