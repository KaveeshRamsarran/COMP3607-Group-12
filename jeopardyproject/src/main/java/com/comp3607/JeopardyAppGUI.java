package com.comp3607;

import com.comp3607.strategies.CategoryStrategy;
import com.comp3607.strategies.VariableStrategy;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * JavaFX GUI Application for Jeopardy Game
 * Provides an interactive window-based interface that mirrors the command-line version
 */
public class JeopardyAppGUI extends Application {
    private final Game game = new Game();
    private final CategoryStrategy defaultStrategy = new VariableStrategy();
    private Stage primaryStage;
    private Scene mainScene;
    private String jeopardyFontFamily = "Impact"; // Default font family (Jeopardy-themed)
    
    // UI Components
    private VBox mainContainer;
    private Label statusLabel;
    private Label playerLabel;
    private Label scoreLabel;
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        loadJeopardyFont();
        primaryStage.setTitle("JEOPARDY! - The Ultimate Trivia Challenge");
        
        // Show welcome screen
        showWelcomeScreen();
        
        primaryStage.setWidth(1000);
        primaryStage.setHeight(700);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    /**
     * Load Jeopardy-themed font from resources or use Impact as fallback
     */
    private void loadJeopardyFont() {
        try {
            File fontFile = new File("src/main/resources/fonts");
            if (fontFile.exists() && fontFile.isDirectory()) {
                File[] fonts = fontFile.listFiles((dir, name) -> name.endsWith(".ttf"));
                if (fonts != null && fonts.length > 0) {
                    // Load the first TTF font found
                    String fontUrl = fonts[0].toURI().toString();
                    Font customFont = Font.loadFont(fontUrl, 20);
                    if (customFont != null) {
                        jeopardyFontFamily = customFont.getFamily();
                    }
                }
            }
        } catch (Exception e) {
            // Fall back to Impact font
            jeopardyFontFamily = "Impact";
        }
    }
    
    /**
     * Display welcome screen
     */
    private void showWelcomeScreen() {
        VBox welcomeBox = new VBox(30);
        welcomeBox.setAlignment(Pos.CENTER);
        welcomeBox.setStyle("-fx-background-color: #060CE9;");
        welcomeBox.setPadding(new Insets(50));
        
        // Title
        Text title = new Text("JEOPARDY!");
        title.setFont(Font.font(jeopardyFontFamily, FontWeight.BOLD, 72));
        title.setFill(Color.WHITE);
        title.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 10, 0, 0, 3);");
        
        Text subtitle = new Text("THE ULTIMATE TRIVIA CHALLENGE");
        subtitle.setFont(Font.font(jeopardyFontFamily, FontWeight.BOLD, 24));
        subtitle.setFill(Color.GOLD);
        
        // Start button
        Button startButton = createStyledButton("START GAME", 200, 60);
        startButton.setOnAction(e -> showFileSelection());
        
        welcomeBox.getChildren().addAll(title, subtitle, startButton);
        
