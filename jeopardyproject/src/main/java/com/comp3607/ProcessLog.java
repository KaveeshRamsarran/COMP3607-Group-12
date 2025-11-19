package com.comp3607;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ProcessLog - Singleton Pattern
 * Manages process mining event logs for the Jeopardy game
 * Records all user interactions in a structured CSV format
 */
public class ProcessLog {
    private static final Logger LOGGER = Logger.getLogger(ProcessLog.class.getName());
    private static ProcessLog instance;
    private final List<LogEvent> events = new ArrayList<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private String currentCaseId;
    
    private ProcessLog() {
        // Private constructor for Singleton pattern
    }
    
    /**
     * Get singleton instance
     */
    public static synchronized ProcessLog getInstance() {
        if (instance == null) {
            instance = new ProcessLog();
        }
        return instance;
    }
    
    /**
     * Set the current case ID (game session identifier)
     */
    public void setCaseId(String caseId) {
        this.currentCaseId = caseId;
    }
    
    /**
     * Log a game event with full details
     */
    public void logEvent(String playerId, String activity, String category, 
                        Integer questionValue, String answerGiven, 
                        String result, Integer scoreAfterPlay) {
        String timestamp = LocalDateTime.now().format(formatter);
        events.add(new LogEvent(currentCaseId, playerId, activity, timestamp, 
                               category, questionValue, answerGiven, result, scoreAfterPlay));
    }
    
    /**
     * Log a simple event without player-specific details
     */
    public void logEvent(String activity, String details) {
        String timestamp = LocalDateTime.now().format(formatter);
        events.add(new LogEvent(currentCaseId, "SYSTEM", activity, timestamp, 
                               details, null, null, null, null));
    }
    
    /**
     * Generate CSV log file for process mining
     */
    public void generateCSVLog() {
        String outputPath = "src/main/resources/reports/game_event_log.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            // Write header
            writer.write("Case_ID,Player_ID,Activity,Timestamp,Category,Question_Value,Answer_Given,Result,Score_After_Play");
            writer.newLine();
            
            // Write events
            for (LogEvent event : events) {
                writer.write(event.toCsvLine());
                writer.newLine();
            }
            LOGGER.log(Level.INFO, "Process mining log generated successfully: {0}", outputPath);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error generating process log", e);
        }
    }
    
    /**
     * Clear all logged events (useful for testing)
     */
    public void clearEvents() {
        events.clear();
    }
    
    /**
     * Get all logged events
     */
    public List<LogEvent> getEvents() {
        return new ArrayList<>(events);
    }
    
    /**
     * Inner class representing a log event
     */
    public static class LogEvent {
        private final String caseId;
        private final String playerId;
        private final String activity;
        private final String timestamp;
        private final String category;
        private final Integer questionValue;
        private final String answerGiven;
        private final String result;
        private final Integer scoreAfterPlay;
        
        public LogEvent(String caseId, String playerId, String activity, String timestamp,
                       String category, Integer questionValue, String answerGiven,
                       String result, Integer scoreAfterPlay) {
            this.caseId = caseId;
            this.playerId = playerId;
            this.activity = activity;
            this.timestamp = timestamp;
            this.category = category;
            this.questionValue = questionValue;
            this.answerGiven = answerGiven;
            this.result = result;
            this.scoreAfterPlay = scoreAfterPlay;
        }
        
        public String toCsvLine() {
            return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s",
                nvl(caseId), nvl(playerId), nvl(activity), nvl(timestamp),
                nvl(category), nvl(questionValue), nvl(answerGiven),
                nvl(result), nvl(scoreAfterPlay));
        }
        
        private String nvl(Object value) {
            return value == null ? "" : value.toString();
        }
        
        // Getters
        public String getCaseId() { return caseId; }
        public String getPlayerId() { return playerId; }
        public String getActivity() { return activity; }
        public String getTimestamp() { return timestamp; }
        public String getCategory() { return category; }
        public Integer getQuestionValue() { return questionValue; }
        public String getAnswerGiven() { return answerGiven; }
        public String getResult() { return result; }
        public Integer getScoreAfterPlay() { return scoreAfterPlay; }
    }
}
