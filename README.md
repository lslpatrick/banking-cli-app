# Banking CLI App

**Banking CLI App is a Java command-line banking simulator that demonstrates object-oriented software design, modular CLI architecture, business-rule modeling, and automated testing. Originally developed as a university project, it has since been refactored into a stronger portfolio application.**

## Highlights

- Designed a modular object-oriented banking system using domain-driven classes and reusable menu workflows
- Refactored account modeling with Java `enum` types to improve type safety and maintainability
- Centralized CLI validation through reusable input utilities to improve robustness against invalid user input
- Implemented banking business rules including overdraft protection, account freezing, administrator controls, and savings interest
- Added automated JUnit 5 test coverage for banking logic, customer authentication, and CLI interaction flows

### Customer

- PIN setup and authentication
- Checking and savings accounts
- Deposits and withdrawals
- Balance inquiry
- Transaction history
- Account switching
- Inter-account transfers
- Bank statement generation

### Administrator

- Password-protected admin mode
- Freeze and unfreeze accounts
- Collect maintenance fees
- Configure savings interest rate
- Apply savings interest
