package com.comp3607.strategies;

public interface CategoryStrategy {
    boolean validateAnswer(String answer, String correctAnswer);
    int calculatePoints(int value);
}