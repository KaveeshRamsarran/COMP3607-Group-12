package com.comp3607;

import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ReportGeneratorTest {

    private ReportGenerator reportGenerator;
    private List<Player> players;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        reportGenerator = new ReportGenerator();
        players = new ArrayList<>();
        
        Player alice = new Player("Alice");
        Player bob = new Player("Bob");
        
        // Simulating some gameplay with turn records
        alice.answerQuestion(100, true);
        alice.addTurn(new Player.TurnRecord(
            "Variables", 100, "What is an int?", "A", true, 100, 100
        ));
        
        bob.answerQuestion(200, false);
        bob.addTurn(new Player.TurnRecord(
            "Control Structures", 200, "What is a loop?", "C", false, -200, -200
        ));
        
        players.add(alice);
        players.add(bob);
    }

    @Test
    void testGenerateTXTReport() throws IOException {
        // Create reports directory if it doesn't exist
        Files.createDirectories(Paths.get("src/main/resources/reports"));
        
        // Test that the report is generated
        reportGenerator.generateTXTReport(players);

        // Check that the file exists
        File reportFile = new File("src/main/resources/reports/game_report.txt");
        assertTrue(reportFile.exists());

        // Check the contents of the file
        String content = new String(Files.readAllBytes(Paths.get(reportFile.getPath())));
        assertTrue(content.contains("Alice"));
        assertTrue(content.contains("Bob"));
        assertTrue(content.contains("JEOPARDY GAME SUMMARY REPORT"));
    }

    @Test
    void testReportFormat() throws IOException {
        Files.createDirectories(Paths.get("src/main/resources/reports"));
        
        // Test different report formats
        assertDoesNotThrow(() -> reportGenerator.generateReport(players, "txt"));
        
        // PDF and DOCX might fail without proper setup, but we test they don't throw unexpected errors
        assertDoesNotThrow(() -> reportGenerator.generateReport(players, "pdf"));
        assertDoesNotThrow(() -> reportGenerator.generateReport(players, "docx"));
    }
    
    @Test
    void testUnsupportedFormat() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reportGenerator.generateReport(players, "xyz");
        });
        assertNotNull(exception);
    }
}
