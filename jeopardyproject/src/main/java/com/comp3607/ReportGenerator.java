package com.comp3607;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ReportGenerator - Strategy Pattern for different report formats
 * Generates comprehensive game summary reports
 */
public class ReportGenerator {
    private static final Logger LOGGER = Logger.getLogger(ReportGenerator.class.getName());
    private static final String REPORTS_DIR = "src/main/resources/reports/";
    
    /** Default constructor */
    public ReportGenerator() {}
    
    /**
     * Ensure reports directory exists
     */
    private void ensureReportsDirectoryExists() {
        File dir = new File(REPORTS_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
            LOGGER.log(Level.INFO, "Created reports directory: {0}", REPORTS_DIR);
        }
    }
    
    /**
     * Generate report in specified format
     * @param players List of players
     * @param format Report format (txt, pdf, docx)
     * @throws IOException If report cannot be generated
     */
    public void generateReport(List<Player> players, String format) throws IOException {
        ensureReportsDirectoryExists();
        switch (format.toLowerCase()) {
            case "txt":
                generateTXTReport(players);
                break;
            case "pdf":
                generatePDFReport(players);
                break;
            case "docx":
                generateDOCXReport(players);
                break;
            default:
                throw new IllegalArgumentException("Unsupported format: " + format);
        }
    }
    
    /**
     * Generate TXT report with full turn-by-turn details
     * @param players List of players
     * @throws IOException If report cannot be generated
     */
    public void generateTXTReport(List<Player> players) throws IOException {
        String filename = REPORTS_DIR + "game_report.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("=====================================\n");
            writer.write("    JEOPARDY GAME SUMMARY REPORT     \n");
            writer.write("=====================================\n");
            writer.write("Generated: " + LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n\n");
            
            // Final Scores
            writer.write("FINAL SCORES:\n");
            writer.write("-------------------------------------\n");
            players.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));
            for (int i = 0; i < players.size(); i++) {
                Player player = players.get(i);
                writer.write(String.format("%d. %s: %d points\n", 
                    i + 1, player.getName(), player.getScore()));
            }
            writer.write("\n");
            
            // Turn-by-turn details for each player
            writer.write("DETAILED TURN-BY-TURN BREAKDOWN:\n");
            writer.write("=====================================\n\n");
            
            for (Player player : players) {
                writer.write("Player: " + player.getName() + "\n");
                writer.write("-------------------------------------\n");
                
                List<Player.TurnRecord> turns = player.getTurnHistory();
                for (int i = 0; i < turns.size(); i++) {
                    Player.TurnRecord turn = turns.get(i);
                    writer.write(String.format("Turn %d:\n", i + 1));
                    writer.write(String.format("  Category: %s\n", turn.getCategory()));
                    writer.write(String.format("  Question Value: %d points\n", turn.getQuestionValue()));
                    writer.write(String.format("  Question: %s\n", turn.getQuestionText()));
                    writer.write(String.format("  Given Answer: %s\n", turn.getGivenAnswer()));
                    writer.write(String.format("  Result: %s\n", turn.isCorrect() ? "CORRECT" : "INCORRECT"));
                    writer.write(String.format("  Points Earned: %+d\n", turn.getPointsEarned()));
                    writer.write(String.format("  Running Total: %d\n\n", turn.getRunningTotal()));
                }
                writer.write("\n");
            }
            
