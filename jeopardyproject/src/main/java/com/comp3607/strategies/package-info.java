/**
 * Strategy pattern implementations for answer validation and scoring.
 * 
 * <h2>Overview</h2>
 * <p>
 * This package provides different strategies for validating player answers
 * and calculating points based on question categories or difficulty levels.
 * </p>
 * 
 * <h2>Design Pattern</h2>
 * <p>
 * <b>Strategy Pattern</b>: {@link com.comp3607.strategies.CategoryStrategy} defines
 * the interface for answer validation and point calculation. Concrete implementations
 * can apply different rules for different question categories.
 * </p>
 * 
 * <h2>Available Strategies</h2>
 * <ul>
 *   <li>{@link com.comp3607.strategies.VariableStrategy} - Standard validation and scoring</li>
 *   <li>{@link com.comp3607.strategies.ControlStructureStrategy} - Control structure questions</li>
 * </ul>
 * 
 * @since 1.0
 * @version 1.0
 * @author COMP3607 Group 12
 */
package com.comp3607.strategies;
