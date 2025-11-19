package com.comp3607;

import org.junit.jupiter.api.*;
import java.util.Map;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

    private Question question;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        // Setting up a question before each test
        Map<String, String> options = new HashMap<>();
        options.put("A", "int num;");
        options.put("B", "float num;");
        options.put("C", "num int;");
        options.put("D", "integer num;");
        
        question = new Question("Variables & Data Types", 100, 
            "Which of the following declares an integer variable in C++?", options, "A");
    }

    @Test
    void testQuestionCreation() {
        // Verify the question was created correctly
        assertNotNull(question);
        assertEquals("Variables & Data Types", question.getCategory());
        assertEquals(100, question.getValue());
        assertEquals("Which of the following declares an integer variable in C++?", question.getQuestionText());
    }

    @Test
    void testCorrectAnswer() {
        // Test the correct answer
        assertEquals("A", question.getCorrectAnswer());
    }

    @Test
    void testOptions() {
        // Test the options map
        assertEquals("int num;", question.getOptions().get("A"));
        assertEquals("float num;", question.getOptions().get("B"));
    }
    
    @Test
    void testValidateAnswer() {
        // Test answer validation
        assertTrue(question.validateAnswer("A"));
        assertFalse(question.validateAnswer("B"));
    }
    
    @Test
    void testMarkAsAnswered() {
        // Test marking question as answered
        assertFalse(question.isAnswered());
        question.markAsAnswered();
        assertTrue(question.isAnswered());
    }
}
