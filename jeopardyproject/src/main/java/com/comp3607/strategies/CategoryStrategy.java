package com.comp3607.strategies;

/**
 * Strategy interface for category-specific answer validation
 */
public interface CategoryStrategy {
    
    /**
     * Validates player answer against correct answer
     * @param answer Player's answer
     * @param correctAnswer The correct answer
     * @return true if answer is correct
     */
    boolean validateAnswer(String answer, String correctAnswer);
    
    /**
     * Calculates points for the question
     * @param value Question value
     * @return Points to award
     */
    int calculatePoints(int value);
}