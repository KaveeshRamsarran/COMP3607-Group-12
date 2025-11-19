package com.comp3607;

import org.junit.jupiter.api.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ProcessLogTest {

    private ProcessLog processLog;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        processLog = ProcessLog.getInstance();
        processLog.clearEvents();
        processLog.setCaseId("TEST_CASE_" + System.currentTimeMillis());
    }

    @Test
    void testSingletonInstance() {
        ProcessLog instance1 = ProcessLog.getInstance();
        ProcessLog instance2 = ProcessLog.getInstance();
        assertSame(instance1, instance2);
    }

    @Test
    void testLogSimpleEvent() {
        processLog.logEvent("Test Event", "This is a test event.");
        assertNotNull(processLog);
        assertFalse(processLog.getEvents().isEmpty());
    }
    
    @Test
    void testLogFullEvent() {
        processLog.logEvent("PLAYER1", "Answer Question", "Variables", 100, "A", "Correct", 100);
        assertEquals(1, processLog.getEvents().size());
        
        ProcessLog.LogEvent event = processLog.getEvents().get(0);
        assertEquals("PLAYER1", event.getPlayerId());
        assertEquals("Answer Question", event.getActivity());
        assertEquals("Variables", event.getCategory());
        assertEquals(100, event.getQuestionValue());
    }

    @Test
    void testGenerateCSVLog() throws IOException {
        processLog.logEvent("PLAYER1", "Test Event", "Category", 100, "A", "Correct", 100);
        processLog.generateCSVLog();
        
        File logFile = new File("src/main/resources/reports/game_event_log.csv");
        assertTrue(logFile.exists());
        
        String content = new String(Files.readAllBytes(Paths.get(logFile.getPath())));
        assertTrue(content.contains("Case_ID"));
        assertTrue(content.contains("PLAYER1"));
    }
}
