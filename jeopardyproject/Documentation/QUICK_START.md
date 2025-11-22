# Quick Start Guide

## Prerequisites
- **Java 11+** ([Download](https://www.oracle.com/java/technologies/javase-downloads.html))
- **Maven 3.6+** ([Download](https://maven.apache.org/download.cgi))
- **Git**

Verify: `java -version` and `mvn -version`

## Installation

### 1. Clone Repository
```bash
git clone https://github.com/KaveeshRamsarran/COMP3607-Group-12.git
cd COMP3607-Group-12/jeopardyproject
```

### 2. Build Project
```bash
# Clean, compile, and run tests
mvn clean compile
mvn test

# Package the application
mvn package
```

Expected output:
```
[INFO] BUILD SUCCESS
[INFO] Tests run: 22, Failures: 0, Errors: 0, Skipped: 0
```

## Running the Application

### Option 1: JavaFX GUI (Recommended)
```bash
# Run the interactive GUI application
mvn javafx:run
```

**GUI Features:**
- 🎮 Interactive Jeopardy-style game board
- 📁 Built-in file browser
- 🎯 Visual category and dollar amount selection
- ✅ Real-time score tracking
- 🏆 End game screen with rankings and medals
- 📄 One-click report generation with timestamps

### Option 2: Command Line Interface
```bash
# Windows PowerShell
mvn exec:java '-Dexec.mainClass=com.comp3607.JeopardyApp'

# Linux/Mac/Git Bash
mvn exec:java -Dexec.mainClass="com.comp3607.JeopardyApp"
```

### Option 3: Using IDE
- Open project in IntelliJ IDEA, Eclipse, or VS Code
- For GUI: Navigate to `src/main/java/com/comp3607/JeopardyAppGUI.java` and run
- For CLI: Navigate to `src/main/java/com/comp3607/JeopardyApp.java` and run

## Playing the Game

### GUI Version (Recommended)

#### Step 1: Welcome Screen
- Launch the application with `mvn javafx:run`
- Click **"LET'S PLAY!"** on the welcome screen

#### Step 2: Select Questions File
- Click **"BROWSE FILES"** button
- Navigate to `src/main/resources/data/`
- Select a file (CSV, JSON, or XML)
- File type is auto-detected
- Click **"LOAD QUESTIONS"**

#### Step 3: Choose Number of Players
- Click a number button (1-4)
- Click **"CONTINUE"**

#### Step 4: Enter Player Names
- Type name for each contestant
- Click **"START GAME"**

#### Step 5: Play the Game
- View current player and scores at top
- Click a **CATEGORY** button
- Click a **DOLLAR AMOUNT** button ($100-$500)
- Read the question
- Select your answer (A, B, C, or D) using radio buttons
- Click **"SUBMIT ANSWER"**
- View result (correct/incorrect) with score update
- Next player's turn begins automatically

#### Step 6: End Game and Generate Reports
- Click **"QUIT GAME"** when ready to end
- Confirm to view final results
- See rankings with medals (🥇 🥈 🥉)
- Click report buttons:
  - **TXT Report** - Plain text summary
  - **PDF Report** - PDF document
  - **DOCX Report** - Word document
  - **Process Log** - CSV event log
- Each button shows file location and timestamp
- Click **"EXIT GAME"** when finished

---

### CLI Version

#### Step 1: Load Questions
When prompted, enter the file path:
```
src/main/resources/data/sample_game_JSON.json
```

**Available sample files:**
- `sample_game_CSV.csv`
- `sample_game_JSON.json`
- `sample_game_XML.xml`

Output:
```
Questions loaded successfully!
Available categories: [Variables, Control Structures]
```

### Step 2: Setup Players
Enter number of players (1-4):
```
2
```

Enter player names:
```
Enter name for Player 1: Alice
Player Alice added successfully!

Enter name for Player 2: Bob
Player Bob added successfully!
```

### Step 3: Play Turns (CLI)
For each turn:
1. **Select category**: Type the category name (e.g., `Variables`)
2. **Select value**: Choose point value (e.g., `200`)
3. **Read question**: View question with 4 options (A, B, C, D)
4. **Answer**: Enter your choice (A, B, C, or D)
5. **View result**: See if correct and your new score

---

## Troubleshooting

### GUI Won't Start
```bash
# Ensure JavaFX dependencies are installed
mvn clean install
mvn javafx:run
```

### Reports Not Updating
- Check the timestamp shown in the success dialog
- Ensure you're looking at the correct file path (shown in dialog)
- Reports directory: `src/main/resources/reports/`

### File Browser Not Working
- Use absolute path or navigate from project root
- Default location: `src/main/resources/data/`

Example turn:
```
Available categories:
1. Variables
2. Control Structures

Enter category: Variables

Available values for Variables: [100, 200, 300]
Enter question value: 200

Question (200 points):
What is a variable in programming?

A. A type of loop
B. A storage location with a name
C. A function
D. A class

Enter your answer (A, B, C, or D): B
✅ Correct! You earned 200 points.
Current score: 200
```

### Step 4: Generate Reports
After gameplay:
```
Would you like to generate a summary report? (yes/no): yes

Select report format (txt/pdf/docx): pdf

Report generated: src/main/resources/reports/game_report.pdf
```

### Step 5: Generate Process Mining Log
```
Would you like to generate the process mining log? (yes/no): yes

Process mining log generated: src/main/resources/reports/game_event_log.csv
```

## Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=GameTest

# View test results
# Reports are in: target/surefire-reports/
```

Expected output:
```
[INFO] Tests run: 22, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

## Troubleshooting

### Common Issues & Solutions

**"Cannot find Maven"**
```bash
mvn --version  # Check if Maven is installed and in PATH
```

**"Java version error"**
```bash
java -version  # Verify Java 11+ is installed
```

**"File not found" when loading questions**
- Use relative path: `src/main/resources/data/sample_game_JSON.json`
- Or absolute path: `C:\Users\YourName\COMP3607-Group-12\jeopardyproject\src\main\resources\data\sample_game_JSON.json`

**"BUILD FAILURE" during tests**
```bash
mvn clean install -U  # Clean and rebuild with updated dependencies
```

**"NoClassDefFoundError"**
```bash
# Use Maven to run (includes all dependencies)
mvn exec:java '-Dexec.mainClass=com.comp3607.JeopardyApp'
```

## Common Commands

```bash
# Build and compile
mvn clean compile

# Run tests
mvn test

# Package application
mvn package

# Run application (Windows PowerShell)
mvn exec:java '-Dexec.mainClass=com.comp3607.JeopardyApp'

# Run application (Linux/Mac/Git Bash)
mvn exec:java -Dexec.mainClass="com.comp3607.JeopardyApp"

# Clean build artifacts
mvn clean
```

## Project Structure

```
jeopardyproject/
├── src/main/
│   ├── java/com/comp3607/        # Source code
│   └── resources/
│       ├── data/                 # Sample question files
│       └── reports/              # Generated reports
├── src/test/                     # JUnit tests
├── target/                       # Build output
└── pom.xml                       # Maven configuration
```

## Additional Documentation

- **[README.md](README.md)** - Complete project documentation
- **[DESIGN_PATTERNS.md](DESIGN_PATTERNS.md)** - Design patterns (80+ pages)
- **[SOLID_PRINCIPLES.md](SOLID_PRINCIPLES.md)** - SOLID principles (60+ pages)
- **[TESTING_GUIDE.md](TESTING_GUIDE.md)** - Testing instructions
- **[PROJECT_SUMMARY.md](PROJECT_SUMMARY.md)** - Implementation summary

## Quick Start Checklist

- [ ] Install Java 11+ and Maven 3.6+
- [ ] Clone repository
- [ ] Build project: `mvn clean compile`
- [ ] Run tests: `mvn test`
- [ ] Run application: `mvn exec:java '-Dexec.mainClass=com.comp3607.JeopardyApp'`
- [ ] Load sample file: `src/main/resources/data/sample_game_JSON.json`
- [ ] Play a game
- [ ] Generate reports
- [ ] Check event log

**Need help?** See the full [README.md](README.md) for detailed instructions.

---

**Happy Gaming! 🎮**
