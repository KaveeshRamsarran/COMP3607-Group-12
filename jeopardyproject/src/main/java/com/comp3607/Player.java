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

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.turnHistory = new ArrayList<>();
    }

    /**
     * Update player score based on answer correctness
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
     */
    public void addTurn(TurnRecord turn) {
        turnHistory.add(turn);
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
    
    public List<TurnRecord> getTurnHistory() {
        return new ArrayList<>(turnHistory); // Return defensive copy
    }
    
    /**
     * Get player ID for logging purposes
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
        public String getCategory() { return category; }
        public int getQuestionValue() { return questionValue; }
        public String getQuestionText() { return questionText; }
        public String getGivenAnswer() { return givenAnswer; }
        public boolean isCorrect() { return isCorrect; }
        public int getPointsEarned() { return pointsEarned; }
        public int getRunningTotal() { return runningTotal; }
    }
}
