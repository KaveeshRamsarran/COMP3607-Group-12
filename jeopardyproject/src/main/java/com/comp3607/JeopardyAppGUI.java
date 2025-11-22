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
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.util.Duration;
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
    private Timeline questionTimer;
    
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
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: linear-gradient(#01021A, #060CE9);");
        root.setPadding(new Insets(40));

        Region shimmer = new Region();
        shimmer.setStyle("-fx-background-color: radial-gradient(center 50% 30%, radius 70%, rgba(255,255,255,0.25), transparent);");
        shimmer.setMouseTransparent(true);
        shimmer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        VBox welcomeBox = new VBox(25);
        welcomeBox.setAlignment(Pos.CENTER);
        welcomeBox.setPadding(new Insets(50));
        welcomeBox.setSpacing(25);
        welcomeBox.setMaxWidth(600);
        welcomeBox.setStyle("-fx-background-color: rgba(0,0,0,0.45); -fx-background-radius: 30; " +
                            "-fx-border-color: rgba(255,215,0,0.6); -fx-border-width: 2; -fx-border-radius: 28; " +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 30, 0.2, 0, 10);");
        
        // Title
        Text title = new Text("JEOPARDY!");
        title.setFont(Font.font(jeopardyFontFamily, FontWeight.BOLD, 72));
        title.setFill(Color.WHITE);
        title.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 10, 0, 0, 3);");
        DropShadow titleGlow = new DropShadow();
        titleGlow.setColor(Color.web("#00F0FF"));
        titleGlow.setRadius(25);
        titleGlow.setSpread(0.4);
        title.setEffect(titleGlow);
        
        Text subtitle = new Text("THE ULTIMATE TRIVIA CHALLENGE");
        subtitle.setFont(Font.font(jeopardyFontFamily, FontWeight.BOLD, 24));
        subtitle.setFill(Color.GOLD);
        Glow subtitleGlow = new Glow(0.4);
        subtitle.setEffect(subtitleGlow);

        Text tagline = new Text("Load a dataset, register your contestants, and prove who knows the board best.");
        tagline.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 18));
        tagline.setFill(Color.web("#E0E6FF"));
        tagline.setWrappingWidth(480);
        tagline.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        Region divider = new Region();
        divider.setPrefHeight(4);
        divider.setMaxWidth(200);
        divider.setStyle("-fx-background-color: linear-gradient(to right, transparent, #FFD700, transparent); " +
                 "-fx-background-radius: 2;");

        // Simple animation for home screen text
        ScaleTransition titlePulse = new ScaleTransition(Duration.seconds(1.2), title);
        titlePulse.setFromX(1.0);
        titlePulse.setFromY(1.0);
        titlePulse.setToX(1.08);
        titlePulse.setToY(1.08);
        titlePulse.setAutoReverse(true);
        titlePulse.setCycleCount(Timeline.INDEFINITE);
        titlePulse.play();

        FadeTransition subtitleFade = new FadeTransition(Duration.seconds(1.5), subtitle);
        subtitleFade.setFromValue(0.4);
        subtitleFade.setToValue(1.0);
        subtitleFade.setAutoReverse(true);
        subtitleFade.setCycleCount(Timeline.INDEFINITE);
        subtitleFade.play();
        
        // Start button
        Button startButton = createStyledButton("START GAME", 200, 60);
        startButton.setOnAction(e -> showFileSelection());
        
        welcomeBox.getChildren().addAll(title, subtitle, divider, tagline, startButton);
        
        root.getChildren().addAll(shimmer, welcomeBox);
        StackPane.setAlignment(welcomeBox, Pos.CENTER);
        
        mainScene = new Scene(root);
        primaryStage.setScene(mainScene);
    }
    
    /**
     * Show file selection screen
     */
    private void showFileSelection() {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: linear-gradient(#01021A, #04098C);");
        root.setPadding(new Insets(40));

        VBox fileBox = new VBox(25);
        fileBox.setAlignment(Pos.CENTER);
        fileBox.setPadding(new Insets(50));
        fileBox.setSpacing(20);
        fileBox.setMaxWidth(700);
        fileBox.setStyle("-fx-background-color: rgba(0,0,0,0.45); -fx-background-radius: 30; " +
                         "-fx-border-color: rgba(255,215,0,0.6); -fx-border-width: 2; -fx-border-radius: 28; " +
                         "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 25, 0.2, 0, 12);");
        
        Text header = new Text("GAME SETUP");
        header.setFont(Font.font(jeopardyFontFamily, FontWeight.BOLD, 50));
        header.setFill(Color.WHITE);
        header.setEffect(new DropShadow(20, Color.BLACK));
        
        Text instruction = new Text("📁 Choose your question bank");
        instruction.setFont(Font.font(jeopardyFontFamily, FontWeight.NORMAL, 24));
        instruction.setFill(Color.GOLD);
        instruction.setWrappingWidth(520);
        instruction.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        
        // File path display
        Label filePathLabel = new Label("No file selected");
        filePathLabel.setFont(Font.font(jeopardyFontFamily, 16));
        filePathLabel.setTextFill(Color.WHITE);
        filePathLabel.setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-padding: 14; -fx-background-radius: 12; " +
                       "-fx-border-color: rgba(255,215,0,0.4); -fx-border-radius: 12;");
        filePathLabel.setMinWidth(500);
        
        // File type selection
        HBox fileTypeBox = new HBox(15);
        fileTypeBox.setAlignment(Pos.CENTER);
        fileTypeBox.setStyle("-fx-background-color: rgba(0,0,0,0.35); -fx-padding: 15 25; -fx-background-radius: 15;");
        
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

        Label helperText = new Label("Tip: Sample datasets live in src/main/resources/data to help you get started quickly.");
        helperText.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 16));
        helperText.setTextFill(Color.web("#E0E6FF"));
        helperText.setWrapText(true);
        helperText.setAlignment(Pos.CENTER);
        helperText.setStyle("-fx-background-color: rgba(0,0,0,0.3); -fx-padding: 12 18; -fx-background-radius: 12;");
        helperText.setMaxWidth(520);

        fileBox.getChildren().addAll(header, instruction, filePathLabel, fileTypeBox, buttonBox, helperText);

        root.getChildren().add(fileBox);
        
        mainScene = new Scene(root);
        primaryStage.setScene(mainScene);
    }
    
    /**
     * Show player setup screen
     */
    private void showPlayerSetup() {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: linear-gradient(#01021A, #04116C);");
        root.setPadding(new Insets(40));

        VBox setupBox = new VBox(25);
        setupBox.setAlignment(Pos.CENTER);
        setupBox.setPadding(new Insets(60));
        setupBox.setMaxWidth(650);
        setupBox.setStyle("-fx-background-color: rgba(0,0,0,0.45); -fx-background-radius: 30; " +
                          "-fx-border-color: rgba(255,215,0,0.6); -fx-border-width: 2; -fx-border-radius: 28; " +
                          "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 25, 0.2, 0, 12);");
        
        Text header = new Text("PLAYER REGISTRATION");
        header.setFont(Font.font(jeopardyFontFamily, FontWeight.BOLD, 48));
        header.setFill(Color.WHITE);
        header.setEffect(new DropShadow(20, Color.BLACK));
        
        Text instruction = new Text("👥 How many contestants are stepping up?");
        instruction.setFont(Font.font(jeopardyFontFamily, FontWeight.NORMAL, 24));
        instruction.setFill(Color.GOLD);
        instruction.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        
        // Player count buttons
        HBox playerCountBox = new HBox(15);
        playerCountBox.setAlignment(Pos.CENTER);
        
        ToggleGroup playerGroup = new ToggleGroup();
        List<ToggleButton> playerButtons = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            ToggleButton btn = new ToggleButton("Player " + i);
            btn.setUserData(i); // keep numeric value for logic while showing friendlier text
            btn.setToggleGroup(playerGroup);
            btn.setFont(Font.font(jeopardyFontFamily, FontWeight.BOLD, 26));
            btn.setPrefSize(140, 90);
            btn.setWrapText(true);
            btn.setStyle("-fx-background-color: rgba(255,255,255,0.15); -fx-text-fill: white; " +
                        "-fx-background-radius: 16; -fx-border-color: rgba(255,215,0,0.7); " +
                        "-fx-border-width: 2; -fx-border-radius: 16;");
            
            btn.setOnMouseEntered(ev -> btn.setStyle("-fx-background-color: rgba(255,215,0,0.8); " +
                    "-fx-text-fill: #04116C; -fx-background-radius: 16; -fx-border-color: white; " +
                    "-fx-border-width: 2; -fx-border-radius: 16;"));
            btn.setOnMouseExited(ev -> {
                if (!btn.isSelected()) {
                    btn.setStyle("-fx-background-color: rgba(255,255,255,0.15); -fx-text-fill: white; " +
                            "-fx-background-radius: 16; -fx-border-color: rgba(255,215,0,0.7); " +
                            "-fx-border-width: 2; -fx-border-radius: 16;");
                }
            });

            playerButtons.add(btn);
            playerCountBox.getChildren().add(btn);
        }
        
        Button continueButton = createStyledButton("CONTINUE", 180, 50);
        continueButton.setDisable(true);
        
        playerGroup.selectedToggleProperty().addListener((obs, old, newVal) -> {
            continueButton.setDisable(newVal == null);

            // Ensure only the active button shows the "selected" style
            for (ToggleButton btn : playerButtons) {
                if (btn.equals(newVal)) {
                    btn.setStyle("-fx-background-color: linear-gradient(#FFD700, #FFB347); -fx-text-fill: #04116C; " +
                            "-fx-background-radius: 16; -fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 16;");
                } else {
                    btn.setStyle("-fx-background-color: rgba(255,255,255,0.15); -fx-text-fill: white; " +
                            "-fx-background-radius: 16; -fx-border-color: rgba(255,215,0,0.7); " +
                            "-fx-border-width: 2; -fx-border-radius: 16;");
                }
            }
        });
        
        continueButton.setOnAction(e -> {
            ToggleButton selected = (ToggleButton) playerGroup.getSelectedToggle();
            int numPlayers = (int) selected.getUserData();
            ProcessLog.getInstance().logEvent("Select Player Count", "Number of players: " + numPlayers);
            showPlayerNames(numPlayers);
        });
        
        Label note = new Label("Need more than four? Duplicate contestants to share a score board slot.");
        note.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 16));
        note.setTextFill(Color.web("#E0E6FF"));
        note.setWrapText(true);
        note.setAlignment(Pos.CENTER);
        note.setStyle("-fx-background-color: rgba(0,0,0,0.3); -fx-padding: 10 16; -fx-background-radius: 12;");
        note.setMaxWidth(460);

        setupBox.getChildren().addAll(header, instruction, playerCountBox, continueButton, note);

        root.getChildren().add(setupBox);

        mainScene = new Scene(root);
        primaryStage.setScene(mainScene);
    }
    
    /**
     * Show player name entry screen
     */
    private void showPlayerNames(int numPlayers) {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: linear-gradient(#01021A, #04116C);");
        root.setPadding(new Insets(40));
        
        VBox namesBox = new VBox(28);
        namesBox.setAlignment(Pos.CENTER);
        namesBox.setPadding(new Insets(70));
        namesBox.setSpacing(28);
        namesBox.setMaxWidth(860);
        namesBox.setStyle("-fx-background-color: rgba(0,0,0,0.45); -fx-background-radius: 30; " +
                          "-fx-border-color: rgba(255,215,0,0.6); -fx-border-width: 2; -fx-border-radius: 28; " +
                          "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 25, 0.2, 0, 12);");
        
        Text header = new Text("ENTER CONTESTANT NAMES");
        header.setFont(Font.font(jeopardyFontFamily, FontWeight.BOLD, 46));
        header.setFill(Color.WHITE);
        header.setEffect(new DropShadow(18, Color.BLACK));
        
        VBox inputsBox = new VBox(20);
        inputsBox.setAlignment(Pos.CENTER);
        inputsBox.setPadding(new Insets(10));
        
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
            nameField.setStyle("-fx-background-color: rgba(255,255,255,0.9); -fx-text-fill: #04116C; " +
                             "-fx-padding: 12; -fx-font-weight: bold; -fx-background-radius: 10; " +
                             "-fx-border-radius: 10; -fx-border-color: rgba(0,0,0,0.05);");
            
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
        
        Label helper = new Label("Pro tip: fun team names keep the energy high—feel free to improvise!");
        helper.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 16));
        helper.setTextFill(Color.web("#E0E6FF"));
        helper.setWrapText(true);
        helper.setAlignment(Pos.CENTER);
        helper.setStyle("-fx-background-color: rgba(0,0,0,0.3); -fx-padding: 10 16; -fx-background-radius: 12;");
        helper.setMaxWidth(520);
        
        namesBox.getChildren().addAll(header, inputsBox, startGameButton, helper);

        StackPane namesWrapper = new StackPane(namesBox);
        namesWrapper.setAlignment(Pos.CENTER);
        namesWrapper.setPadding(new Insets(40));

        ScrollPane scrollPane = new ScrollPane(namesWrapper);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setPannable(true);

        // Center the card and allow it to shrink from all sides
        StackPane.setAlignment(scrollPane, Pos.CENTER);
        StackPane.setMargin(scrollPane, new Insets(0));
        root.getChildren().add(scrollPane);

        mainScene = new Scene(root, 1000, 700);
        primaryStage.setScene(mainScene);
    }
    
    /**
     * Show main game board
     */
    private void showGameBoard() {
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: linear-gradient(#01021A, #020D52);");
        
        // Top: Player info
        VBox topBox = new VBox(10);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(20));
        topBox.setStyle("-fx-background-color: rgba(0,0,0,0.55); -fx-background-radius: 0 0 25 25; " +
                        "-fx-border-color: rgba(255,215,0,0.4); -fx-border-width: 0 0 2 0;");
        
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
        bottomBox.setStyle("-fx-background-color: rgba(0,0,0,0.55); -fx-background-radius: 25 25 0 0; " +
                   "-fx-border-color: rgba(255,215,0,0.4); -fx-border-width: 2 0 0 0;");
        
        Button quitButton = createSmallButton("QUIT GAME", 150, 40);
        quitButton.setOnAction(e -> confirmQuit());
        
        statusLabel = new Label("Select a category to continue...");
        statusLabel.setFont(Font.font(jeopardyFontFamily, FontWeight.NORMAL, 18));
        statusLabel.setTextFill(Color.WHITE);
        statusLabel.setStyle("-fx-background-color: rgba(0,0,0,0.35); -fx-padding: 8 16; -fx-background-radius: 16;");
        
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
        header.setEffect(new DropShadow(18, Color.BLACK));
        
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
            categoryBtn.setStyle("-fx-background-color: rgba(255,255,255,0.15); -fx-text-fill: white; " +
                               "-fx-background-radius: 18; -fx-border-color: rgba(255,215,0,0.8); " +
                               "-fx-border-width: 3; -fx-border-radius: 18; -fx-padding: 10;");
            
            categoryBtn.setOnMouseEntered(e -> 
                categoryBtn.setStyle("-fx-background-color: linear-gradient(#FFD700, #FFB347); -fx-text-fill: #020D52; " +
                                   "-fx-background-radius: 18; -fx-border-color: white; -fx-border-width: 3; -fx-border-radius: 18;"));
            categoryBtn.setOnMouseExited(e -> 
                categoryBtn.setStyle("-fx-background-color: rgba(255,255,255,0.15); -fx-text-fill: white; " +
                                   "-fx-background-radius: 18; -fx-border-color: rgba(255,215,0,0.8); -fx-border-width: 3; -fx-border-radius: 18; -fx-padding: 10;"));
            
            categoryBtn.setOnAction(e -> showValueSelection(category));
            
            categoryGrid.add(categoryBtn, col, row);
            
            col++;
            if (col > 1) {
                col = 0;
                row++;
            }
        }
        
        VBox boardCard = new VBox(15, header, categoryGrid);
        boardCard.setAlignment(Pos.CENTER);
        boardCard.setStyle("-fx-background-color: rgba(0,0,0,0.45); -fx-background-radius: 30; " +
                           "-fx-border-color: rgba(255,215,0,0.5); -fx-border-width: 2; -fx-border-radius: 28; " +
                           "-fx-padding: 30; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 20, 0.2, 0, 8);");
        mainContainer.getChildren().add(boardCard);
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
        header.setEffect(new DropShadow(15, Color.BLACK));
        
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
            valueBtn.setStyle("-fx-background-color: rgba(255,255,255,0.15); -fx-text-fill: white; " +
                            "-fx-background-radius: 20; -fx-border-color: rgba(255,215,0,0.8); -fx-border-width: 4; -fx-border-radius: 20;");
            
            valueBtn.setOnMouseEntered(e -> 
                valueBtn.setStyle("-fx-background-color: linear-gradient(#FFD700, #FFB347); -fx-text-fill: #020D52; " +
                                "-fx-background-radius: 20; -fx-border-color: white; -fx-border-width: 4; -fx-border-radius: 20;"));
            valueBtn.setOnMouseExited(e -> 
                valueBtn.setStyle("-fx-background-color: rgba(255,255,255,0.15); -fx-text-fill: white; " +
                                "-fx-background-radius: 20; -fx-border-color: rgba(255,215,0,0.8); -fx-border-width: 4; -fx-border-radius: 20;"));
            
            valueBtn.setOnAction(e -> showQuestion(category, value));
            
            valuesBox.getChildren().add(valueBtn);
        }
        
        Button backBtn = createSmallButton("← BACK", 120, 40);
        backBtn.setOnAction(e -> showCategorySelection());
        
        VBox valueCard = new VBox(18, header, subHeader, valuesBox, backBtn);
        valueCard.setAlignment(Pos.CENTER);
        valueCard.setStyle("-fx-background-color: rgba(0,0,0,0.45); -fx-background-radius: 30; " +
                           "-fx-border-color: rgba(255,215,0,0.5); -fx-border-width: 2; -fx-border-radius: 28; " +
                           "-fx-padding: 30; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 20, 0.2, 0, 8);");
        mainContainer.getChildren().add(valueCard);
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
        questionBox.setPadding(new Insets(30));
        questionBox.setMaxWidth(820);
        questionBox.setStyle("-fx-background-color: rgba(0,0,0,0.55); -fx-background-radius: 30; " +
                    "-fx-border-color: rgba(255,215,0,0.6); -fx-border-width: 2; -fx-border-radius: 28; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 25, 0.2, 0, 12);");
        
        Label categoryLabel = new Label(category.toUpperCase() + " - $" + value);
        categoryLabel.setFont(Font.font(jeopardyFontFamily, FontWeight.BOLD, 24));
        categoryLabel.setTextFill(Color.GOLD);
        
        Text questionText = new Text(question.getQuestionText());
        questionText.setFont(Font.font(jeopardyFontFamily, FontWeight.NORMAL, 22));
        questionText.setFill(Color.WHITE);
        questionText.setWrappingWidth(750);
        questionText.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        // Timer display
        Label timerLabel = new Label("🕒 30s");
        timerLabel.setFont(Font.font(jeopardyFontFamily, FontWeight.BOLD, 22));
        timerLabel.setTextFill(Color.LIGHTGREEN);
        timerLabel.setStyle("-fx-background-color: rgba(0,0,0,0.35); -fx-padding: 8 16; -fx-background-radius: 20;");

        ProgressBar timerBar = new ProgressBar(1.0);
        timerBar.setPrefWidth(360);
        timerBar.setStyle("-fx-accent: #56F89A; -fx-control-inner-background: rgba(255,255,255,0.2);");
        
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
            stopQuestionTimer();
            RadioButton selected = (RadioButton) answerGroup.getSelectedToggle();
            String answer = (String) selected.getUserData();
            
            Game.TurnResult result = game.playTurn(category, value, answer, defaultStrategy);
            showResult(result);
        });

        // Start 30-second countdown timer
        startQuestionTimer(timerLabel, timerBar, () -> {
            submitBtn.setDisable(true);
            statusLabel.setText("Time's up! Moving on...");
            Game.TurnResult result = game.playTurn(category, value, "", defaultStrategy);
            showResult(result);
        });

        HBox actionRow = new HBox(20, timerLabel, submitBtn);
        actionRow.setAlignment(Pos.CENTER);

        questionBox.getChildren().addAll(categoryLabel, timerBar, questionText, optionsBox, actionRow);
        mainContainer.getChildren().add(questionBox);
        statusLabel.setText("Select your answer and click SUBMIT...");
    }

    /**
     * Start a 30-second countdown for the current question
     */
    private void startQuestionTimer(Label timerLabel, ProgressBar timerBar, Runnable onTimeout) {
        stopQuestionTimer();

        final int totalSeconds = 30;
        final int[] remaining = {totalSeconds};
        timerLabel.setText("🕒 " + remaining[0] + "s");
        timerBar.setProgress(1.0);

        questionTimer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            remaining[0]--;
            timerLabel.setText("🕒 " + Math.max(remaining[0], 0) + "s");
            double progress = Math.max(0, remaining[0] / (double) totalSeconds);
            timerBar.setProgress(progress);

            if (remaining[0] <= 5) {
                timerLabel.setTextFill(Color.web("#FF6161"));
                timerBar.setStyle("-fx-accent: #FF6161; -fx-control-inner-background: rgba(255,255,255,0.2);");
            } else if (remaining[0] <= 10) {
                timerLabel.setTextFill(Color.web("#FFC857"));
                timerBar.setStyle("-fx-accent: #FFC857; -fx-control-inner-background: rgba(255,255,255,0.2);");
            } else {
                timerLabel.setTextFill(Color.LIGHTGREEN);
                timerBar.setStyle("-fx-accent: #56F89A; -fx-control-inner-background: rgba(255,255,255,0.2);");
            }

            if (remaining[0] <= 0) {
                stopQuestionTimer();
                onTimeout.run();
            }
        }));
        questionTimer.setCycleCount(totalSeconds);
        questionTimer.playFromStart();
    }

    /**
     * Stop and clear any active question timer
     */
    private void stopQuestionTimer() {
        if (questionTimer != null) {
            questionTimer.stop();
            questionTimer = null;
        }
    }
    
    /**
     * Show answer result
     */
    private void showResult(Game.TurnResult result) {
        mainContainer.getChildren().clear();
        
        VBox resultBox = new VBox(30);
        resultBox.setAlignment(Pos.CENTER);
        resultBox.setPadding(new Insets(40));
        resultBox.setStyle("-fx-background-color: rgba(0,0,0,0.55); -fx-background-radius: 30; " +
                   "-fx-border-color: rgba(255,215,0,0.6); -fx-border-width: 2; -fx-border-radius: 28; " +
                   "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 25, 0.2, 0, 12);");
        
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
        detailsBox.setStyle("-fx-background-color: rgba(0,0,0,0.35); -fx-background-radius: 18; -fx-border-color: rgba(255,255,255,0.1); -fx-border-width: 1;");
        
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
        
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: linear-gradient(#01021A, #020D52);");
        root.setPadding(new Insets(40));

        VBox endBox = new VBox(30);
        endBox.setAlignment(Pos.CENTER);
        endBox.setPadding(new Insets(60));
        endBox.setMaxWidth(880);
        endBox.setStyle("-fx-background-color: rgba(0,0,0,0.55); -fx-background-radius: 30; " +
                "-fx-border-color: rgba(255,215,0,0.8); -fx-border-width: 2; -fx-border-radius: 28; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 25, 0.2, 0, 12);");

        Text header = new Text("🏆 GAME OVER 🏆");
        header.setFont(Font.font(jeopardyFontFamily, FontWeight.BOLD, 60));
        header.setFill(Color.GOLD);
        header.setEffect(new DropShadow(25, Color.BLACK));

        Text subHeader = new Text("FINAL RESULTS");
        subHeader.setFont(Font.font(jeopardyFontFamily, FontWeight.BOLD, 40));
        subHeader.setFill(Color.WHITE);

        VBox scoresBox = new VBox(12);
        scoresBox.setAlignment(Pos.CENTER);
        scoresBox.setPadding(new Insets(22));
        scoresBox.setStyle("-fx-background-color: rgba(0,0,0,0.35); -fx-background-radius: 20;");
        
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
        
        // Scrollable container to handle any player count gracefully
        // Report generation options
        VBox reportBox = new VBox(12);
        reportBox.setAlignment(Pos.CENTER);
        reportBox.setPadding(new Insets(20));
        reportBox.setStyle("-fx-background-color: rgba(0,0,0,0.35); -fx-background-radius: 20; -fx-border-color: rgba(255,255,255,0.05); -fx-border-width: 1;");
        
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

        StackPane.setAlignment(endBox, Pos.CENTER);
        root.getChildren().add(endBox);

        mainScene = new Scene(root, 1000, 700);
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