        mainScene = new Scene(welcomeBox);
        primaryStage.setScene(mainScene);
    }
    
    /**
     * Show file selection screen
     */
    private void showFileSelection() {
        VBox fileBox = new VBox(20);
        fileBox.setAlignment(Pos.CENTER);
        fileBox.setStyle("-fx-background-color: #060CE9;");
        fileBox.setPadding(new Insets(50));
        
        Text header = new Text("GAME SETUP");
        header.setFont(Font.font(jeopardyFontFamily, FontWeight.BOLD, 48));
        header.setFill(Color.WHITE);
        
        Text instruction = new Text("📁 Select Questions File");
        instruction.setFont(Font.font(jeopardyFontFamily, FontWeight.NORMAL, 24));
        instruction.setFill(Color.GOLD);
        
        // File path display
        Label filePathLabel = new Label("No file selected");
        filePathLabel.setFont(Font.font(jeopardyFontFamily, 16));
        filePathLabel.setTextFill(Color.WHITE);
        filePathLabel.setStyle("-fx-background-color: rgba(0,0,0,0.3); -fx-padding: 10;");
        filePathLabel.setMinWidth(500);
        
        // File type selection
        HBox fileTypeBox = new HBox(15);
        fileTypeBox.setAlignment(Pos.CENTER);
        
        Label fileTypeLabel = new Label("File Type:");
        fileTypeLabel.setFont(Font.font(jeopardyFontFamily, FontWeight.BOLD, 18));
        fileTypeLabel.setTextFill(Color.WHITE);
        
        ToggleGroup fileTypeGroup = new ToggleGroup();
        RadioButton csvRadio = new RadioButton("CSV");
        RadioButton jsonRadio = new RadioButton("JSON");
        RadioButton xmlRadio = new RadioButton("XML");
        
        csvRadio.setToggleGroup(fileTypeGroup);
        jsonRadio.setToggleGroup(fileTypeGroup);
        xmlRadio.setToggleGroup(fileTypeGroup);
        jsonRadio.setSelected(true);
        
        styleRadioButton(csvRadio);
        styleRadioButton(jsonRadio);
        styleRadioButton(xmlRadio);
        
        fileTypeBox.getChildren().addAll(fileTypeLabel, csvRadio, jsonRadio, xmlRadio);
        
        // Buttons
        Button browseButton = createStyledButton("BROWSE FILES", 180, 50);
        Button loadButton = createStyledButton("LOAD QUESTIONS", 180, 50);
        loadButton.setDisable(true);
        
        final String[] selectedFile = {null};
        
        browseButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Questions File");
            fileChooser.setInitialDirectory(new File("src/main/resources/data"));
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
                new FileChooser.ExtensionFilter("JSON Files", "*.json"),
                new FileChooser.ExtensionFilter("XML Files", "*.xml")
            );
            
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                selectedFile[0] = file.getAbsolutePath();
                filePathLabel.setText(file.getName());
                loadButton.setDisable(false);
                
                // Auto-detect file type
                String fileName = file.getName().toLowerCase();
                if (fileName.endsWith(".csv")) csvRadio.setSelected(true);
                else if (fileName.endsWith(".json")) jsonRadio.setSelected(true);
                else if (fileName.endsWith(".xml")) xmlRadio.setSelected(true);
            }
        });
        
        loadButton.setOnAction(e -> {
            if (selectedFile[0] != null) {
                RadioButton selected = (RadioButton) fileTypeGroup.getSelectedToggle();
                String fileType = selected.getText().toLowerCase();
                
                try {
                    game.loadQuestions(selectedFile[0], fileType);
                    showPlayerSetup();
                } catch (IOException ex) {
                    showError("Error loading file: " + ex.getMessage());
                }
            }
        });
        
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(browseButton, loadButton);
        
        fileBox.getChildren().addAll(header, instruction, filePathLabel, fileTypeBox, buttonBox);
        
        mainScene = new Scene(fileBox);
        primaryStage.setScene(mainScene);
    }
    
    /**
     * Show player setup screen
     */
    private void showPlayerSetup() {
        VBox setupBox = new VBox(25);
        setupBox.setAlignment(Pos.CENTER);
        setupBox.setStyle("-fx-background-color: #060CE9;");
        setupBox.setPadding(new Insets(50));
        
        Text header = new Text("PLAYER REGISTRATION");
        header.setFont(Font.font(jeopardyFontFamily, FontWeight.BOLD, 48));
        header.setFill(Color.WHITE);
        
        Text instruction = new Text("👥 How Many Contestants?");
        instruction.setFont(Font.font(jeopardyFontFamily, FontWeight.NORMAL, 24));
        instruction.setFill(Color.GOLD);
        
        // Player count buttons
        HBox playerCountBox = new HBox(15);
        playerCountBox.setAlignment(Pos.CENTER);
        
        ToggleGroup playerGroup = new ToggleGroup();
        for (int i = 1; i <= 4; i++) {
            ToggleButton btn = new ToggleButton(String.valueOf(i));
            btn.setToggleGroup(playerGroup);
            btn.setFont(Font.font(jeopardyFontFamily, FontWeight.BOLD, 28));
            btn.setPrefSize(80, 80);
            btn.setStyle("-fx-background-color: #FFD700; -fx-text-fill: #060CE9; " +
                        "-fx-background-radius: 10; -fx-border-color: white; " +
                        "-fx-border-width: 3; -fx-border-radius: 10;");
            
            btn.setOnMouseEntered(ev -> btn.setStyle("-fx-background-color: #FFA500; " +
                        "-fx-text-fill: white; -fx-background-radius: 10; " +
                        "-fx-border-color: white; -fx-border-width: 3; -fx-border-radius: 10;"));
            btn.setOnMouseExited(ev -> {
                if (!btn.isSelected()) {
                    btn.setStyle("-fx-background-color: #FFD700; -fx-text-fill: #060CE9; " +
                        "-fx-background-radius: 10; -fx-border-color: white; " +
                        "-fx-border-width: 3; -fx-border-radius: 10;");
                }
            });
            
            playerCountBox.getChildren().add(btn);
        }
        
        Button continueButton = createStyledButton("CONTINUE", 180, 50);
        continueButton.setDisable(true);
        
        playerGroup.selectedToggleProperty().addListener((obs, old, newVal) -> {
            continueButton.setDisable(newVal == null);
        });
        
        continueButton.setOnAction(e -> {
            ToggleButton selected = (ToggleButton) playerGroup.getSelectedToggle();
            int numPlayers = Integer.parseInt(selected.getText());
            ProcessLog.getInstance().logEvent("Select Player Count", "Number of players: " + numPlayers);
            showPlayerNames(numPlayers);
        });
        
        setupBox.getChildren().addAll(header, instruction, playerCountBox, continueButton);
        
        mainScene = new Scene(setupBox);
        primaryStage.setScene(mainScene);
    }
    
    /**
     * Show player name entry screen
     */
    private void showPlayerNames(int numPlayers) {
        VBox namesBox = new VBox(20);
        namesBox.setAlignment(Pos.CENTER);
        namesBox.setStyle("-fx-background-color: #060CE9;");
        namesBox.setPadding(new Insets(50));
        
        Text header = new Text("ENTER CONTESTANT NAMES");
        header.setFont(Font.font(jeopardyFontFamily, FontWeight.BOLD, 42));
        header.setFill(Color.WHITE);
        
        VBox inputsBox = new VBox(15);
        inputsBox.setAlignment(Pos.CENTER);
        inputsBox.setPadding(new Insets(20));
        
        TextField[] nameFields = new TextField[numPlayers];
        
        for (int i = 0; i < numPlayers; i++) {
            HBox playerBox = new HBox(15);
            playerBox.setAlignment(Pos.CENTER);
            
            Label label = new Label("👤 Contestant " + (i + 1) + ":");
            label.setFont(Font.font(jeopardyFontFamily, FontWeight.BOLD, 20));
            label.setTextFill(Color.GOLD);
            label.setMinWidth(180);
            
            TextField nameField = new TextField();
            nameField.setPromptText("Enter name");
            nameField.setFont(Font.font(jeopardyFontFamily, 18));
            nameField.setPrefWidth(300);
            nameField.setStyle("-fx-background-color: white; -fx-text-fill: #060CE9; " +
                             "-fx-padding: 10; -fx-font-weight: bold;");
            
            nameFields[i] = nameField;
            
            playerBox.getChildren().addAll(label, nameField);
            inputsBox.getChildren().add(playerBox);
        }
        
        Button startGameButton = createStyledButton("START GAME", 200, 55);
        startGameButton.setOnAction(e -> {
            boolean allFilled = true;
            for (TextField field : nameFields) {
                if (field.getText().trim().isEmpty()) {
                    allFilled = false;
                    break;
                }
            }
            
            if (allFilled) {
                for (TextField field : nameFields) {
                    game.addPlayer(field.getText().trim());
                }
                
                try {
                    game.startGame();
                    showGameBoard();
                } catch (Exception ex) {
                    showError("Error starting game: " + ex.getMessage());
                }
            } else {
                showError("Please enter names for all contestants!");
            }
        });
        
        namesBox.getChildren().addAll(header, inputsBox, startGameButton);
        
        ScrollPane scrollPane = new ScrollPane(namesBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #060CE9; -fx-background-color: #060CE9;");
        
        mainScene = new Scene(scrollPane, 1000, 700);
        primaryStage.setScene(mainScene);
    }
    
    /**
     * Show main game board
     */
    private void showGameBoard() {
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #060CE9;");
        
        // Top: Player info
        VBox topBox = new VBox(10);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(20));
        topBox.setStyle("-fx-background-color: #000080;");
        
        Player currentPlayer = game.getCurrentPlayer();
        
        playerLabel = new Label("CONTESTANT: " + currentPlayer.getName().toUpperCase());
        playerLabel.setFont(Font.font(jeopardyFontFamily, FontWeight.BOLD, 32));
        playerLabel.setTextFill(Color.WHITE);
        
        scoreLabel = new Label("SCORE: $" + currentPlayer.getScore());
        scoreLabel.setFont(Font.font(jeopardyFontFamily, FontWeight.BOLD, 28));
        scoreLabel.setTextFill(Color.GOLD);
        
        topBox.getChildren().addAll(playerLabel, scoreLabel);
        
        // Center: Category and value selection or question display
        mainContainer = new VBox(20);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setPadding(new Insets(30));
        
        // Bottom: Status
        HBox bottomBox = new HBox(15);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(15));
        bottomBox.setStyle("-fx-background-color: #000080;");
        
        Button quitButton = createSmallButton("QUIT GAME", 150, 40);
        quitButton.setOnAction(e -> confirmQuit());
        
        statusLabel = new Label("Select a category to continue...");
        statusLabel.setFont(Font.font(jeopardyFontFamily, FontWeight.NORMAL, 16));
        statusLabel.setTextFill(Color.WHITE);
        
        bottomBox.getChildren().addAll(statusLabel, quitButton);
        
        borderPane.setTop(topBox);
        borderPane.setCenter(mainContainer);
        borderPane.setBottom(bottomBox);
        
        mainScene = new Scene(borderPane, 1000, 700);
        primaryStage.setScene(mainScene);
        
        showCategorySelection();
    }
    
    /**
     * Show category selection
     */
    private void showCategorySelection() {
        mainContainer.getChildren().clear();
        
        if (game.isGameComplete()) {
            endGame();
            return;
        }
        
        Set<String> categories = game.getAvailableCategories();
        if (categories.isEmpty()) {
            endGame();
            return;
        }
        
        Text header = new Text("SELECT A CATEGORY");
        header.setFont(Font.font(jeopardyFontFamily, FontWeight.BOLD, 36));
        header.setFill(Color.WHITE);
        
        GridPane categoryGrid = new GridPane();
        categoryGrid.setAlignment(Pos.CENTER);
        categoryGrid.setHgap(20);
        categoryGrid.setVgap(20);
        categoryGrid.setPadding(new Insets(30));
        
        String[] categoryArray = categories.toArray(String[]::new);
        int col = 0;
        int row = 0;
        
        for (String category : categoryArray) {
            Button categoryBtn = new Button(category.toUpperCase());
            categoryBtn.setFont(Font.font(jeopardyFontFamily, FontWeight.BOLD, 22));
            categoryBtn.setPrefSize(400, 100);
            categoryBtn.setStyle("-fx-background-color: #FFD700; -fx-text-fill: #060CE9; " +
                               "-fx-background-radius: 15; -fx-border-color: white; " +
                               "-fx-border-width: 4; -fx-border-radius: 15;");
            
            categoryBtn.setOnMouseEntered(e -> 
                categoryBtn.setStyle("-fx-background-color: #FFA500; -fx-text-fill: white; " +
                                   "-fx-background-radius: 15; -fx-border-color: white; " +
                                   "-fx-border-width: 4; -fx-border-radius: 15;"));
            categoryBtn.setOnMouseExited(e -> 
                categoryBtn.setStyle("-fx-background-color: #FFD700; -fx-text-fill: #060CE9; " +
                                   "-fx-background-radius: 15; -fx-border-color: white; " +
                                   "-fx-border-width: 4; -fx-border-radius: 15;"));
            
            categoryBtn.setOnAction(e -> showValueSelection(category));
            
            categoryGrid.add(categoryBtn, col, row);
            
            col++;
            if (col > 1) {
                col = 0;
                row++;
            }
        }
        
        mainContainer.getChildren().addAll(header, categoryGrid);
        statusLabel.setText("Choose your category...");
    }
    
    /**
     * Show value selection for chosen category
     */
    private void showValueSelection(String category) {
        mainContainer.getChildren().clear();
        
        List<Integer> values = game.getAvailableValues(category);
        if (values.isEmpty()) {
            showCategorySelection();
            return;
        }
        
        Text header = new Text(category.toUpperCase());
        header.setFont(Font.font(jeopardyFontFamily, FontWeight.BOLD, 36));
        header.setFill(Color.GOLD);
        
        Text subHeader = new Text("SELECT POINT VALUE");
        subHeader.setFont(Font.font(jeopardyFontFamily, FontWeight.BOLD, 28));
        subHeader.setFill(Color.WHITE);
        
        HBox valuesBox = new HBox(20);
        valuesBox.setAlignment(Pos.CENTER);
        valuesBox.setPadding(new Insets(30));
        
        for (int value : values) {
            Button valueBtn = new Button("$" + value);
            valueBtn.setFont(Font.font(jeopardyFontFamily, FontWeight.BOLD, 32));
            valueBtn.setPrefSize(150, 150);
            valueBtn.setStyle("-fx-background-color: #FFD700; -fx-text-fill: #060CE9; " +
                            "-fx-background-radius: 20; -fx-border-color: white; " +
                            "-fx-border-width: 5; -fx-border-radius: 20;");
            
            valueBtn.setOnMouseEntered(e -> 
                valueBtn.setStyle("-fx-background-color: #FFA500; -fx-text-fill: white; " +
                                "-fx-background-radius: 20; -fx-border-color: white; " +
                                "-fx-border-width: 5; -fx-border-radius: 20;"));
            valueBtn.setOnMouseExited(e -> 
                valueBtn.setStyle("-fx-background-color: #FFD700; -fx-text-fill: #060CE9; " +
                                "-fx-background-radius: 20; -fx-border-color: white; " +
                                "-fx-border-width: 5; -fx-border-radius: 20;"));
            
            valueBtn.setOnAction(e -> showQuestion(category, value));
            
            valuesBox.getChildren().add(valueBtn);
        }
        
        Button backBtn = createSmallButton("← BACK", 120, 40);
        backBtn.setOnAction(e -> showCategorySelection());
        
        mainContainer.getChildren().addAll(header, subHeader, valuesBox, backBtn);
        statusLabel.setText("Choose your point value...");
    }
    
    /**
     * Show question
     */
    private void showQuestion(String category, int value) {
        Question question = findQuestion(category, value);
        if (question == null) {
            showCategorySelection();
            return;
        }
        
        mainContainer.getChildren().clear();
        
        VBox questionBox = new VBox(25);
        questionBox.setAlignment(Pos.CENTER);
        questionBox.setPadding(new Insets(20));
        questionBox.setMaxWidth(800);
        questionBox.setStyle("-fx-background-color: #000080; -fx-background-radius: 20; " +
                            "-fx-border-color: #FFD700; -fx-border-width: 5; -fx-border-radius: 20;");
        
        Label categoryLabel = new Label(category.toUpperCase() + " - $" + value);
        categoryLabel.setFont(Font.font(jeopardyFontFamily, FontWeight.BOLD, 24));
        categoryLabel.setTextFill(Color.GOLD);
        
        Text questionText = new Text(question.getQuestionText());
        questionText.setFont(Font.font(jeopardyFontFamily, FontWeight.NORMAL, 22));
        questionText.setFill(Color.WHITE);
        questionText.setWrappingWidth(750);
        questionText.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        
        VBox optionsBox = new VBox(15);
        optionsBox.setAlignment(Pos.CENTER);
        optionsBox.setPadding(new Insets(20, 0, 0, 0));
        
        ToggleGroup answerGroup = new ToggleGroup();
        String[] optionKeys = {"A", "B", "C", "D"};
        
        for (String key : optionKeys) {
            String optionText = question.getOptions().get(key);
            if (optionText != null) {
                RadioButton option = new RadioButton("[" + key + "] " + optionText);
                option.setToggleGroup(answerGroup);
                option.setFont(Font.font(jeopardyFontFamily, FontWeight.BOLD, 18));
                option.setTextFill(Color.WHITE);
                option.setUserData(key);
                
                // Custom radio button style
                option.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
                
                optionsBox.getChildren().add(option);
            }
        }
        
        Button submitBtn = createStyledButton("SUBMIT ANSWER", 200, 50);
        submitBtn.setDisable(true);
        
        answerGroup.selectedToggleProperty().addListener((obs, old, newVal) -> {
            submitBtn.setDisable(newVal == null);
        });
        
        submitBtn.setOnAction(e -> {
            RadioButton selected = (RadioButton) answerGroup.getSelectedToggle();
            String answer = (String) selected.getUserData();
            
            Game.TurnResult result = game.playTurn(category, value, answer, defaultStrategy);
            showResult(result);
        });
        
        questionBox.getChildren().addAll(categoryLabel, questionText, optionsBox, submitBtn);
        mainContainer.getChildren().add(questionBox);
        statusLabel.setText("Select your answer and click SUBMIT...");
    }
    
    /**
     * Show answer result
     */
    private void showResult(Game.TurnResult result) {
        mainContainer.getChildren().clear();
        
        VBox resultBox = new VBox(30);
        resultBox.setAlignment(Pos.CENTER);
        resultBox.setPadding(new Insets(40));
        
        boolean correct = result.isCorrect();
        String resultEmoji = correct ? "✅" : "❌";
        String resultText = correct ? "CORRECT!" : "INCORRECT!";
        Color resultColor = correct ? Color.LIGHTGREEN : Color.LIGHTCORAL;
        
        Text resultHeader = new Text(resultEmoji + " " + resultText + " " + resultEmoji);
        resultHeader.setFont(Font.font(jeopardyFontFamily, FontWeight.BOLD, 48));
        resultHeader.setFill(resultColor);
        
        VBox detailsBox = new VBox(15);
        detailsBox.setAlignment(Pos.CENTER);
        detailsBox.setPadding(new Insets(20));
        detailsBox.setStyle("-fx-background-color: rgba(0,0,0,0.5); -fx-background-radius: 15;");
        
        if (!correct) {
            Label correctAnswerLabel = new Label("Correct Answer: " + result.getCorrectAnswer());
            correctAnswerLabel.setFont(Font.font(jeopardyFontFamily, FontWeight.BOLD, 22));
            correctAnswerLabel.setTextFill(Color.WHITE);
            detailsBox.getChildren().add(correctAnswerLabel);
        }
        
        Label pointsLabel = new Label("Points: " + (correct ? "+" : "") + result.getPointsEarned());
        pointsLabel.setFont(Font.font(jeopardyFontFamily, FontWeight.BOLD, 28));
        pointsLabel.setTextFill(Color.GOLD);
        
        Label newScoreLabel = new Label("New Score: $" + result.getNewScore());
        newScoreLabel.setFont(Font.font(jeopardyFontFamily, FontWeight.BOLD, 28));
        newScoreLabel.setTextFill(Color.GOLD);
        
        detailsBox.getChildren().addAll(pointsLabel, newScoreLabel);
        
        Button continueBtn = createStyledButton("CONTINUE", 200, 55);
        continueBtn.setOnAction(e -> {
            // Update player info
            Player currentPlayer = game.getCurrentPlayer();
            playerLabel.setText("CONTESTANT: " + currentPlayer.getName().toUpperCase());
            scoreLabel.setText("SCORE: $" + currentPlayer.getScore());
            
            showCategorySelection();
        });
        
        resultBox.getChildren().addAll(resultHeader, detailsBox, continueBtn);
        mainContainer.getChildren().add(resultBox);
        statusLabel.setText(correct ? "Great job!" : "Better luck next time!");
    }
    
    /**
     * End game and show results
     */
    private void endGame() {
        // End the game properly to finalize state
        game.endGame();
        
        VBox endBox = new VBox(30);
        endBox.setAlignment(Pos.CENTER);
        endBox.setStyle("-fx-background-color: #060CE9;");
        endBox.setPadding(new Insets(50));
        
        Text header = new Text("🏆 GAME OVER 🏆");
        header.setFont(Font.font(jeopardyFontFamily, FontWeight.BOLD, 56));
        header.setFill(Color.GOLD);
        
        Text subHeader = new Text("FINAL RESULTS");
        subHeader.setFont(Font.font(jeopardyFontFamily, FontWeight.BOLD, 36));
        subHeader.setFill(Color.WHITE);
        
        VBox scoresBox = new VBox(15);
        scoresBox.setAlignment(Pos.CENTER);
        scoresBox.setPadding(new Insets(30));
        scoresBox.setStyle("-fx-background-color: rgba(0,0,0,0.3); -fx-background-radius: 15;");
        
        List<Player> rankedPlayers = new ArrayList<>(game.getPlayers());
        rankedPlayers.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));
        
        int rank = 1;
        for (Player player : rankedPlayers) {
            String medal = rank == 1 ? "🥇" : rank == 2 ? "🥈" : rank == 3 ? "🥉" : "  ";
            Label playerScore = new Label(medal + " #" + rank + " - " + 
                                         player.getName() + ": $" + player.getScore());
            playerScore.setFont(Font.font(jeopardyFontFamily, FontWeight.BOLD, 28));
            playerScore.setTextFill(rank == 1 ? Color.GOLD : Color.WHITE);
            scoresBox.getChildren().add(playerScore);
            rank++;
        }
        
        // Report generation options
        VBox reportBox = new VBox(15);
        reportBox.setAlignment(Pos.CENTER);
        reportBox.setPadding(new Insets(20));
        
        Text reportHeader = new Text("📊 GENERATE REPORTS");
        reportHeader.setFont(Font.font(jeopardyFontFamily, FontWeight.BOLD, 24));
        reportHeader.setFill(Color.GOLD);
        
        HBox reportButtons = new HBox(15);
        reportButtons.setAlignment(Pos.CENTER);
        
        Button txtBtn = createSmallButton("TXT Report", 120, 40);
        Button pdfBtn = createSmallButton("PDF Report", 120, 40);
        Button docxBtn = createSmallButton("DOCX Report", 120, 40);
        Button logBtn = createSmallButton("Process Log", 120, 40);
        
        txtBtn.setOnAction(e -> generateReport("txt"));
        pdfBtn.setOnAction(e -> generateReport("pdf"));
        docxBtn.setOnAction(e -> generateReport("docx"));
        logBtn.setOnAction(e -> generateProcessLog());
        
        reportButtons.getChildren().addAll(txtBtn, pdfBtn, docxBtn, logBtn);
        
        Button exitBtn = createStyledButton("EXIT GAME", 180, 50);
        exitBtn.setOnAction(e -> Platform.exit());
        
        reportBox.getChildren().addAll(reportHeader, reportButtons);
        
        endBox.getChildren().addAll(header, subHeader, scoresBox, reportBox, exitBtn);
        
        ScrollPane scrollPane = new ScrollPane(endBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #060CE9; -fx-background-color: #060CE9;");
        
        mainScene = new Scene(scrollPane, 1000, 700);
        primaryStage.setScene(mainScene);
    }
    
    /**
     * Generate report
     */
    private void generateReport(String format) {
        try {
            // Ensure reports directory exists
            java.io.File reportsDir = new java.io.File("src/main/resources/reports");
            if (!reportsDir.exists()) {
                reportsDir.mkdirs();
            }
            
            game.generateSummaryReport(format);
            java.io.File reportFile = new java.io.File("src/main/resources/reports/game_report." + format);
            String absolutePath = reportFile.getAbsolutePath();
            long lastModified = reportFile.lastModified();
            String timestamp = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(lastModified));
            
            showInfo("Report generated successfully!\n\n" +
                    "Format: " + format.toUpperCase() + "\n" +
                    "Location: " + absolutePath + "\n" +
                    "File updated: " + timestamp);
        } catch (IOException e) {
            showError("Error generating report: " + e.getMessage());
        }
    }
    
    /**
     * Generate process mining log
     */
    private void generateProcessLog() {
        try {
            // Ensure reports directory exists
            java.io.File reportsDir = new java.io.File("src/main/resources/reports");
            if (!reportsDir.exists()) {
                reportsDir.mkdirs();
            }
            
            game.generateProcessMiningLog();
            java.io.File logFile = new java.io.File("src/main/resources/reports/game_event_log.csv");
            String absolutePath = logFile.getAbsolutePath();
            long lastModified = logFile.lastModified();
            String timestamp = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(lastModified));
            
            showInfo("Process log generated successfully!\n\n" +
                    "Location: " + absolutePath + "\n" +
                    "File updated: " + timestamp + "\n" +
                    "Events logged: " + ProcessLog.getInstance().getEvents().size());
        } catch (Exception e) {
            showError("Error generating process log: " + e.getMessage());
        }
    }
    
    /**
     * Find question by category and value
     */
    private Question findQuestion(String category, int value) {
        for (Question q : game.getQuestions()) {
            if (q.getCategory().equals(category) && q.getValue() == value) {
                return q;
            }
        }
        return null;
    }
    
    /**
     * Confirm quit - takes user to end game screen
     */
    private void confirmQuit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("End Game");
        alert.setHeaderText("Are you sure you want to end the game?");
        alert.setContentText("You'll be able to view final scores and generate reports.");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                endGame();
            }
        });
    }
    
    /**
     * Show error dialog
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Show info dialog
     */
    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Create styled button
     */
    private Button createStyledButton(String text, double width, double height) {
        Button button = new Button(text);
        button.setFont(Font.font(jeopardyFontFamily, FontWeight.BOLD, 18));
        button.setPrefSize(width, height);
        button.setStyle("-fx-background-color: #FFD700; -fx-text-fill: #060CE9; " +
                       "-fx-background-radius: 10; -fx-border-color: white; " +
                       "-fx-border-width: 3; -fx-border-radius: 10;");
        
        button.setOnMouseEntered(e -> 
            button.setStyle("-fx-background-color: #FFA500; -fx-text-fill: white; " +
                          "-fx-background-radius: 10; -fx-border-color: white; " +
                          "-fx-border-width: 3; -fx-border-radius: 10;"));
        button.setOnMouseExited(e -> 
            button.setStyle("-fx-background-color: #FFD700; -fx-text-fill: #060CE9; " +
                          "-fx-background-radius: 10; -fx-border-color: white; " +
                          "-fx-border-width: 3; -fx-border-radius: 10;"));
        
        return button;
    }
    
    /**
     * Create small styled button
     */
    private Button createSmallButton(String text, double width, double height) {
        Button button = new Button(text);
        button.setFont(Font.font(jeopardyFontFamily, FontWeight.BOLD, 14));
        button.setPrefSize(width, height);
        button.setStyle("-fx-background-color: #FFD700; -fx-text-fill: #060CE9; " +
                       "-fx-background-radius: 8; -fx-border-color: white; " +
                       "-fx-border-width: 2; -fx-border-radius: 8;");
        
        button.setOnMouseEntered(e -> 
            button.setStyle("-fx-background-color: #FFA500; -fx-text-fill: white; " +
                          "-fx-background-radius: 8; -fx-border-color: white; " +
                          "-fx-border-width: 2; -fx-border-radius: 8;"));
        button.setOnMouseExited(e -> 
            button.setStyle("-fx-background-color: #FFD700; -fx-text-fill: #060CE9; " +
                          "-fx-background-radius: 8; -fx-border-color: white; " +
                          "-fx-border-width: 2; -fx-border-radius: 8;"));
        
        return button;
    }
    
    /**
     * Style radio button
     */
    private void styleRadioButton(RadioButton radio) {
        radio.setFont(Font.font(jeopardyFontFamily, FontWeight.BOLD, 18));
        radio.setTextFill(Color.WHITE);
        radio.setStyle("-fx-text-fill: white;");
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
