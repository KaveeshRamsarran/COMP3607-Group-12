# COMP3607-Group-12
A multi-player Jeopardy game built in Java, supporting 1-4 players on a local device. It loads questions from CSV, JSON, or XML, tracks scores, logs interactions for process mining, and generates detailed reports. The project uses design patterns, SOLID principles, and includes automated JUnit tests for functionality.

## Features

Local multiplayer (1-4 players)

Data loading from CSV, JSON, or XML

Turn-based gameplay with categories and questions

Scoring system with dynamic updates

Generates game summary reports (PDF, TXT, DOCX)

Process mining log with detailed event tracking

Implementation of design patterns (Factory, Observer, Strategy)

Follows SOLID principles for clean, maintainable code

JUnit tests for core functionality

## Technologies

Java: Core game logic and UI

JUnit: Automated testing

Maven: Build management

CSV/JSON/XML: Supported file formats for question data

## Installation & Setup

Clone the repository:

git clone https://github.com/your-username/jeopardy-game.git


Navigate to the project directory:

cd jeopardy-game


Build the project using Maven:

mvn clean install


Run the game:

mvn exec:java

## Usage

Follow the on-screen prompts to select the number of players, load the game data, and begin playing.

Players take turns selecting categories and answering questions.

At the end of the game, a summary report is generated, and a process mining log is saved.