            LOGGER.log(Level.INFO, "TXT report generated: {0}", filename);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error generating TXT report", e);
            throw e;
        }
    }
    
    /**
     * Generate PDF report with full turn-by-turn details
     * @param players List of players
     * @throws IOException If report cannot be generated
     */
    public void generatePDFReport(List<Player> players) throws IOException {
        String filename = REPORTS_DIR + "game_report.pdf";
        
        // Delete existing file to ensure fresh generation
        File pdfFile = new File(filename);
        if (pdfFile.exists()) {
            pdfFile.delete();
        }
        
        PDPageContentStream contentStream = null;
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            
            float yPosition = 750;
            contentStream = new PDPageContentStream(document, page);
            
            // Title
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, yPosition);
            contentStream.showText("JEOPARDY GAME SUMMARY REPORT");
            contentStream.endText();
            yPosition -= 30;
            
            // Date
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, yPosition);
            contentStream.showText("Generated: " + LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            contentStream.endText();
            yPosition -= 30;
            
            // Final Scores
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, yPosition);
            contentStream.showText("FINAL SCORES:");
            contentStream.endText();
            yPosition -= 20;
            
            contentStream.setFont(PDType1Font.HELVETICA, 11);
            players.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));
            for (int i = 0; i < players.size(); i++) {
                Player player = players.get(i);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, yPosition);
                contentStream.showText(String.format("%d. %s: %d points", 
                    i + 1, player.getName(), player.getScore()));
                contentStream.endText();
                yPosition -= 15;
            }
            yPosition -= 20;
            
            // Turn-by-turn breakdown
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, yPosition);
            contentStream.showText("DETAILED TURN-BY-TURN BREAKDOWN:");
            contentStream.endText();
            yPosition -= 25;
            
            contentStream.setFont(PDType1Font.HELVETICA, 10);
            
            for (Player player : players) {
                // Check if we need a new page
                if (yPosition < 100) {
                    contentStream.close();
                    page = new PDPage();
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page);
                    yPosition = 750;
                }
                
                // Player name
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 11);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, yPosition);
                contentStream.showText("Player: " + player.getName());
                contentStream.endText();
                yPosition -= 18;
                
                contentStream.setFont(PDType1Font.HELVETICA, 9);
                List<Player.TurnRecord> turns = player.getTurnHistory();
                for (int i = 0; i < turns.size(); i++) {
                    Player.TurnRecord turn = turns.get(i);
                    
                    // Check space for turn details (need ~110 points for full turn)
                    if (yPosition < 120) {
                        contentStream.close();
                        page = new PDPage();
                        document.addPage(page);
                        contentStream = new PDPageContentStream(document, page);
                        yPosition = 750;
                    }
                    
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, yPosition);
                    contentStream.showText(String.format("Turn %d:", i + 1));
                    contentStream.endText();
                    yPosition -= 13;
                    
                    contentStream.beginText();
                    contentStream.newLineAtOffset(60, yPosition);
                    contentStream.showText(String.format("Category: %s", turn.getCategory()));
                    contentStream.endText();
                    yPosition -= 13;
                    
                    contentStream.beginText();
                    contentStream.newLineAtOffset(60, yPosition);
                    contentStream.showText(String.format("Question Value: %d points", turn.getQuestionValue()));
                    contentStream.endText();
                    yPosition -= 13;
                    
                    // Question text (truncate if too long)
                    String questionText = turn.getQuestionText();
                    if (questionText.length() > 75) {
                        questionText = questionText.substring(0, 72) + "...";
                    }
                    contentStream.beginText();
                    contentStream.newLineAtOffset(60, yPosition);
                    contentStream.showText("Question: " + questionText);
                    contentStream.endText();
                    yPosition -= 13;
                    
                    // Given answer (truncate if too long)
                    String givenAnswer = turn.getGivenAnswer();
                    if (givenAnswer.length() > 70) {
                        givenAnswer = givenAnswer.substring(0, 67) + "...";
                    }
                    contentStream.beginText();
                    contentStream.newLineAtOffset(60, yPosition);
                    contentStream.showText("Given Answer: " + givenAnswer);
                    contentStream.endText();
                    yPosition -= 13;
                    
                    contentStream.beginText();
                    contentStream.newLineAtOffset(60, yPosition);
                    contentStream.showText(String.format("Result: %s", turn.isCorrect() ? "CORRECT" : "INCORRECT"));
                    contentStream.endText();
                    yPosition -= 13;
                    
                    contentStream.beginText();
                    contentStream.newLineAtOffset(60, yPosition);
                    contentStream.showText(String.format("Points Earned: %+d", turn.getPointsEarned()));
                    contentStream.endText();
                    yPosition -= 13;
                    
                    contentStream.beginText();
                    contentStream.newLineAtOffset(60, yPosition);
                    contentStream.showText(String.format("Running Total: %d", turn.getRunningTotal()));
                    contentStream.endText();
                    yPosition -= 20;
                }
                yPosition -= 10;
            }
            
            contentStream.close();
            document.save(filename);
            LOGGER.log(Level.INFO, "PDF report generated: {0}", filename);
        } catch (IOException e) {
            try {
                if (contentStream != null) {
                    contentStream.close();
                }
            } catch (IOException ex) {
                LOGGER.log(Level.WARNING, "Error closing content stream", ex);
            }
            LOGGER.log(Level.SEVERE, "Error generating PDF report", e);
            throw e;
        }
    }
    
    /**
     * Generate DOCX report
     * @param players List of players
     * @throws IOException If report cannot be generated
     */
    public void generateDOCXReport(List<Player> players) throws IOException {
        String filename = REPORTS_DIR + "game_report.docx";
        
        // Delete existing file to ensure fresh generation
        File docxFile = new File(filename);
        if (docxFile.exists()) {
            docxFile.delete();
        }
        
        try (XWPFDocument document = new XWPFDocument()) {
            // Title
            XWPFParagraph title = document.createParagraph();
            title.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleRun = title.createRun();
            titleRun.setText("JEOPARDY GAME SUMMARY REPORT");
            titleRun.setBold(true);
            titleRun.setFontSize(18);
            
            // Date
            XWPFParagraph date = document.createParagraph();
            XWPFRun dateRun = date.createRun();
            dateRun.setText("Generated: " + LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            dateRun.addBreak();
            
            // Final Scores
            XWPFParagraph scoresTitle = document.createParagraph();
            XWPFRun scoresTitleRun = scoresTitle.createRun();
            scoresTitleRun.setText("FINAL SCORES:");
            scoresTitleRun.setBold(true);
            scoresTitleRun.setFontSize(14);
            
            players.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));
            for (int i = 0; i < players.size(); i++) {
                Player player = players.get(i);
                XWPFParagraph scorePara = document.createParagraph();
                XWPFRun scoreRun = scorePara.createRun();
                scoreRun.setText(String.format("%d. %s: %d points", 
                    i + 1, player.getName(), player.getScore()));
            }
            
            // Turn-by-turn details
            XWPFParagraph detailsTitle = document.createParagraph();
            XWPFRun detailsTitleRun = detailsTitle.createRun();
            detailsTitleRun.addBreak();
            detailsTitleRun.setText("DETAILED TURN-BY-TURN BREAKDOWN:");
            detailsTitleRun.setBold(true);
            detailsTitleRun.setFontSize(14);
            
            for (Player player : players) {
                XWPFParagraph playerName = document.createParagraph();
                XWPFRun playerNameRun = playerName.createRun();
                playerNameRun.addBreak();
                playerNameRun.setText("Player: " + player.getName());
                playerNameRun.setBold(true);
                
                List<Player.TurnRecord> turns = player.getTurnHistory();
                for (int i = 0; i < turns.size(); i++) {
                    Player.TurnRecord turn = turns.get(i);
                    XWPFParagraph turnPara = document.createParagraph();
                    XWPFRun turnRun = turnPara.createRun();
                    turnRun.setText(String.format("Turn %d:", i + 1));
                    turnRun.addBreak();
                    turnRun.setText(String.format("  Category: %s", turn.getCategory()));
                    turnRun.addBreak();
                    turnRun.setText(String.format("  Question Value: %d points", turn.getQuestionValue()));
                    turnRun.addBreak();
                    turnRun.setText(String.format("  Question: %s", turn.getQuestionText()));
                    turnRun.addBreak();
                    turnRun.setText(String.format("  Given Answer: %s", turn.getGivenAnswer()));
                    turnRun.addBreak();
                    turnRun.setText(String.format("  Result: %s", turn.isCorrect() ? "CORRECT" : "INCORRECT"));
                    turnRun.addBreak();
                    turnRun.setText(String.format("  Points Earned: %+d", turn.getPointsEarned()));
                    turnRun.addBreak();
                    turnRun.setText(String.format("  Running Total: %d", turn.getRunningTotal()));
                }
            }
            
            try (FileOutputStream out = new FileOutputStream(filename)) {
                document.write(out);
            }
            
            LOGGER.log(Level.INFO, "DOCX report generated: {0}", filename);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error generating DOCX report", e);
            throw e;
        }
    }
}
