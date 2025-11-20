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
- ✅ Implementation of 4 design patterns (Factory, Singleton, Strategy, Template Method)
- ✅ Full adherence to SOLID principles
- ✅ JUnit test coverage
- ✅ Maven build system

## 🚀 Quick Start Guide

### Prerequisites
Before running the game, ensure you have:
- **Java 11 or higher** installed ([Download Java](https://www.oracle.com/java/technologies/javase-downloads.html))
- **Maven 3.6 or higher** installed ([Download Maven](https://maven.apache.org/download.cgi))
- **Git** (to clone the repository)

### Verify Installation
```bash
# Check Java version
java -version
# Should show: java version "11.x.x" or higher

# Check Maven version
mvn -version
# Should show: Apache Maven 3.6.x or higher
```

## 📦 Setup Instructions

### Step 1: Clone the Repository
```bash
git clone https://github.com/KaveeshRamsarran/COMP3607-Group-12.git
cd COMP3607-Group-12\jeopardyproject\
```

### Step 2: Build the Project
```bash
# Clean any previous builds and compile
mvn clean compile

# Run all tests to verify setup
mvn test

# Package the application
mvn package
```

Expected output:
```
[INFO] BUILD SUCCESS
[INFO] Tests run: 22, Failures: 0, Errors: 0, Skipped: 0
```

### Step 3: Run the Application

**Option 1: Using Maven (Recommended)**
```bash
# Windows PowerShell
mvn exec:java '-Dexec.mainClass=com.comp3607.JeopardyApp'

# Linux/Mac/Git Bash
mvn exec:java -Dexec.mainClass="com.comp3607.JeopardyApp"
```

**Option 2: Using IDE**
- Open the project in IntelliJ IDEA, Eclipse, or VS Code
- Navigate to `src/main/java/com/comp3607/JeopardyApp.java`
- Right-click and select "Run JeopardyApp.main()"

## 🎮 Complete Game Walkthrough

### Starting the Game

When you run the application, you'll see:
```
=======================================
  WELCOME TO JEOPARDY GAME!
=======================================

Enter the path to the questions file:
```

### Step-by-Step Gameplay

#### 1️⃣ Load Game Data

**Sample Files Provided:**
- **CSV**: `src/main/resources/data/sample_game_CSV.csv`
- **JSON**: `src/main/resources/data/sample_game_JSON.json`
- **XML**: `src/main/resources/data/sample_game_XML.xml`

**Input:**
```
src/main/resources/data/sample_game_JSON.json
```

**Output:**
```
Questions loaded successfully!
Available categories: [Variables, Control Structures]
```

#### 2️⃣ Select Number of Players

**Prompt:**
```
Enter the number of players (1-4):
```

**Input:** Enter a number between 1 and 4
```
2
```

#### 3️⃣ Enter Player Names

**Prompt:**
```
Enter name for Player 1:
```

**Example:**
```
Enter name for Player 1: Alice
Player Alice added successfully!

Enter name for Player 2: Bob
Player Bob added successfully!
```

#### 4️⃣ Playing Turns

**Turn Display:**
```
========================================
Turn 1 - Player: Alice (Score: 0)
========================================

Available categories:
1. Variables
2. Control Structures

Enter category:
```

**Select Category:**
```
Variables
```

**Select Question Value:**
```
Available values for Variables: [100, 200, 300]
Enter question value:
200
```

**Question Display:**
```
╔════════════════════════════════════════════════════╗
║              QUESTION - 200 POINTS                 ║
╠════════════════════════════════════════════════════╣
║  Category: Variables                               ║
╠════════════════════════════════════════════════════╣
║                                                    ║
║  Which of the following best describes             ║
║  a variable in programming                         ║
║                                                    ║
║  [A] A type of loop                                ║
║  [B] A storage location with a name                ║
║  [C] A function                                    ║
║  [D] A class                                       ║
║                                                    ║
╚════════════════════════════════════════════════════╝

Enter your answer (A, B, C, or D):
```

**Answer:**
```
B
```

**Result (Correct Answer):**
```
╔════════════════════════════════════════════════════╗
║                    ✅ CORRECT!                     ║
╠════════════════════════════════════════════════════╣
║  Points Earned: +200                               ║
║  Current Score: 200                                ║
╚════════════════════════════════════════════════════╝
```

**Result (Incorrect Answer):**
```
╔════════════════════════════════════════════════════╗
║                   ❌ INCORRECT                     ║
╠════════════════════════════════════════════════════╣
║  Correct Answer: B                                 ║
║  Points Lost: -200                                 ║
║  Current Score: -200                               ║
╚════════════════════════════════════════════════════╝
```

#### 5️⃣ Continue Playing

The game continues with each player taking turns until all questions are answered or players choose to exit.

#### 6️⃣ Generate Reports

**After gameplay:**
```
Would you like to generate a summary report? (yes/no):
yes

Select report format (txt/pdf/docx):
pdf

Report generated: src/main/resources/reports/game_report.pdf
```

#### 7️⃣ Generate Process Mining Log

```
Would you like to generate the process mining log? (yes/no):
yes

Process mining log generated: src/main/resources/reports/game_event_log.csv
```

#### 8️⃣ Exit Game

```
Would you like to play another round? (yes/no):
no

Thank you for playing! Final Scores:
1. Alice - 600 points
2. Bob - 400 points
```

## 📊 Sample Gameplay Session

```
=======================================
  WELCOME TO JEOPARDY GAME!
=======================================

Enter the path to the questions file:
> src/main/resources/data/sample_game_JSON.json
✓ Questions loaded successfully!
✓ Available categories: [Variables, Control Structures]

Enter the number of players (1-4):
> 2

Enter name for Player 1: Alice
✓ Player Alice added successfully!

Enter name for Player 2: Bob
✓ Player Bob added successfully!

========================================
Turn 1 - Player: Alice (Score: 0)
========================================

Available categories:
1. Variables
2. Control Structures

Enter category: Variables

Available values for Variables: [100, 200, 300]
Enter question value: 200

╔════════════════════════════════════════════════════╗
║              QUESTION - 200 POINTS                 ║
╠════════════════════════════════════════════════════╣
║  Category: Variables                               ║
╠════════════════════════════════════════════════════╣
║                                                    ║
║  Which of the following best describes             ║
║  a variable in programming                         ║
║                                                    ║
║  [A] A type of loop                                ║
║  [B] A storage location with a name                ║
║  [C] A function                                    ║
║  [D] A class                                       ║
║                                                    ║
╚════════════════════════════════════════════════════╝

Enter your answer (A, B, C, or D): B

╔════════════════════════════════════════════════════╗
║                    ✅ CORRECT!                     ║
╠════════════════════════════════════════════════════╣
║  Points Earned: +200                               ║
║  Current Score: 200                                ║
╚════════════════════════════════════════════════════╝

========================================
Turn 2 - Player: Bob (Score: 0)
========================================
[Game continues...]
```

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

## 🎯 Understanding the Output

### Generated Files

After playing a game, you'll find these files:

**1. Summary Report** (`src/main/resources/reports/`)
- `game_report.txt` - Plain text format
- `game_report.pdf` - PDF format
- `game_report.docx` - Microsoft Word format

**Report Contents:**
```
=== JEOPARDY GAME SUMMARY REPORT ===
Generated: 2025-11-20 15:30:45

FINAL SCORES (Ranked):
1. Alice - 600 points
2. Bob - 400 points

PLAYER DETAILS:

Player: Alice (Final Score: 600)
--------------------------------------------------
Turn 1: Variables - 200 points
  Q: What is a variable in programming?
  A: B (Correct)
  Points: +200 | Total: 200

Turn 2: Control Structures - 300 points
  Q: What is a for loop?
  A: C (Correct)
  Points: +300 | Total: 500
[... more turns ...]
```

**2. Process Mining Event Log** (`src/main/resources/reports/game_event_log.csv`)

CSV format with columns:
```csv
Case_ID,Player_ID,Activity,Timestamp,Category,Question_Value,Answer_Given,Result,Score_After_Play
GAME_001,PLAYER_001,Start Game,2025-11-20T15:30:00,,,,,0
GAME_001,PLAYER_001,Load File,2025-11-20T15:30:05,,,,,0
GAME_001,PLAYER_001,Select Player Count,2025-11-20T15:30:10,,,,,0
GAME_001,PLAYER_001,Enter Player Name,2025-11-20T15:30:15,,,,,0
GAME_001,PLAYER_001,Select Category,2025-11-20T15:30:20,Variables,,,0
GAME_001,PLAYER_001,Select Question,2025-11-20T15:30:25,Variables,200,,0
GAME_001,PLAYER_001,Answer Question,2025-11-20T15:30:30,Variables,200,B,Correct,200
```

## 🔧 Troubleshooting

### Common Issues

**Issue 1: "java: error: release version 11 not supported"**
```bash
# Solution: Install Java 11 or higher
# Check your Java version
java -version
```

**Issue 2: "mvn: command not found"**
```bash
# Solution: Install Maven or add to PATH
# Windows: Add Maven bin folder to PATH environment variable
# Linux/Mac: export PATH=$PATH:/path/to/maven/bin
```

**Issue 3: "BUILD FAILURE" during mvn test**
```bash
# Solution: Clean and rebuild
mvn clean install -U
```

**Issue 4: File not found when loading questions**
```bash
# Solution: Use absolute or relative path
# Absolute: C:/Users/YourName/COMP3607-Group-12/jeopardyproject/src/main/resources/data/sample_game_JSON.json
# Relative: src/main/resources/data/sample_game_JSON.json
```

**Issue 5: "NoClassDefFoundError" when running**
```bash
# Solution: Use Maven to run (includes all dependencies)
mvn exec:java '-Dexec.mainClass=com.comp3607.JeopardyApp'
```

## 🧪 Testing

### Run All Tests
```bash
mvn test
```

### Test Coverage Includes:
- ✅ Game logic (starting, loading, playing)
- ✅ File parsing (CSV, JSON, XML formats)
- ✅ Player management (adding, scoring)
- ✅ Question management (selection, validation)
- ✅ Report generation (TXT, PDF, DOCX)
- ✅ Process logging (event tracking)
- ✅ Strategy pattern (category-specific validation)

### Sample Test Output
```
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.comp3607.GameTest
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
[INFO] Running com.comp3607.CategoryStrategyTest
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
[INFO] Running com.comp3607.ProcessLogTest
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
[INFO] Running com.comp3607.QuestionTest
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
[INFO] Running com.comp3607.ReportGeneratorTest
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] Results:
[INFO] Tests run: 22, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

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

## 📝 Creating Custom Question Files

### CSV Format Example
Create a file `my_questions.csv`:
```csv
Category,Value,Question,OptionA,OptionB,OptionC,OptionD,CorrectAnswer
Variables,100,What is an int?,A type,A variable,A function,A class,A
Variables,200,What is a String?,Text data,Number data,Boolean data,Array data,A
Control Structures,100,What is a loop?,Iteration,Variable,Function,Class,A
Control Structures,200,What is if-else?,Conditional,Loop,Function,Variable,A
```

### JSON Format Example
Create a file `my_questions.json`:
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
    },
    {
      "category": "Control Structures",
      "value": 200,
      "question": "What is a loop?",
      "options": {
        "A": "Iteration",
        "B": "Variable",
        "C": "Function",
        "D": "Class"
      },
      "correctAnswer": "A"
    }
  ]
}
```

### XML Format Example
Create a file `my_questions.xml`:
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
    <question>
        <category>Control Structures</category>
        <value>200</value>
        <questionText>What is a loop?</questionText>
        <options>
            <A>Iteration</A>
            <B>Variable</B>
            <C>Function</C>
            <D>Class</D>
        </options>
        <correctAnswer>A</correctAnswer>
    </question>
</questions>
```

**Then load it in the game:**
```
Enter the path to the questions file:
src/main/resources/data/my_questions.json
```

## 🏗️ Architecture Overview

### Design Patterns Used

**1. Factory Pattern** (`FileParserFactory`)
- Creates appropriate parser based on file type
- Encapsulates object creation logic

**2. Singleton Pattern** (`ProcessLog`)
- Ensures single instance for event logging
- Provides global access point

**3. Strategy Pattern** (`CategoryStrategy`)
- Different validation/scoring algorithms
- Selected at runtime based on category

**4. Template Method Pattern** (`ReportGenerator`)
- Common report structure
- Format-specific implementations (TXT, PDF, DOCX)

### SOLID Principles Applied

**S** - Single Responsibility: Each class has one job  
**O** - Open/Closed: Extensible without modification  
**L** - Liskov Substitution: Implementations are interchangeable  
**I** - Interface Segregation: Minimal, focused interfaces  
**D** - Dependency Inversion: Depend on abstractions  

## 📚 Additional Documentation
- **[DESIGN_PATTERNS.md](DESIGN_PATTERNS.md)** - Comprehensive design patterns documentation (80+ pages)
- **[SOLID_PRINCIPLES.md](SOLID_PRINCIPLES.md)** - Detailed SOLID principles implementation (60+ pages)

## 🎓 Assignment Compliance

### All Requirements Met ✅

| Requirement | Status | Implementation |
|-------------|--------|----------------|
| Load data from CSV/JSON/XML | ✅ | FileParserFactory with 3 parsers |
| Support 1-4 players | ✅ | JeopardyApp player setup |
| Turn-based gameplay | ✅ | Game.playTurn() method |
| Score tracking | ✅ | Player.answerQuestion() |
| Summary reports (TXT/PDF/DOCX) | ✅ | ReportGenerator with 3 formats |
| Process mining event log | ✅ | ProcessLog with CSV output |
| ≥3 design patterns | ✅ | 4 patterns implemented |
| All SOLID principles | ✅ | All 5 principles applied |
| JUnit tests | ✅ | 22 tests, 100% pass rate |
| Maven build system | ✅ | pom.xml configured |
| GitHub repository | ✅ | Version controlled |
| Documentation | ✅ | README + 2 detailed docs |

## 👥 Contributing Team Members
- Group 12 Members

## 📄 License
Academic Project - COMP3607

---

## 🚀 Quick Command Reference

```bash
# Build project
mvn clean package

# Run tests
mvn test

# Run application (Windows PowerShell)
mvn exec:java '-Dexec.mainClass=com.comp3607.JeopardyApp'

# Run application (Linux/Mac/Git Bash)
mvn exec:java -Dexec.mainClass="com.comp3607.JeopardyApp"

# Clean build artifacts
mvn clean
```

**Happy Gaming! 🎮**


