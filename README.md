# Jeopardy Game - COMP3607 Group 12

## Project Overview
A Multi-Player Jeopardy Game application developed in Java that supports 1-4 players in turn-based gameplay. The game loads content from CSV, JSON, or XML files and generates comprehensive reports and process mining event logs.

## Features
- Support for 1-4 players on a single device
- Load game data from CSV, JSON, or XML formats
- Turn-based gameplay with question selection by category and value
- Automatic scoring with correct/incorrect answer tracking
- Prevention of question reuse
- Comprehensive summary reports (TXT, PDF, DOCX formats)
- Complete process mining event log in CSV format
- Implementation of 3+ design patterns (Factory, Strategy, Template Method)
- Full adherence to SOLID principles
- JUnit test coverage
- Maven build system

## Technologies Used
- **Java 11**
- **Maven** (build automation)
- **JUnit 5** (testing)
- **OpenCSV** (CSV parsing)
- **Jackson** (JSON parsing)
- **JAXB** (XML parsing)
- **Apache PDFBox** (PDF report generation)
- **Apache POI** (DOCX report generation)

## Project Structure
```
jeopardyproject/
├── src/
│   ├── main/
│   │   ├── java/com/comp3607/
│   │   │   ├── Game.java                    # Main game controller
│   │   │   ├── Player.java                  # Player entity
│   │   │   ├── Question.java                # Question entity
│   │   │   ├── ProcessLog.java              # Event logging (Singleton)
│   │   │   ├── ReportGenerator.java         # Report generation
│   │   │   ├── JeopardyApp.java             # Main application entry point
│   │   │   ├── QuestionFactory.java         # Question creation
│   │   │   ├── parsers/
│   │   │   │   ├── FileParser.java          # Parser interface
│   │   │   │   ├── CSVParser.java           # CSV implementation
│   │   │   │   ├── JSONParser.java          # JSON implementation
│   │   │   │   ├── XMLParser.java           # XML implementation
│   │   │   │   └── FileParserFactory.java   # Factory pattern
│   │   │   └── strategies/
│   │   │       ├── CategoryStrategy.java    # Strategy interface
│   │   │       ├── VariableStrategy.java    # Concrete strategy
│   │   │       └── ControlStructureStrategy.java
│   │   └── resources/
│   │       ├── data/                        # Sample game files
│   │       └── reports/                     # Generated reports
│   └── test/
│       └── java/com/comp3607/
│           ├── GameTest.java
│           ├── PlayerTest.java
│           ├── QuestionTest.java
│           ├── ProcessLogTest.java
│           ├── CategoryStrategyTest.java
│           └── ReportGeneratorTest.java
├── pom.xml                                   # Maven configuration
├── README.md
├── SOLID_PRINCIPLES.md                       # SOLID documentation
└── DESIGN_PATTERNS.md                        # Design patterns documentation
```
## Additional Documentation
- [SOLID_PRINCIPLES.md](SOLID_PRINCIPLES.md) - Detailed SOLID principles implementation
- [DESIGN_PATTERNS.md](DESIGN_PATTERNS.md) - Design patterns documentation
A multi-player Jeopardy game built in Java, supporting 1-4 players on a local device. It loads questions from CSV, JSON, or XML, tracks scores, logs interactions for process mining, and generates detailed reports. The project uses design patterns, SOLID principles, and includes automated JUnit tests for functionality.

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


