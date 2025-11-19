package com.comp3607;

import java.util.Map;
import java.util.HashMap;

/**
 * Question class representing a Jeopardy game question
 * Demonstrates encapsulation and immutability (SOLID principles)
 */
public class Question {
    private final String category;
    private final int value;
    private final String questionText;
    private final Map<String, String> options;
    private final String correctAnswer;
    private boolean answered;

    public Question(String category, int value, String questionText, Map<String, String> options, String correctAnswer) {
        this.category = category;
        this.value = value;
        this.questionText = questionText;
        this.options = new HashMap<>(options); // Defensive copy
        this.correctAnswer = correctAnswer;
        this.answered = false;
    }

    public String getCategory() {
        return category;
    }

    public int getValue() {
        return value;
    }

    public String getQuestionText() {
        return questionText;
    }

    public Map<String, String> getOptions() {
        return new HashMap<>(options); // Return defensive copy
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
    
    public boolean isAnswered() {
        return answered;
    }
    
    public void markAsAnswered() {
        this.answered = true;
    }
    
    /**
     * Validate if the provided answer is correct
     */
    public boolean validateAnswer(String answer) {
        return correctAnswer.equalsIgnoreCase(answer.trim());
    }
    
    /**
     * Get formatted question display
     */
    public String getFormattedQuestion() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nCategory: ").append(category)
          .append(" | Value: ").append(value).append(" points\n")
          .append("Question: ").append(questionText).append("\n")
          .append("Options:\n");
        
        options.forEach((optionKey, optionValue) -> 
            sb.append("  ").append(optionKey).append(". ").append(optionValue).append("\n")
        );
        
        return sb.toString();
    }
}
