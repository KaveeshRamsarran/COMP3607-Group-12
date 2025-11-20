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
        displayWelcomeBanner();
        System.out.println();
        
        try {
            // Load questions
            loadGameData();
            
            // Setup players
            setupPlayers();
            
            // Start game
            game.startGame();
            displayGameStartMessage();
            
            // Game loop
            playGame();
            
            // End game and generate reports
            endGame();
            
        } catch (IOException e) {
            System.err.println("File error: " + e.getMessage());
        } catch (RuntimeException e) {
            System.err.println("Runtime error: " + e.getMessage());
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
    
    /**
     * Load game data from file
     */
    private void loadGameData() throws IOException {
        printSectionDivider();
        System.out.println("║  GAME SETUP - LOAD QUESTIONS");
        printSectionDivider();
        System.out.println();
        System.out.print("📁 Enter the path to the questions file: ");
        String filePath = scanner.nextLine().trim();
        
        System.out.print("📄 Enter file type (csv/json/xml): ");
        String fileType = scanner.nextLine().trim();
        
        System.out.print("\n⏳ Loading questions");
        for (int i = 0; i < 3; i++) {
            System.out.print(".");
            try { Thread.sleep(200); } catch (InterruptedException e) { }
        }
        
        game.loadQuestions(filePath, fileType);
        System.out.println("\n✓ Questions loaded successfully!");
        System.out.println("✓ " + game.getQuestions().size() + " questions ready for gameplay\n");
    }
    
    /**
     * Setup players
     */
    private void setupPlayers() {
        printSectionDivider();
        System.out.println("║  PLAYER REGISTRATION");
        printSectionDivider();
        System.out.println();
        System.out.print("👥 How many players? (1-4): ");
        int numPlayers = getIntInput(1, 4);
        
        // Log player count selection
        ProcessLog.getInstance().logEvent("Select Player Count", "Number of players: " + numPlayers);
        
        System.out.println();
        for (int i = 1; i <= numPlayers; i++) {
            System.out.print("👤 Enter name for Player " + i + ": ");
            String name = scanner.nextLine().trim();
            game.addPlayer(name);
            System.out.println("   ✓ " + name + " registered!");
        }
        
        System.out.println();
        printSectionDivider();
        System.out.println("║  ROSTER");
        printSectionDivider();
        for (Player player : game.getPlayers()) {
            System.out.println("   • " + player.getName() + " - Ready to compete!");
        }
        System.out.println();
    }
    
    /**
     * Main game play loop
     */
    private void playGame() {
        while (!game.isGameComplete()) {
            Player currentPlayer = game.getCurrentPlayer();
            System.out.println();
            printGameBoard();
            System.out.println();
            printSectionDivider();
            System.out.println("║  CONTESTANT: " + currentPlayer.getName().toUpperCase());
            System.out.println("║  SCORE: $" + currentPlayer.getScore());
            printSectionDivider();
            System.out.println();
            
            // Check if player wants to quit
            System.out.print("▶ Continue playing? (P)lay or (Q)uit: ");
            String choice = scanner.nextLine().trim().toUpperCase();
            
            if (choice.equals("Q")) {
                System.out.println("\n⚠ Game ended by player request.");
                break;
            }
            
            // Select category
            Set<String> categories = game.getAvailableCategories();
            if (categories.isEmpty()) {
                System.out.println("\n🎉 All questions have been answered!");
                break;
            }
            
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║  SELECT A CATEGORY                     ║");
            System.out.println("╚════════════════════════════════════════╝");
            int index = 1;
            String[] categoryArray = categories.toArray(String[]::new);
            for (String category : categoryArray) {
                System.out.println("  [" + index++ + "] " + category);
            }
            
            System.out.print("\n➤ Your choice (1-" + categoryArray.length + "): ");
            int categoryChoice = getIntInput(1, categoryArray.length);
            String selectedCategory = categoryArray[categoryChoice - 1];
            
            // Select value
            List<Integer> values = game.getAvailableValues(selectedCategory);
            if (values.isEmpty()) {
                System.out.println("\n⚠ No questions available in this category!");
                continue;
            }
            
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║  SELECT POINT VALUE                    ║");
            System.out.println("╚════════════════════════════════════════╝");
            for (int i = 0; i < values.size(); i++) {
                System.out.println("  [" + (i + 1) + "] $" + values.get(i));
            }
            
            System.out.print("\n➤ Your choice (1-" + values.size() + "): ");
            int valueChoice = getIntInput(1, values.size());
            int selectedValue = values.get(valueChoice - 1);
            
            // Display question
            Question question = findQuestion(selectedCategory, selectedValue);
            if (question != null) {
                displayQuestion(question, selectedCategory, selectedValue);
                System.out.print("\n➤ Your answer (A/B/C/D): ");
                String answer = scanner.nextLine().trim().toUpperCase();
                
                // Process answer
                Game.TurnResult result = game.playTurn(selectedCategory, selectedValue, 
                                                      answer, defaultStrategy);
                
                displayAnswerResult(result);
            }
        }
    }
    
    /**
     * End game and generate reports
     */
    private void endGame() {
        System.out.println();
        displayGameOverBanner();
        
        // Display final scores
        System.out.println();
        printSectionDivider();
        System.out.println("║  FINAL SCORES");
        printSectionDivider();
        List<Player> players = game.getPlayers();
        players.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));
        
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            if (i == 0) {
                System.out.println();
                System.out.println("   🏆 CHAMPION: " + player.getName().toUpperCase());
                System.out.println("   💰 Final Score: $" + player.getScore());
                System.out.println();
            } else {
                System.out.println("   " + (i + 1) + ". " + player.getName() + " - $" + player.getScore());
            }
        }
        System.out.println();
        
        // Generate reports
        printSectionDivider();
        System.out.println("║  GENERATING REPORTS");
        printSectionDivider();
        System.out.println();
        try {
            System.out.print("📊 Choose report format (txt/pdf/docx): ");
            String format = scanner.nextLine().trim().toLowerCase();
            if (format.isEmpty()) {
                format = "txt";
            }
            
            System.out.print("⏳ Generating reports");
            for (int i = 0; i < 3; i++) {
                System.out.print(".");
                try { Thread.sleep(200); } catch (InterruptedException e) { }
            }
            System.out.println();
            
            game.generateSummaryReport(format);
            game.generateProcessMiningLog();
            
            System.out.println("\n✓ Reports generated successfully!");
            System.out.println("   📄 Summary report: src/main/resources/reports/game_report." + format);
            System.out.println("   📊 Event log: src/main/resources/reports/game_event_log.csv");
        } catch (IOException e) {
            System.err.println("❌ Error generating reports: " + e.getMessage());
        }
        
        System.out.println();
        displayClosingMessage();
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
    
    // ==================== UI Display Methods ====================
    
    private void displayWelcomeBanner() {
        System.out.println("\n");
        System.out.println("╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                                    ║");
        System.out.println("║       ██╗███████╗ ██████╗ ██████╗  █████╗ ██████╗ ██████╗ ██╗   ██ ║");
        System.out.println("║       ██║██╔════╝██╔═══██╗██╔══██╗██╔══██╗██╔══██╗██╔══██╗╚██╗ ██  ║");
        System.out.println("║       ██║█████╗  ██║   ██║██████╔╝███████║██████╔╝██║  ██║ ╚████╔  ║");
        System.out.println("║  ██   ██║██╔══╝  ██║   ██║██╔═══╝ ██╔══██║██╔══██╗██║  ██║  ╚██╔╝  ║");
        System.out.println("║  ╚█████╔╝███████╗╚██████╔╝██║     ██║  ██║██║  ██║██████╔╝   ██║   ║");
        System.out.println("║   ╚════╝ ╚══════╝ ╚═════╝ ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═╝╚═════╝    ╚═╝   ║");
        System.out.println("║                                                                    ║");
        System.out.println("║                  THE ULTIMATE TRIVIA CHALLENGE                     ║");
        System.out.println("║                                                                    ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════╝");
        System.out.println();
    }
    
    private void displayGameStartMessage() {
        System.out.println();
        printSectionDivider();
        System.out.println("║  LET THE GAME BEGIN!");
        printSectionDivider();
        System.out.println();
        System.out.println("   📺 Answer questions correctly to earn points");
        System.out.println("   💡 Each question has four options (A, B, C, D)");
        System.out.println("   ⚡ The higher the value, the harder the question");
        System.out.println("   🏆 The contestant with the most points wins!");
        System.out.println();
    }
    
    private void displayGameBoard() {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                     JEOPARDY GAME BOARD                      ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    }
    
    private void displayQuestion(Question question, String category, int value) {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║  CATEGORY: " + category);                                   
        System.out.println("║  VALUE: $" + value);
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("❓ " + question.getQuestionText());
        System.out.println();
        System.out.println("   [A] " + question.getOptions().get("A"));
        System.out.println("   [B] " + question.getOptions().get("B"));
        System.out.println("   [C] " + question.getOptions().get("C"));
        System.out.println("   [D] " + question.getOptions().get("D"));
    }
    
    private void displayAnswerResult(Game.TurnResult result) {
        System.out.println();
        if (result.isCorrect()) {
            System.out.println("╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║  ✓ CORRECT!                                                  ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            System.out.println("   🎉 Well done! You earned $" + result.getPointsEarned());
        } else {
            System.out.println("╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║  ✗ INCORRECT                                                 ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            System.out.println("   💔 Sorry, you lost $" + Math.abs(result.getPointsEarned()));
            System.out.println("   📚 The correct answer was: " + result.getCorrectAnswer());
        }
        System.out.println();
        System.out.println("   💰 NEW SCORE: $" + result.getNewScore());
        System.out.println();
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }
    
    private void displayGameOverBanner() {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                              ║");
        System.out.println("║           ██████╗  █████╗ ███╗   ███╗███████╗                ║");
        System.out.println("║          ██╔════╝ ██╔══██╗████╗ ████║██╔════╝                ║");
        System.out.println("║          ██║  ███╗███████║██╔████╔██║█████╗                  ║");
        System.out.println("║          ██║   ██║██╔══██║██║╚██╔╝██║██╔══╝                  ║");
        System.out.println("║          ╚██████╔╝██║  ██║██║ ╚═╝ ██║███████╗                ║");
        System.out.println("║           ╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝                ║");
        System.out.println("║                                                              ║");
        System.out.println("║               ██████╗ ██╗   ██╗███████╗██████╗               ║");
        System.out.println("║              ██╔═══██╗██║   ██║██╔════╝██╔══██╗              ║");
        System.out.println("║              ██║   ██║██║   ██║█████╗  ██████╔╝              ║");
        System.out.println("║              ██║   ██║╚██╗ ██╔╝██╔══╝  ██╔══██╗              ║");
        System.out.println("║              ╚██████╔╝ ╚████╔╝ ███████╗██║  ██║              ║");
        System.out.println("║               ╚═════╝   ╚═══╝  ╚══════╝╚═╝  ╚═╝              ║");
        System.out.println("║                                                              ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    }
    
    private void displayClosingMessage() {
        printSectionDivider();
        System.out.println("║  THANK YOU FOR PLAYING!");
        printSectionDivider();
        System.out.println();
        System.out.println("   🎭 That's all for today's Jeopardy!");
        System.out.println("   🌟 We hope you enjoyed the game");
        System.out.println("   👋 See you next time, contestants!");
        System.out.println();
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    }
    
    private void printSectionDivider() {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
    }
    
    private void printGameBoard() {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                     JEOPARDY GAME BOARD                      ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    }
}
