package com.comp3607.parsers;

/**
 * Factory Pattern for creating file parsers
 * Demonstrates Factory Design Pattern
 */
public class FileParserFactory {
    
    /** Default constructor */
    public FileParserFactory() {}
    
    /**
     * Creates appropriate parser based on file type
     * @param fileType The type of file (csv, json, xml)
     * @return FileParser instance
     * @throws IllegalArgumentException if file type is not supported
     */
    public static FileParser createParser(String fileType) {
        if (fileType == null) {
            throw new IllegalArgumentException("File type cannot be null");
        }
        
        switch (fileType.toLowerCase()) {
            case "csv":
                return new CSVParser();
            case "json":
                return new JSONParser();
            case "xml":
                return new XMLParser();
            default:
                throw new IllegalArgumentException("Unsupported file type: " + fileType);
        }
    }
}
