# Testing Guide

## Running Tests

### Run All Tests
```bash
mvn test
```

### Run Specific Test Class
```bash
mvn test -Dtest=GameTest
mvn test -Dtest=QuestionTest
mvn test -Dtest=ProcessLogTest
```

### Run with Coverage
```bash
mvn test jacoco:report
```

## Manual Testing

### Test GUI Application
```bash
# Launch GUI for manual testing
mvn javafx:run
```

**Test Checklist:**
- ✅ Welcome screen displays correctly
- ✅ File browser opens and selects files
- ✅ File format auto-detection works
- ✅ Player setup buttons respond to clicks
- ✅ Player names can be entered
- ✅ Game board displays with all players
- ✅ Category buttons are clickable
- ✅ Value buttons show correct amounts
- ✅ Questions display with radio buttons
- ✅ Answer submission works correctly
- ✅ Correct/incorrect feedback appears
- ✅ Scores update in real-time
- ✅ "Quit Game" shows end screen
- ✅ Final rankings display with medals
- ✅ Report buttons generate files
- ✅ File timestamps are current
- ✅ Event counts match gameplay
- ✅ Exit button closes application

### Test CLI Application
```bash
# Windows
mvn exec:java '-Dexec.mainClass=com.comp3607.JeopardyApp'

# Linux/Mac
mvn exec:java -Dexec.mainClass="com.comp3607.JeopardyApp"
```

## Test Coverage (22 tests, 100% pass rate)

**GameTest** (6 tests) - Initialization, loading, gameplay  
**QuestionTest** (5 tests) - Creation, validation, answered state  
**ProcessLogTest** (4 tests) - Singleton, logging, CSV generation  
**CategoryStrategyTest** (4 tests) - Strategy validation and scoring  
**ReportGeneratorTest** (3 tests) - TXT, PDF, DOCX generation

### Integration Tests

The application includes integration-style tests that verify:
- End-to-end game flow
- File parsing → Game logic → Report generation
- Process logging throughout gameplay

## Manual Testing Checklist

### Game Flow
- [ ] Start application
- [ ] Load questions from CSV file
- [ ] Load questions from JSON file
- [ ] Load questions from XML file
- [ ] Add 1 player
- [ ] Add 2 players
- [ ] Add 4 players
- [ ] Select category
- [ ] Select question value
- [ ] Answer correctly
- [ ] Answer incorrectly
- [ ] Complete full game
- [ ] Quit game early

### Report Generation
- [ ] Generate TXT report
- [ ] Generate PDF report
- [ ] Generate DOCX report
- [ ] Verify final scores
- [ ] Verify turn-by-turn details
- [ ] Verify report format

### Process Mining Log
- [ ] Verify CSV format
- [ ] Verify all columns present
- [ ] Verify timestamps are ISO format
- [ ] Verify all events logged
- [ ] Verify chronological order

## Test Data

### Sample Files Location
- `src/main/resources/data/sample_game_CSV.csv`
- `src/main/resources/data/sample_game_JSON.json`
- `src/main/resources/data/sample_game_XML.xml`

### Test Scenarios

#### Scenario 1: Single Player Complete Game
1. Start game
2. Load CSV file
3. Add player "Alice"
4. Play all questions
5. Generate reports

#### Scenario 2: Multi-Player Partial Game
1. Start game
2. Load JSON file
3. Add players "Alice", "Bob", "Charlie"
4. Play 5 questions
5. Quit early
6. Generate reports

#### Scenario 3: Strategy Pattern Testing
1. Create questions from different categories
2. Use VariableStrategy for some
3. Use ControlStructureStrategy for others
4. Verify correct validation behavior

## Expected Results

### Process Mining Log Format
```csv
Case_ID,Player_ID,Activity,Timestamp,Category,Question_Value,Answer_Given,Result,Score_After_Play
GAME_1234567890,SYSTEM,Start Game,2024-01-01T10:00:00,Game Started,,,,
GAME_1234567890,ALICE,Enter Player Name,2024-01-01T10:00:05,Alice,,,,0
GAME_1234567890,ALICE,Select Category,2024-01-01T10:00:10,Variables,,,,0
GAME_1234567890,ALICE,Answer Question,2024-01-01T10:00:15,Variables,100,A,Correct,100
```

### Report Format (TXT)
```
=====================================
    JEOPARDY GAME SUMMARY REPORT     
=====================================
Generated: 2024-01-01 10:05:00

FINAL SCORES:
-------------------------------------
1. Alice: 300 points
2. Bob: 150 points

DETAILED TURN-BY-TURN BREAKDOWN:
=====================================

Player: Alice
-------------------------------------
Turn 1:
  Category: Variables
  Question Value: 100 points
  Question: What is an int?
  Given Answer: A
  Result: CORRECT
  Points Earned: +100
  Running Total: 100
```

## Troubleshooting

### Test Failures

#### "File not found" errors
- Ensure sample data files exist in `src/main/resources/data/`
- Check file paths are correct

#### "Cannot create directory" errors
- Ensure `src/main/resources/reports/` directory exists
- Check write permissions

#### Strategy validation failures
- Verify strategy implementations match interface
- Check answer comparison logic

### Build Failures

#### Missing dependencies
```bash
mvn clean install
```

#### Compilation errors
```bash
mvn clean compile
```

## Continuous Integration

### GitHub Actions (Optional)
Create `.github/workflows/maven.yml`:
```yaml
name: Java CI with Maven

on: [push, pull_request]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v2
    - name: Set up JDK 11
      uses: actions/setup-java@v2
      with:
        java-version: '11'
    - name: Build with Maven
      run: mvn clean verify
```

## Test Metrics

Expected test coverage:
- Line Coverage: >80%
- Branch Coverage: >70%
- Method Coverage: >85%

## Best Practices

1. **Run tests before committing**
   ```bash
   mvn clean test
   ```

2. **Write tests for new features**
   - Add test methods for new functionality
   - Follow naming convention: `test[MethodName][Scenario]`

3. **Keep tests independent**
   - Each test should be self-contained
   - Use @BeforeEach for setup
   - Don't rely on test execution order

4. **Use meaningful assertions**
   - Use specific assertion methods
   - Include failure messages
   - Test both positive and negative cases

## Additional Resources

- JUnit 5 Documentation: https://junit.org/junit5/docs/current/user-guide/
- Maven Surefire Plugin: https://maven.apache.org/surefire/maven-surefire-plugin/
- AssertJ (optional): https://assertj.github.io/doc/
