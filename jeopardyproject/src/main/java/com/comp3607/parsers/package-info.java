/**
 * File parsing strategies for loading questions from various file formats.
 * 
 * <h2>Overview</h2>
 * <p>
 * This package implements the Strategy pattern for parsing question files.
 * Supports CSV, JSON, and XML formats with a unified interface.
 * </p>
 * 
 * <h2>Design Pattern</h2>
 * <p>
 * <b>Strategy Pattern</b>: {@link com.comp3607.parsers.FileParser} defines the interface,
 * while {@link com.comp3607.parsers.CSVParser}, {@link com.comp3607.parsers.JSONParser},
 * and {@link com.comp3607.parsers.XMLParser} provide concrete implementations.
 * {@link com.comp3607.parsers.FileParserFactory} creates appropriate parser instances.
 * </p>
 * 
 * <h2>Supported Formats</h2>
 * <ul>
 *   <li><b>CSV</b>: Comma-separated values with OpenCSV library</li>
 *   <li><b>JSON</b>: JavaScript Object Notation with Jackson library</li>
 *   <li><b>XML</b>: Extensible Markup Language with DOM parser</li>
 * </ul>
 * 
 * @since 1.0
 * @version 1.0
 * @author COMP3607 Group 12
 */
package com.comp3607.parsers;
