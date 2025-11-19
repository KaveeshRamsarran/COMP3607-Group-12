package com.comp3607;

import com.comp3607.strategies.CategoryStrategy;
import com.comp3607.strategies.VariableStrategy;

import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.List;

/**
 * Main application class - Entry point for Jeopardy Game
 * Handles user interaction through console interface
 */
public class JeopardyApp {
    private final Game game;
    private final Scanner scanner;
    private final CategoryStrategy defaultStrategy;
    
    public JeopardyApp() {
        this.game = new Game();
        this.scanner = new Scanner(System.in);
        this.defaultStrategy = new VariableStrategy();
    }
    
    public static void main(String[] args) {
        JeopardyApp app = new JeopardyApp();
        app.run();
    }
    
    /**
     * Main application loop
     */
    public void run() {
        System.out.println("=======================================");
        System.out.println("  WELCOME TO JEOPARDY GAME!");
        System.out.println("=======================================\n");
        
        try {
            // Load questions
            loadGameData();
            
            // Setup players
            setupPlayers();
            
            // Start game
            game.startGame();
            System.out.println("\nGame started! Let's play!\n");
            
            // Game loop
            playGame();
            
            // End game and generate reports
            endGame();
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
    
    /**
     * Load game data from file
     */
    private void loadGameData() throws IOException {
        System.out.println("Enter the path to the questions file:");
        String filePath = scanner.nextLine().trim();
        
        System.out.println("Enter file type (csv/json/xml):");
        String fileType = scanner.nextLine().trim();
        
        game.loadQuestions(filePath, fileType);
        System.out.println("Questions loaded successfully!\n");
    }
    
    /**
     * Setup players
     */
    private void setupPlayers() {
        System.out.println("How many players? (1-4):");
        int numPlayers = getIntInput(1, 4);
        
        for (int i = 1; i <= numPlayers; i++) {
            System.out.printf("Enter name for Player %d: ", i);
            String name = scanner.nextLine().trim();
            game.addPlayer(name);
        }
        
        System.out.println("\nPlayers registered:");
        for (Player player : game.getPlayers()) {
            System.out.println("  - " + player.getName());
        }
    }
    
    /**
     * Main game play loop
     */
    private void playGame() {
        while (!game.isGameComplete()) {
            Player currentPlayer = game.getCurrentPlayer();
            System.out.println("\n=======================================");
            System.out.println("Current Player: " + currentPlayer.getName());
            System.out.println("Current Score: " + currentPlayer.getScore());
            System.out.println("---------------------------------------");
            
            // Check if player wants to quit
            System.out.println("\nDo you want to (P)lay or (Q)uit? ");
            String choice = scanner.nextLine().trim().toUpperCase();
            
            if (choice.equals("Q")) {
                System.out.println("Ending game early...");
                break;
            }
            
            // Select category
            Set<String> categories = game.getAvailableCategories();
            if (categories.isEmpty()) {
                System.out.println("No more questions available!");
                break;
            }
            
            System.out.println("\nAvailable Categories:");
            int index = 1;
            String[] categoryArray = categories.toArray(new String[0]);
            for (String category : categoryArray) {
                System.out.println(index++ + ". " + category);
            }
            
            System.out.print("Select category (1-" + categoryArray.length + "): ");
            int categoryChoice = getIntInput(1, categoryArray.length);
            String selectedCategory = categoryArray[categoryChoice - 1];
            
            // Select value
            List<Integer> values = game.getAvailableValues(selectedCategory);
            if (values.isEmpty()) {
                System.out.println("No questions available in this category!");
                continue;
            }
            
            System.out.println("\nAvailable Values:");
            for (int i = 0; i < values.size(); i++) {
                System.out.println((i + 1) + ". " + values.get(i) + " points");
            }
            
            System.out.print("Select value (1-" + values.size() + "): ");
            int valueChoice = getIntInput(1, values.size());
            int selectedValue = values.get(valueChoice - 1);
            
            // Display question
            Question question = findQuestion(selectedCategory, selectedValue);
            if (question != null) {
                System.out.println("\n" + question.getFormattedQuestion());
                System.out.print("Your answer (A/B/C/D): ");
                String answer = scanner.nextLine().trim().toUpperCase();
                
                // Process answer
                Game.TurnResult result = game.playTurn(selectedCategory, selectedValue, 
                                                      answer, defaultStrategy);
                
                if (result.isCorrect()) {
                    System.out.println("\n✓ CORRECT! +" + result.getPointsEarned() + " points");
                } else {
                    System.out.println("\n✗ INCORRECT! " + result.getPointsEarned() + " points");
                    System.out.println("Correct answer was: " + result.getCorrectAnswer());
                }
                
                System.out.println("New score: " + result.getNewScore());
            }
        }
    }
    
    /**
     * End game and generate reports
     */
    private void endGame() {
        System.out.println("\n=======================================");
        System.out.println("         GAME OVER!");
        System.out.println("=======================================\n");
        
        // Display final scores
        System.out.println("FINAL SCORES:");
        List<Player> players = game.getPlayers();
        players.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));
        
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            System.out.printf("%d. %s: %d points%s\n", 
                i + 1, player.getName(), player.getScore(),
                i == 0 ? " 🏆 WINNER!" : "");
        }
        
        // Generate reports
        System.out.println("\nGenerating reports...");
        try {
            System.out.print("Choose report format (txt/pdf/docx): ");
            String format = scanner.nextLine().trim().toLowerCase();
            if (format.isEmpty()) {
                format = "txt";
            }
            
            game.generateSummaryReport(format);
            game.generateProcessMiningLog();
            
            System.out.println("\nReports generated successfully!");
            System.out.println("  - Summary report: src/main/resources/reports/game_report." + format);
            System.out.println("  - Event log: src/main/resources/reports/game_event_log.csv");
        } catch (IOException e) {
            System.err.println("Error generating reports: " + e.getMessage());
        }
        
        System.out.println("\nThank you for playing Jeopardy!");
    }
    
    /**
     * Helper method to get integer input within range
     */
    private int getIntInput(int min, int max) {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.printf("Please enter a number between %d and %d: ", min, max);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
    
    /**
     * Helper method to find a question
     */
    private Question findQuestion(String category, int value) {
        for (Question q : game.getQuestions()) {
            if (q.getCategory().equals(category) && 
                q.getValue() == value && 
                !q.isAnswered()) {
                return q;
            }
        }
        return null;
    }
}
