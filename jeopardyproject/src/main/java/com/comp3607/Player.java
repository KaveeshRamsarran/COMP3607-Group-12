package com.comp3607;

import java.util.ArrayList;
import java.util.List;

/**
 * Player class representing a game participant
 * Demonstrates Single Responsibility Principle (SRP)
 */
public class Player {
    private final String name;
    private int score;
    private final List<TurnRecord> turnHistory;

    /**
     * Creates a new player
     * @param name Player's name
     */
    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.turnHistory = new ArrayList<>();
    }

    /**
     * Update player score based on answer correctness
     * @param points Points for the question
     * @param isCorrect Whether answer was correct
     */
    public void answerQuestion(int points, boolean isCorrect) {
        if (isCorrect) {
            this.score += points;
        } else {
            this.score -= points;
        }
    }
    
    /**
     * Add a turn to the player's history
     * @param turn Turn record to add
     */
    public void addTurn(TurnRecord turn) {
        turnHistory.add(turn);
    }

    /**
     * Gets player name
     * @return Player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets player score
     * @return Current score
     */
    public int getScore() {
        return score;
    }
    
    /**
     * Gets turn history
     * @return Copy of turn history
     */
    public List<TurnRecord> getTurnHistory() {
        return new ArrayList<>(turnHistory); // Return defensive copy
    }
    
    /**
     * Get player ID for logging purposes
     * @return Player ID
     */
    public String getPlayerId() {
        return name.replaceAll("\\s+", "_").toUpperCase();
    }
    
    /**
     * Inner class to record turn details
     */
    public static class TurnRecord {
        private final String category;
        private final int questionValue;
        private final String questionText;
        private final String givenAnswer;
        private final boolean isCorrect;
        private final int pointsEarned;
        private final int runningTotal;
        
        /**
         * Creates a turn record
         * @param category Question category
         * @param questionValue Question value
         * @param questionText Question text
         * @param givenAnswer Player's answer
         * @param isCorrect Whether correct
         * @param pointsEarned Points earned/lost
         * @param runningTotal Running score total
         */
        public TurnRecord(String category, int questionValue, String questionText,
                         String givenAnswer, boolean isCorrect, int pointsEarned, int runningTotal) {
            this.category = category;
            this.questionValue = questionValue;
            this.questionText = questionText;
            this.givenAnswer = givenAnswer;
            this.isCorrect = isCorrect;
            this.pointsEarned = pointsEarned;
            this.runningTotal = runningTotal;
        }
        
        // Getters
        /** @return Question category */
        public String getCategory() { return category; }
        
        /** @return Question value */
        public int getQuestionValue() { return questionValue; }
        
        /** @return Question text */
        public String getQuestionText() { return questionText; }
        
        /** @return Player's answer */
        public String getGivenAnswer() { return givenAnswer; }
        
        /** @return true if correct */
        public boolean isCorrect() { return isCorrect; }
        
        /** @return Points earned/lost */
        public int getPointsEarned() { return pointsEarned; }
        
        /** @return Running score total */
        public int getRunningTotal() { return runningTotal; }
    }
}
