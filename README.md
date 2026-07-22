# Banking CLI App

Java | JUnit 5 | Bash | OOP | CLI Application Design

**Banking CLI App is a Java command-line banking simulator that demonstrates object-oriented software design, modular CLI architecture, business-rule modeling, and automated testing. Originally developed as a university project, it has since been refactored into a stronger portfolio application.**

## Highlights

- Designed a modular object-oriented banking system with separated domain, workflow, and input-handling layers
- Refactored account modeling with Java `enum` types to improve type safety and maintainability
- Centralized CLI validation through reusable input utilities to improve robustness against invalid user input
- Implemented banking business rules including overdraft protection, account freezing, administrator controls, and savings interest
- Added automated JUnit 5 test coverage for banking logic, customer authentication, and CLI interaction flows

## Customer Features

- PIN setup and authentication
- Checking and savings accounts
- Deposits and withdrawals
- Balance inquiry
- Transaction history
- Account switching
- Inter-account transfers
- Bank statement generation

## Administrator Features

- Password-protected admin mode
- Freeze and unfreeze accounts
- Collect maintenance fees
- Configure savings interest rate
- Apply savings interest

## Design Goals

- Separate business logic from CLI interaction flow
- Keep account behavior encapsulated inside domain objects
- Make input handling consistent and reusable across menus
- Model banking rules explicitly instead of scattering logic across the interface layer

## Architecture Notes

The main implementation is organized around a small set of collaborating classes:

- `Bank` manages the account collection, selected account state, customer PIN logic, transfers, and statement generation
- `BankAccount` models per-account state and rules such as deposits, withdrawals, overdraft behavior, interest, and freeze status
- `MainMenu`, `CustomerMenu`, and `AdminMenu` define the interactive CLI workflows
- `InputHelper` centralizes menu parsing, numeric validation, and required-text input handling
- `AccountType` provides stronger type safety for checking and savings account behavior

## Project Structure

```text
src/
  main/
    AccountType.java
    AdminMenu.java
    Bank.java
    BankAccount.java
    CustomerMenu.java
    InputHelper.java
    MainMenu.java
  test/
    BankAccountTest.java
    BankTest.java
    CustomerPinTest.java
    MainMenuTest.java
runApp.sh
test-lib/
```

## Build and Run

```bash
chmod +x runApp.sh
./runApp.sh
```

This compiles the Java source files into `bin/` and launches the CLI application.

## Run Tests

```bash
mkdir -p bin test-bin
javac -d bin src/main/*.java
javac -cp "bin:test-lib/junit-platform-console-standalone-1.13.0-M3.jar" -d test-bin src/test/*.java
java -jar test-lib/junit-platform-console-standalone-1.13.0-M3.jar execute --class-path "bin:test-bin" --scan-class-path
```

The current test suite covers account operations, PIN logic, statement generation, overdraft rules, admin functionality, and input validation behavior.

## Example Workflow

```text
1. Create a 4-digit customer PIN
2. Deposit money into the default checking account
3. Create a savings account
4. Transfer funds between accounts
5. Generate a bank statement as a .txt file
6. Enter administrator mode to freeze accounts or update savings interest
```

## Why This Project

Rather than simulating a full banking platform, Banking CLI App focuses on software design fundamentals. The project demonstrates how domain modeling, layered CLI workflows, validation helpers, and automated tests can be combined to build a structured Java application with clear separation of responsibilities. It also shows iterative refactoring from a class project into a cleaner, more presentation-ready portfolio piece.

## Future Improvements

- Persist account and transaction data instead of storing everything in memory
- Replace hardcoded administrator credentials with a safer configuration approach
- Use `BigDecimal` for money values instead of `double`
- Add timestamps and richer formatting to generated bank statements
