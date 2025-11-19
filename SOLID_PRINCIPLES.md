# SOLID Principles Implementation

## Overview
This document demonstrates how each of the five SOLID principles is implemented in the Jeopardy Game application.

---

## 1. Single Responsibility Principle (SRP)
**Definition**: A class should have only one reason to change.

### Implementation:
- **`Player` class**: Responsible only for managing player data (name, score, turn history)
- **`Question` class**: Responsible only for question data and validation
- **`ProcessLog` class**: Responsible only for logging game events
- **`ReportGenerator` class**: Responsible only for generating reports
- **`Game` class**: Responsible only for game flow management
- **File parsers** (`CSVParser`, `JSONParser`, `XMLParser`): Each responsible for parsing one file type

**Example**:
```java
public class Player {
    private final String name;
    private int score;
    // Only manages player-related data and behavior
}
```

---

## 2. Open/Closed Principle (OCP)
**Definition**: Software entities should be open for extension but closed for modification.

### Implementation:
- **`FileParser` interface**: New file formats can be added by creating new implementations without modifying existing code
- **`CategoryStrategy` interface**: New scoring or validation strategies can be added without changing existing strategies
- **Factory Pattern**: `FileParserFactory` allows adding new parsers without modifying the factory logic

**Example**:
```java
public interface FileParser {
    List<Question> parseFile(String filePath) throws IOException;
}

// Easy to extend with new formats
public class CSVParser implements FileParser { ... }
public class JSONParser implements FileParser { ... }
public class XMLParser implements FileParser { ... }
```

---

## 3. Liskov Substitution Principle (LSP)
**Definition**: Objects of a superclass should be replaceable with objects of a subclass without breaking the application.

### Implementation:
- All `FileParser` implementations can be used interchangeably
- All `CategoryStrategy` implementations can be substituted without affecting game logic
- The `Game` class uses these interfaces without knowing the concrete implementation

**Example**:
```java
// Can substitute any FileParser implementation
FileParser parser = FileParserFactory.createParser(fileType);
List<Question> questions = parser.parseFile(filePath);

// Can substitute any CategoryStrategy implementation
CategoryStrategy strategy = new VariableStrategy();
boolean isCorrect = strategy.validateAnswer(answer, correctAnswer);
```

---

## 4. Interface Segregation Principle (ISP)
**Definition**: Clients should not be forced to depend on interfaces they don't use.

### Implementation:
- **`FileParser` interface**: Contains only the essential `parseFile()` method
- **`CategoryStrategy` interface**: Contains only necessary methods (`validateAnswer()`, `calculatePoints()`)
- No fat interfaces - each interface is focused and minimal

**Example**:
```java
public interface CategoryStrategy {
    boolean validateAnswer(String answer, String correctAnswer);
    int calculatePoints(int value);
    // No unnecessary methods
}
```

---

## 5. Dependency Inversion Principle (DIP)
**Definition**: High-level modules should not depend on low-level modules. Both should depend on abstractions.

### Implementation:
- **`Game` class** depends on `FileParser` interface, not concrete parsers
- **Game logic** depends on `CategoryStrategy` interface, not concrete strategies
- **Factory Pattern** ensures the game doesn't know about concrete parser implementations

**Example**:
```java
public class Game {
    // Depends on abstraction (FileParser), not concrete implementations
    public void loadQuestions(String filePath, String fileType) throws IOException {
        FileParser parser = FileParserFactory.createParser(fileType);
        this.questions.addAll(parser.parseFile(filePath));
    }
    
    // Depends on abstraction (CategoryStrategy), not concrete strategies
    public TurnResult playTurn(String category, int value, String answer, 
                              CategoryStrategy strategy) {
        boolean isCorrect = strategy.validateAnswer(answer, question.getCorrectAnswer());
        // ...
    }
}
```

---

## Design Patterns Used

### 1. **Factory Pattern**
- **Location**: `FileParserFactory`
- **Purpose**: Creates appropriate file parser based on file type
- **Benefit**: Encapsulates object creation logic

### 2. **Singleton Pattern**
- **Location**: `ProcessLog`
- **Purpose**: Ensures single instance for centralized logging
- **Benefit**: Consistent logging across the application

### 3. **Strategy Pattern**
- **Location**: `CategoryStrategy` and implementations (`VariableStrategy`, `ControlStructureStrategy`)
- **Purpose**: Allows different validation/scoring algorithms
- **Benefit**: Flexible behavior selection at runtime

### 4. **Template Method Pattern** (implicit)
- **Location**: Report generation methods
- **Purpose**: Common report structure with format-specific implementations
- **Benefit**: Code reuse and consistent report structure

---

## Summary
The Jeopardy Game application demonstrates all five SOLID principles through:
- Clear separation of responsibilities
- Extension points through interfaces
- Substitutable implementations
- Focused interfaces
- Dependency on abstractions

These principles, combined with design patterns, create a maintainable, extensible, and testable codebase.
