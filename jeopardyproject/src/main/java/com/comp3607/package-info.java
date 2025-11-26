/**
 * Core game logic and model classes for the Jeopardy trivia game application.
 * 
 * <h2>Overview</h2>
 * <p>
 * This package contains the main game engine, player management, question handling,
 * and reporting functionality. It demonstrates SOLID principles and multiple design
 * patterns including Singleton, Factory, and Strategy patterns.
 * </p>
 * 
 * <h2>Key Classes</h2>
 * <ul>
 *   <li>{@link com.comp3607.Game} - Central game controller managing game flow and state</li>
 *   <li>{@link com.comp3607.Player} - Player model with score tracking and turn history</li>
 *   <li>{@link com.comp3607.Question} - Immutable question data model</li>
 *   <li>{@link com.comp3607.ProcessLog} - Singleton for process mining event logging</li>
 *   <li>{@link com.comp3607.ReportGenerator} - Multi-format report generation (TXT, PDF, DOCX)</li>
 * </ul>
 * 
 * <h2>Application Entry Points</h2>
 * <ul>
 *   <li>{@link com.comp3607.JeopardyApp} - Console-based application</li>
 *   <li>{@link com.comp3607.JeopardyAppGUI} - JavaFX GUI application</li>
 * </ul>
 * 
 * <h2>Design Patterns</h2>
 * <ul>
 *   <li><b>Singleton</b>: {@link com.comp3607.ProcessLog} ensures single log instance</li>
 *   <li><b>Factory</b>: {@link com.comp3607.QuestionFactory} creates question objects</li>
 *   <li><b>Strategy</b>: Uses {@link com.comp3607.strategies.CategoryStrategy} for flexible answer validation</li>
 * </ul>
 * 
 * @since 1.0
 * @version 1.0
 * @author COMP3607 Group 12
 */
package com.comp3607;
