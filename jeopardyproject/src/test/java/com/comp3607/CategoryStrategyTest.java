package com.comp3607;

import com.comp3607.strategies.CategoryStrategy;
import com.comp3607.strategies.VariableStrategy;
import com.comp3607.strategies.ControlStructureStrategy;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class CategoryStrategyTest {

    private CategoryStrategy variableStrategy;
    private CategoryStrategy controlStructureStrategy;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        // Initialize strategies before each test
        variableStrategy = new VariableStrategy();
        controlStructureStrategy = new ControlStructureStrategy();
    }

    @Test
    void testVariableStrategyValidation() {
        assertTrue(variableStrategy.validateAnswer("A", "A"));
        assertFalse(variableStrategy.validateAnswer("B", "A"));
    }

    @Test
    void testVariableStrategyCalculatePoints() {
        assertEquals(100, variableStrategy.calculatePoints(100));
        assertEquals(200, variableStrategy.calculatePoints(200));
    }

    @Test
    void testControlStructureStrategyValidation() {
        assertTrue(controlStructureStrategy.validateAnswer("A", "A"));
        assertFalse(controlStructureStrategy.validateAnswer("B", "A"));
    }

    @Test
    void testControlStructureStrategyCalculatePoints() {
        assertEquals(100, controlStructureStrategy.calculatePoints(100));
        assertEquals(200, controlStructureStrategy.calculatePoints(200));
    }
}
