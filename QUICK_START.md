# Quick Start Guide

## Prerequisites
- Java 11 or higher installed
- Maven 3.6 or higher installed
- Git (for cloning repository)

## Installation

### 1. Clone Repository
```bash
git clone https://github.com/KaveeshRamsarran/COMP3607-Group-12.git
cd COMP3607-Group-12/jeopardyproject
```

### 2. Build Project
```bash
mvn clean install
```

## Running the Application

### Quick Run
```bash
mvn exec:java -Dexec.mainClass="com.comp3607.JeopardyApp"
```

### Alternative: Run from JAR
```bash
mvn package
java -cp target/jeopardy-game-1.0-SNAPSHOT.jar com.comp3607.JeopardyApp
```

## Playing the Game

### Step 1: Load Questions
When prompted, enter the file path to your questions file:
```
src/main/resources/data/sample_game_CSV.csv
```
Or use JSON or XML:
```
src/main/resources/data/sample_game_JSON.json
src/main/resources/data/sample_game_XML.xml
```

Then enter the file type:
```
csv
```
(or `json` or `xml`)

### Step 2: Setup Players
Enter number of players (1-4):
```
2
```

Enter player names when prompted:
```
Enter name for Player 1: Alice
Enter name for Player 2: Bob
```

### Step 3: Play Game
For each turn:
1. Select category (enter number)
2. Select question value (enter number)
3. Read the question
4. Answer (enter A, B, C, or D)
5. See if you're correct and your new score

To quit early, type `Q` when asked to play or quit.

### Step 4: Generate Reports
After the game ends, choose report format:
```
Choose report format (txt/pdf/docx): txt
```

Reports will be generated in:
- `src/main/resources/reports/game_report.[format]`
- `src/main/resources/reports/game_event_log.csv`

## Running Tests

### Run All Tests
```bash
mvn test
```

### Run Specific Test
```bash
mvn test -Dtest=GameTest
```

### View Test Results
Test reports are generated in:
```
target/surefire-reports/
```

## Sample Gameplay

```
=======================================
  WELCOME TO JEOPARDY GAME!
=======================================

Enter the path to the questions file:
src/main/resources/data/sample_game_CSV.csv

Enter file type (csv/json/xml):
csv

Questions loaded successfully!

How many players? (1-4):
2

Enter name for Player 1: Alice
Enter name for Player 2: Bob

Players registered:
  - Alice
  - Bob

Game started! Let's play!

---------------------------------------
Current Player: Alice
Current Score: 0
---------------------------------------

Do you want to (P)lay or (Q)uit? P

Available Categories:
1. Variables & Data Types
2. Control Structures
3. Functions & Methods

Select category (1-3): 1

Available Values:
1. 100 points
2. 200 points

Select value (1-2): 1

Category: Variables & Data Types | Value: 100 points
Question: Which of the following declares an integer variable in C++?
Options:
  A. int num;
  B. float num;
  C. num int;
  D. integer num;

Your answer (A/B/C/D): A

✓ CORRECT! +100 points
New score: 100
```

## Troubleshooting

### Issue: "Cannot find Maven"
**Solution**: Ensure Maven is installed and in your PATH
```bash
mvn --version
```

### Issue: "Java version error"
**Solution**: Verify Java 11+ is installed
```bash
java -version
```

### Issue: "File not found"
**Solution**: Use absolute path or ensure you're in the correct directory
```bash
# Use full path
C:\Users\YourName\COMP3607-Group-12\jeopardyproject\src\main\resources\data\sample_game_CSV.csv
```

### Issue: "Tests failing"
**Solution**: Ensure reports directory exists
```bash
mkdir -p src/main/resources/reports
```

## Project Structure Quick Reference

```
jeopardyproject/
├── src/main/
│   ├── java/com/comp3607/        # Source code
│   └── resources/
│       ├── data/                 # Question files
│       └── reports/              # Generated reports
├── src/test/                     # Test files
├── target/                       # Build output
└── pom.xml                       # Maven config
```

## Common Commands

```bash
# Clean build
mvn clean

# Compile
mvn compile

# Run tests
mvn test

# Package JAR
mvn package

# Run application
mvn exec:java -Dexec.mainClass="com.comp3607.JeopardyApp"

# Clean and rebuild
mvn clean install

# Skip tests (not recommended)
mvn install -DskipTests
```

## Documentation Links

- [README.md](README.md) - Full project documentation
- [SOLID_PRINCIPLES.md](SOLID_PRINCIPLES.md) - SOLID principles explanation
- [DESIGN_PATTERNS.md](DESIGN_PATTERNS.md) - Design patterns documentation
- [TESTING_GUIDE.md](TESTING_GUIDE.md) - Testing instructions
- [PROJECT_SUMMARY.md](PROJECT_SUMMARY.md) - Implementation summary

## Support

For issues or questions:
1. Check the documentation files
2. Review error messages carefully
3. Ensure all prerequisites are met
4. Verify file paths are correct

## Next Steps

1. ✅ Build the project: `mvn clean install`
2. ✅ Run tests: `mvn test`
3. ✅ Run the application: `mvn exec:java -Dexec.mainClass="com.comp3607.JeopardyApp"`
4. ✅ Play a sample game
5. ✅ Review generated reports
6. ✅ Check process mining log

Enjoy playing Jeopardy!
