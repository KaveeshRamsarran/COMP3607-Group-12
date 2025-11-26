package com.comp3607.strategies;

/**
 * Variable category strategy implementation
 */
public class VariableStrategy implements CategoryStrategy {
    
    /** Default constructor */
    public VariableStrategy() {}
    
    @Override
    public boolean validateAnswer(String answer, String correctAnswer) {
        return answer.equals(correctAnswer);
    }

    @Override
    public int calculatePoints(int value) {
        return value; // Points equal to the value of the question
    }
}
