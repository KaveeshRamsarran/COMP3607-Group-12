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

# Run application (Java)
mvn javafx:run 

# Run application (Linux/Mac/Git Bash)
mvn exec:java -Dexec.mainClass="com.comp3607.JeopardyApp"

# Clean build artifacts
mvn clean

# Generate JavaDoc API documentation
mvn javadoc:javadoc
# Or use: .\generate-javadoc.ps1
```

## 📚 API Documentation

JavaDoc API documentation is available and can be generated locally or published to GitHub Pages.

### Generate Documentation
```powershell
# Using PowerShell script (recommended)
.\generate-javadoc.ps1

# Or using Maven
mvn javadoc:javadoc

# To view JavaDoc API documentation
start docs\index.html
```

### Live API Documentation
- **[Quick Start Guide](https://kaveeshramsarran.github.io/COMP3607-Group-12/)** - Get started with the API
- **[Design Patterns](https://kaveeshramsarran.github.io/COMP3607-Group-12/)** - Factory, Singleton, Strategy implementations
- **[SOLID Principles](https://kaveeshramsarran.github.io/COMP3607-Group-12/)** - Architecture and design
- **[Testing Guide](https://kaveeshramsarran.github.io/COMP3607-Group-12/)** - Unit tests and coverage

### Documentation Includes
- **Packages:** Core game logic, parsers (CSV/JSON/XML), strategies, patterns
- **Classes:** Game, Player, Question, ProcessLog, ReportGenerator, JeopardyApp, JeopardyAppGUI
- **All Javadoc:** Complete API reference with detailed method documentation



