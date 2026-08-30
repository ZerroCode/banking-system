# Banking System

A comprehensive Java banking system application with support for multiple account types, transaction processing, and customer management.

## Project Overview

The Banking System is a full-featured, object-oriented banking application built in Java that demonstrates professional software engineering practices. It provides a complete banking solution with support for savings accounts, checking accounts, and certificates of deposit (CDs).

### Key Features

- **Multiple Account Types**
  - Savings Accounts: Standard savings with account management
  - Checking Accounts: Checking with check clearing support
  - CD Accounts: Certificates of Deposit with maturity dates

- **Transaction Management**
  - Deposits and withdrawals
  - Balance inquiries
  - Check processing
  - Account history tracking

- **Account Operations**
  - Create new accounts
  - Close and reopen accounts
  - View account information and history
  - Real-time balance tracking

## Project Structure

```
banking-system/
├── pom.xml                              # Maven build configuration
├── README.md                            # This file
├── .gitignore                           # Git ignore rules
└── src/
    ├── main/
    │   └── java/
    │       ├── ui/                      # User interface / Main entry point
    │       │   ├── pgm.java             # Main application class
    │       │   └── package-info.java    # Package documentation
    │       ├── models/                  # Core domain model classes
    │       │   ├── Bank.java            # Banking institution
    │       │   ├── Account.java         # Base account class
    │       │   ├── Depositor.java       # Customer information
    │       │   ├── Name.java            # Name representation
    │       │   ├── Check.java           # Check handling
    │       │   └── package-info.java    # Package documentation
    │       ├── accounts/                # Account type implementations
    │       │   ├── SavingsAccount.java  # Savings account
    │       │   ├── CheckingAccount.java # Checking account
    │       │   ├── CDAccount.java       # CD account
    │       │   └── package-info.java    # Package documentation
    │       ├── transactions/            # Transaction handling
    │       │   ├── TransactionTicket.java
    │       │   ├── TransactionReceipt.java
    │       │   └── package-info.java    # Package documentation
    │       ├── generators/              # Abstract base classes
    │       │   ├── genAccount.java
    │       │   ├── genDepositor.java
    │       │   ├── genName.java
    │       │   ├── genTransactionTicket.java
    │       │   ├── genTransactionReceipt.java
    │       │   └── package-info.java    # Package documentation
    │       └── resources/               # Data files
    │           ├── initAccounts.txt     # Initial account database
    │           └── myTestCases.txt      # Test transaction cases
    └── test/
        └── java/                        # Unit tests (for future expansion)
```

## Technology Stack

- **Language**: Java 11+
- **Build Tool**: Maven 3.6+
- **Testing**: JUnit 4.13.2

## Installation & Setup

### Prerequisites

- Java Development Kit (JDK) 11 or higher
- Maven 3.6 or higher

### Building the Project

```bash
# Clone or download the repository
cd banking-system

# Clean and build the project
mvn clean package

# Build with tests
mvn clean package -DskipTests=false
```

### Running the Application

#### Option 1: Direct Execution
```bash
mvn exec:java -Dexec.mainClass="ui.pgm"
```

#### Option 2: Using JAR
```bash
# Build the executable JAR
mvn clean package

# Run the application
java -jar target/banking-system-1.0.0-jar-with-dependencies.jar
```

#### Option 3: IDE Execution
1. Open the project in your IDE (IntelliJ IDEA, Eclipse, VS Code)
2. Locate `src/main/java/ui/pgm.java`
3. Run the `main` method

## Usage

Once the application starts, you'll be presented with an interactive menu:

```
Select one of the following transactions:
    ****************************
    List of Choices
    ****************************
    W -- Withdrawal
    D -- Deposit
    C -- Clear Check
    N -- New Account
    B -- Balance Inquiry
    I -- Account Info
    H -- Account Info and Transaction History
    S -- Close Account
    R -- Reopen Closed Account
    X -- Delete Account
    Q -- Quit
```

### Available Operations

