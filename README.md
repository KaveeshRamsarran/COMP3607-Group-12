# Jeopardy Game - COMP3607 Group 12

## Project Overview
A Multi-Player Jeopardy Game application developed in Java that supports 1-4 players in turn-based gameplay. The game loads content from CSV, JSON, or XML files and generates comprehensive reports and process mining event logs.

## Features
- ✅ Support for 1-4 players on a single device
- ✅ Load game data from CSV, JSON, or XML formats
- ✅ Turn-based gameplay with question selection by category and value
- ✅ Automatic scoring with correct/incorrect answer tracking
- ✅ Prevention of question reuse
- ✅ Comprehensive summary reports (TXT, PDF, DOCX formats)
- ✅ Complete process mining event log in CSV format
- ✅ Implementation of 3+ design patterns (Factory, Strategy, Template Method)
- ✅ Full adherence to SOLID principles
- ✅ JUnit test coverage
- ✅ Maven build system

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

## Design Patterns Implemented

### 1. Factory Pattern
- **Class**: `FileParserFactory`
- **Purpose**: Creates appropriate file parser based on file type
- **Benefit**: Encapsulates object creation and allows easy extension

### 2. Strategy Pattern
- **Classes**: `CategoryStrategy`, `VariableStrategy`, `ControlStructureStrategy`
- **Purpose**: Different validation/scoring algorithms for different question categories
- **Benefit**: Flexible behavior selection at runtime

### 3. Singleton Pattern
- **Class**: `ProcessLog`
- **Purpose**: Single instance for centralized event logging
- **Benefit**: Consistent logging across the application

### 4. Template Method Pattern
- **Class**: `ReportGenerator`
- **Purpose**: Common report structure with format-specific implementations
- **Benefit**: Code reuse and consistent report structure

See [DESIGN_PATTERNS.md](DESIGN_PATTERNS.md) for detailed documentation.

## SOLID Principles

All five SOLID principles are implemented:

1. **Single Responsibility Principle (SRP)**: Each class has one clear responsibility
2. **Open/Closed Principle (OCP)**: Extensions through interfaces without modification
3. **Liskov Substitution Principle (LSP)**: Implementations are interchangeable
4. **Interface Segregation Principle (ISP)**: Focused, minimal interfaces
5. **Dependency Inversion Principle (DIP)**: Dependencies on abstractions

See [SOLID_PRINCIPLES.md](SOLID_PRINCIPLES.md) for detailed explanations.

## Building the Project

### Prerequisites
- Java 11 or higher
- Maven 3.6 or higher

### Build Commands
```bash
# Clean and compile
mvn clean compile

# Run tests
mvn test

# Package application
mvn package

# Run the application
mvn exec:java -Dexec.mainClass="com.comp3607.JeopardyApp"
```

## Running the Game

### Option 1: Using Maven
```bash
mvn exec:java -Dexec.mainClass="com.comp3607.JeopardyApp"
```

### Option 2: Using Java directly
```bash
# From project root
java -cp target/classes com.comp3607.JeopardyApp
```

### Option 3: From IDE
Run the `JeopardyApp.java` main method

## Game File Formats

### CSV Format
```csv
Category,Value,Question,OptionA,OptionB,OptionC,OptionD,CorrectAnswer
Variables,100,What is an int?,A type,A variable,A function,A class,A
```

### JSON Format
```json
{
  "questions": [
    {
      "category": "Variables",
      "value": 100,
      "question": "What is an int?",
      "options": {
        "A": "A type",
        "B": "A variable",
        "C": "A function",
        "D": "A class"
      },
      "correctAnswer": "A"
    }
  ]
}
```

### XML Format
```xml
<?xml version="1.0" encoding="UTF-8"?>
<questions>
    <question>
        <category>Variables</category>
        <value>100</value>
        <questionText>What is an int?</questionText>
        <options>
            <A>A type</A>
            <B>A variable</B>
            <C>A function</C>
            <D>A class</D>
        </options>
        <correctAnswer>A</correctAnswer>
    </question>
</questions>
```

## Process Mining Log Format

The application generates a CSV log file with the following columns:
- **Case_ID**: Unique game session identifier
- **Player_ID**: Player identifier
- **Activity**: Action performed (e.g., "Start Game", "Select Category", "Answer Question")
- **Timestamp**: ISO timestamp of the event
- **Category**: Question category (if applicable)
- **Question_Value**: Point value of question (if applicable)
- **Answer_Given**: Player's answer (if applicable)
- **Result**: "Correct" or "Incorrect" (if applicable)
- **Score_After_Play**: Player's score after the action

## Generated Reports

### Summary Report Contents
1. **Final Scores**: Ranked list of players with scores
2. **Turn-by-Turn Breakdown**: For each player:
   - Turn number
   - Category selected
   - Question value
   - Question text
   - Given answer
   - Result (Correct/Incorrect)
   - Points earned
   - Running total

Reports are generated in the `src/main/resources/reports/` directory.

## Testing

### Run All Tests
```bash
mvn test
```

### Test Coverage
- Game logic testing
- File parsing (CSV, JSON, XML)
- Player scoring
- Report generation
- Process logging
- Strategy pattern implementation

## Contributing Team Members
- Group 12 Members (Add your names here)

## License
Academic Project - COMP3607

## Assignment Compliance

### Requirements Met
✅ Load data from CSV/JSON/XML  
✅ Support 1-4 players  
✅ Turn-based gameplay  
✅ Score tracking  
✅ Summary report generation (TXT/PDF/DOCX)  
✅ Process mining event log  
✅ 3+ design patterns (Factory, Strategy, Template Method, + Singleton)  
✅ All SOLID principles  
✅ JUnit tests  
✅ Maven build system  
✅ GitHub repository  
✅ Documentation  

## Additional Documentation
- [SOLID_PRINCIPLES.md](SOLID_PRINCIPLES.md) - Detailed SOLID principles implementation
- [DESIGN_PATTERNS.md](DESIGN_PATTERNS.md) - Design patterns documentation
A multi-player Jeopardy game built in Java, supporting 1-4 players on a local device. It loads questions from CSV, JSON, or XML, tracks scores, logs interactions for process mining, and generates detailed reports. The project uses design patterns, SOLID principles, and includes automated JUnit tests for functionality.

Features

Local multiplayer (1-4 players)

Data loading from CSV, JSON, or XML

Turn-based gameplay with categories and questions

Scoring system with dynamic updates

Generates game summary reports (PDF, TXT, DOCX)

Process mining log with detailed event tracking

Implementation of design patterns (Factory, Observer, Strategy)

Follows SOLID principles for clean, maintainable code

JUnit tests for core functionality

Technologies

Java: Core game logic and UI

JUnit: Automated testing

Maven: Build management

CSV/JSON/XML: Supported file formats for question data

Installation & Setup

Clone the repository:

git clone https://github.com/your-username/jeopardy-game.git


Navigate to the project directory:

cd jeopardy-game


Build the project using Maven:

mvn clean install


Run the game:

mvn exec:java

Usage

Follow the on-screen prompts to select the number of players, load the game data, and begin playing.

Players take turns selecting categories and answering questions.

At the end of the game, a summary report is generated, and a process mining log is saved.

Contributing

Feel free to fork this repository and submit pull requests. Please ensure any changes adhere to the project’s code structure and design principles.

License

This project is licensed under the MIT License – see the LICENSE
 file for details.
