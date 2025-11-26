package com.comp3607;

import java.util.Map;

/**
 * Factory for creating Question objects
 */
public class QuestionFactory {
    
    /** Default constructor */
    public QuestionFactory() {}
    
    /**
     * Creates a question
     * @param category Question category
     * @param value Point value
     * @param questionText Question text
     * @param options Answer options
     * @param correctAnswer Correct answer
     * @return New Question instance
     */
    public Question createQuestion(String category, int value, String questionText, Map<String, String> options, String correctAnswer) {
        return new Question(category, value, questionText, options, correctAnswer);
    }
}