- **W (Withdrawal)**: Withdraw funds from an account
- **D (Deposit)**: Deposit funds into an account
- **C (Clear Check)**: Process a check withdrawal
- **N (New Account)**: Create a new account
- **B (Balance Inquiry)**: Check account balance
- **I (Account Info)**: View detailed account information
- **H (History)**: View account info and transaction history
- **S (Close Account)**: Close an existing account
- **R (Reopen)**: Reopen a previously closed account
- **X (Delete)**: Permanently delete an account
- **Q (Quit)**: Exit the application

## Architecture & Design Patterns

### Package Organization

1. **ui**: User interface layer - handles all user interactions
2. **models**: Domain model - core business entities
3. **accounts**: Account implementations - specialized account types
4. **transactions**: Transaction handling - processing and recording
5. **generators**: Abstract base classes - shared behavior and interfaces

### Design Principles

- **Single Responsibility Principle**: Each class has one reason to change
- **Open/Closed Principle**: Classes open for extension, closed for modification
- **Inheritance Hierarchy**: Common behavior abstracted to base classes
- **Encapsulation**: Data and methods appropriately scoped

### Key Classes

**Bank**
- Manages all accounts in the system
- Tracks account statistics and totals
- Processes transactions

**Account**
- Base class for all account types
- Handles common account operations
- Maintains transaction history

**SavingsAccount / CheckingAccount / CDAccount**
- Specialized account implementations
- Implement account-type-specific rules

**TransactionTicket / TransactionReceipt**
- Request/response pattern for transactions
- Provides detailed transaction information

## Data Files

### initAccounts.txt
Initial database of accounts loaded at startup. Format:
```
LastName FirstName SSN AccountNumber AccountType Status Balance [MaturityDate for CD]
```

### myTestCases.txt
Predefined test transactions for automated testing.

## Development Guidelines

### Code Style
- Follow Java naming conventions
- Use meaningful variable and method names
- Include Javadoc comments for public classes and methods
- Maintain consistent indentation (4 spaces)

### Testing
- Place unit tests in `src/test/java`
- Follow naming convention: `*Test.java`
- Run tests: `mvn test`

### Version Management
- Semantic versioning: MAJOR.MINOR.PATCH
- Current version: 1.0.0

## Building & Deployment

### Generate Documentation
```bash
mvn javadoc:javadoc
```
Documentation will be generated in `target/site/apidocs/`

### Run Specific Test
```bash
mvn test -Dtest=YourTestClassName
```

### Generate Project Report
```bash
mvn site
```

## Maven Commands Reference

| Command | Purpose |
|---------|---------|
| `mvn clean` | Delete build directory |
| `mvn compile` | Compile source code |
| `mvn test` | Run unit tests |
| `mvn package` | Create JAR package |
| `mvn install` | Install JAR to local repository |
| `mvn clean package` | Clean and rebuild |
| `mvn exec:java -Dexec.mainClass="ui.pgm"` | Run application |

## Future Enhancements

- [ ] Unit test suite
- [ ] Database persistence layer
- [ ] REST API endpoints
- [ ] Web UI (Spring Boot/JSF)
- [ ] Account interest calculations
- [ ] Transaction fee management
- [ ] Comprehensive logging
- [ ] Exception handling improvements
- [ ] Authentication & authorization
- [ ] Transaction audit trail

## Project Standards

This project adheres to professional Java development standards:

✅ **Package Organization**: Maven standard directory structure
✅ **Build Management**: Maven POM configuration
✅ **Code Documentation**: Javadoc and package-info files
✅ **Version Control**: Git-ready with .gitignore
✅ **Dependency Management**: Centralized Maven dependency resolution
✅ **Compilation**: Java 11 compatibility
✅ **Executable JAR**: Runnable as standalone application

## Troubleshooting

### Build Errors
```bash
# Clear Maven cache and rebuild
mvn clean install -U
```

### Resource File Not Found
- Ensure resource files are in `src/main/java/resources/`
- Rebuild the project: `mvn clean package`