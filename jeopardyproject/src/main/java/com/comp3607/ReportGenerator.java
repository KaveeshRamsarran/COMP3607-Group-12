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
     */
    public void generateTXTReport(List<Player> players) {
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
            throw new RuntimeException("Failed to generate TXT report", e);
        }
    }
    
    /**
     * Generate PDF report
     */
    public void generatePDFReport(List<Player> players) throws IOException {
        String filename = REPORTS_DIR + "game_report.pdf";
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText("JEOPARDY GAME SUMMARY REPORT");
                contentStream.endText();
                
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 720);
                contentStream.showText("Generated: " + LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                contentStream.endText();
                
                // Final Scores
                float yPosition = 680;
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
            }
            
            document.save(filename);
            LOGGER.log(Level.INFO, "PDF report generated: {0}", filename);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error generating PDF report", e);
            throw e;
        }
    }
    
    /**
     * Generate DOCX report
     */
    public void generateDOCXReport(List<Player> players) throws IOException {
        String filename = REPORTS_DIR + "game_report.docx";
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
