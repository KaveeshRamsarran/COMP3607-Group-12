package com.comp3607.strategies;

public class ControlStructureStrategy implements CategoryStrategy {

    @Override
    public boolean validateAnswer(String answer, String correctAnswer) {
        return answer.equals(correctAnswer);
    }

    @Override
    public int calculatePoints(int value) {
        return value;  // Points equal to the value of the question
    }
}
