package com.comp3607;

import com.comp3607.parsers.FileParser;
import com.comp3607.parsers.FileParserFactory;
import com.comp3607.strategies.CategoryStrategy;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Game class - Central game management
 * Demonstrates Single Responsibility and Dependency Inversion Principles
 */
public class Game {
    private final List<Player> players;
    private final List<Question> questions;
    private final Map<String, List<Question>> questionsByCategory;
    private int currentPlayerIndex;
    private final ReportGenerator reportGenerator;
    private final ProcessLog processLog;
    private final String caseId;
    private boolean gameStarted;
    private boolean gameEnded;

    public Game() {
        this.players = new ArrayList<>();
        this.questions = new ArrayList<>();
        this.questionsByCategory = new HashMap<>();
        this.currentPlayerIndex = 0;
        this.reportGenerator = new ReportGenerator();
        this.processLog = ProcessLog.getInstance();
        this.caseId = "GAME_" + System.currentTimeMillis();
        this.processLog.setCaseId(caseId);
        this.gameStarted = false;
        this.gameEnded = false;
    }

    /**
     * Start the game
     */
    public void startGame() {
        processLog.logEvent("Start Game", "Game Started");
        this.gameStarted = true;
    }

    /**
     * Load questions from file using Factory pattern
     */
    public void loadQuestions(String filePath, String fileType) throws IOException {
        processLog.logEvent("Load File", "Attempting to load: " + filePath);
        
        try {
            FileParser parser = FileParserFactory.createParser(fileType);
            this.questions.addAll(parser.parseFile(filePath));
            
            // Organize questions by category
            for (Question q : questions) {
                questionsByCategory.computeIfAbsent(q.getCategory(), k -> new ArrayList<>()).add(q);
            }
            
            processLog.logEvent("File Loaded Successfully", 
                              "Loaded " + questions.size() + " questions");
        } catch (IOException e) {
            processLog.logEvent("Load File Failed", "Error: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Add a player to the game
     */
    public void addPlayer(String playerName) {
        Player player = new Player(playerName);
        players.add(player);
        processLog.logEvent(player.getPlayerId(), "Enter Player Name", 
                          playerName, null, null, null, 0);
    }

    /**
     * Get available categories
     */
    public Set<String> getAvailableCategories() {
        return questionsByCategory.keySet().stream()
            .filter(category -> hasUnansweredQuestions(category))
            .collect(Collectors.toSet());
    }

    /**
     * Get available question values for a category
     */
    public List<Integer> getAvailableValues(String category) {
        return questionsByCategory.getOrDefault(category, new ArrayList<>()).stream()
            .filter(q -> !q.isAnswered())
            .map(Question::getValue)
            .distinct()
            .sorted()
            .collect(Collectors.toList());
    }

    /**
     * Play a turn
     */
    public TurnResult playTurn(String category, int value, String answer, CategoryStrategy strategy) {
        if (!gameStarted) {
            throw new IllegalStateException("Game has not started");
        }
        
        if (gameEnded) {
            throw new IllegalStateException("Game has ended");
        }
        
        Player currentPlayer = players.get(currentPlayerIndex);
        
        // Log category selection
        processLog.logEvent(currentPlayer.getPlayerId(), "Select Category", 
                          category, null, null, null, currentPlayer.getScore());
        
        // Find the question
        Question question = findQuestion(category, value);
        if (question == null) {
            return new TurnResult(false, "Question not found", 0, currentPlayer.getScore());
        }
        
        // Log question selection
        processLog.logEvent(currentPlayer.getPlayerId(), "Select Question", 
                          category, value, null, null, currentPlayer.getScore());
        
        // Validate answer using strategy
        boolean isCorrect = strategy != null ? 
            strategy.validateAnswer(answer, question.getCorrectAnswer()) :
            question.validateAnswer(answer);
        
        int pointsEarned = isCorrect ? value : -value;
        currentPlayer.answerQuestion(value, isCorrect);
        
        // Mark question as answered
        question.markAsAnswered();
        
        // Record turn in player history
        Player.TurnRecord turnRecord = new Player.TurnRecord(
            category, value, question.getQuestionText(), answer,
            isCorrect, pointsEarned, currentPlayer.getScore()
        );
        currentPlayer.addTurn(turnRecord);
        
        // Log the answer
        processLog.logEvent(currentPlayer.getPlayerId(), "Answer Question", 
                          category, value, answer, 
                          isCorrect ? "Correct" : "Incorrect", 
                          currentPlayer.getScore());
        
        // Move to next player
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        
        return new TurnResult(isCorrect, question.getCorrectAnswer(), 
                            pointsEarned, currentPlayer.getScore());
    }

    /**
     * Check if game is complete
     */
    public boolean isGameComplete() {
        return questions.stream().allMatch(Question::isAnswered);
    }

    /**
     * End the game
     */
    public void endGame() {
        this.gameEnded = true;
        processLog.logEvent("Exit Game", "Game Ended");
    }

    /**
     * Generate summary report
     */
    public void generateSummaryReport(String format) throws IOException {
        processLog.logEvent("Generate Report", "Format: " + format);
        reportGenerator.generateReport(players, format);
    }

    /**
     * Generate process mining log
     */
    public void generateProcessMiningLog() {
        processLog.logEvent("Generate Event Log", "Creating process mining log");
        processLog.generateCSVLog();
    }

    // Helper methods
    
    private boolean hasUnansweredQuestions(String category) {
        return questionsByCategory.getOrDefault(category, new ArrayList<>()).stream()
            .anyMatch(q -> !q.isAnswered());
    }

    private Question findQuestion(String category, int value) {
        return questionsByCategory.getOrDefault(category, new ArrayList<>()).stream()
            .filter(q -> q.getValue() == value && !q.isAnswered())
            .findFirst()
            .orElse(null);
    }

    // Getters
    
    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public List<Question> getQuestions() {
        return new ArrayList<>(questions);
    }
    
    public boolean isGameStarted() {
        return gameStarted;
    }
    
    public boolean isGameEnded() {
        return gameEnded;
    }

    /**
     * Inner class to represent turn results
     */
    public static class TurnResult {
        private final boolean correct;
        private final String correctAnswer;
        private final int pointsEarned;
        private final int newScore;

        public TurnResult(boolean correct, String correctAnswer, int pointsEarned, int newScore) {
            this.correct = correct;
            this.correctAnswer = correctAnswer;
            this.pointsEarned = pointsEarned;
            this.newScore = newScore;
        }

        public boolean isCorrect() { return correct; }
        public String getCorrectAnswer() { return correctAnswer; }
        public int getPointsEarned() { return pointsEarned; }
        public int getNewScore() { return newScore; }
    }
}

