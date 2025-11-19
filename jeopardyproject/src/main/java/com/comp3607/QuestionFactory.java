package com.comp3607;

import java.util.Map;

public class QuestionFactory {
    public Question createQuestion(String category, int value, String questionText, Map<String, String> options, String correctAnswer) {
        return new Question(category, value, questionText, options, correctAnswer);
    }
}
