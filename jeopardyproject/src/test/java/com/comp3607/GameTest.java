package com.comp3607;

import org.junit.jupiter.api.*;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game game;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        game = new Game();
    }

    @Test
    void testStartGame() {
        game.startGame();
        assertTrue(game.isGameStarted());
        assertFalse(game.isGameEnded());
    }
    
    @Test
    void testAddPlayers() {
        game.addPlayer("Alice");
        game.addPlayer("Bob");
        
        assertEquals(2, game.getPlayers().size());
        assertEquals("Alice", game.getPlayers().get(0).getName());
        assertEquals("Bob", game.getPlayers().get(1).getName());
    }

    @Test
    void testLoadQuestionsCSV() throws IOException {
        // This test requires actual CSV file
        // For now, we test that exception handling works
        Exception exception = assertThrows(IOException.class, () -> {
            game.loadQuestions("nonexistent.csv", "csv");
        });
        assertNotNull(exception);
    }

    @Test
    void testPlayerScoreUpdate() {
        Player player = new Player("Alice");
        player.answerQuestion(100, true);
        assertEquals(100, player.getScore());
        
        player.answerQuestion(50, false);
        assertEquals(50, player.getScore());
    }
    
    @Test
    void testEndGame() {
        game.startGame();
        game.endGame();
        assertTrue(game.isGameEnded());
    }
    
    @Test
    void testGetCurrentPlayer() {
        game.addPlayer("Alice");
        game.addPlayer("Bob");
        
        Player currentPlayer = game.getCurrentPlayer();
        assertEquals("Alice", currentPlayer.getName());
    }
}
