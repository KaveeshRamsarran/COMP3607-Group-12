package com.comp3607.parsers;

import com.comp3607.Question;
import java.io.IOException;
import java.util.List;

/**
 * Interface for file parsers - Strategy Pattern
 * Allows different parsing strategies for CSV, JSON, and XML
 */
public interface FileParser {
    
    /**
     * Parses a file and returns list of questions
     * @param filePath Path to the file
     * @return List of parsed questions
     * @throws IOException If file cannot be read
     */
    List<Question> parseFile(String filePath) throws IOException;
}
