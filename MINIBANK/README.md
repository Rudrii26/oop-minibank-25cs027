# MiniBank — Object-Oriented Banking System

**Course:** B.Tech CSE — Object Oriented Programming (CEUE203)  
**Project:** MiniBank Progressive Capstone (Practicals 1–14)

---

## 📌 Project Overview

**MiniBank** is a comprehensive, production-grade console banking application developed progressively across 14 laboratory sessions. It demonstrates core and advanced Object-Oriented Programming (OOP) paradigms in Java, evolving from basic classes to interface-driven design, reflection, custom exception handling, concurrent transaction processing, file persistence with Java NIO, and generic repository architectures.

---

## 🏗️ Layered Architecture & Package Layout

```text
MINIBANK-PROJECT/
│
├── model/                               # Domain models & abstractions
│   ├── Account.java                     # Base account (abstract, Transactable, InterestBearing, Serializable, Comparable)
│   ├── SavingsAccount.java              # Savings account with min-balance & 4% interest (implements Premium)
│   ├── CurrentAccount.java              # Current account with overdraft limit & 0% interest
│   ├── FixedDepositAccount.java         # Fixed deposit with locked withdrawals & 7% interest
│   ├── Customer.java                    # Customer entity with static nested Address class
│   ├── BankInfo.java                    # Record for bank metadata
│   ├── Command.java                     # Record for parsed banking commands
│   ├── TransactionType.java             # Enum: DEPOSIT, WITHDRAW, TRANSFER
│   ├── MenuOption.java                  # Enum: OPEN_ACCOUNT, DEPOSIT, WITHDRAW, TRANSFER, EXIT
│   ├── Transactable.java                # Interface for deposit/withdraw contracts
│   ├── InterestBearing.java             # Interface with default yearlyInterest() calculation
│   ├── WithdrawRule.java                # Functional interface for withdrawal rules
│   ├── Premium.java                     # Marker interface for privileged accounts
│   └── annotation/                      # Custom runtime annotations
│       ├── Id.java                      # Field marker for entity identifiers
│       ├── Positive.java                # Validation annotation with custom message
│       └── MaxLength.java               # String length validation annotation
│
├── exception/                           # Custom checked exception hierarchy
│   ├── BankException.java               # Base checked bank exception
│   ├── InsufficientFundsException.java  # Carries shortfall amount
│   ├── AccountNotFoundException.java    # Thrown when account lookup fails
│   └── InvalidAmountException.java      # Thrown for non-positive transaction amounts
│
├── util/                                # Utility & formatting tools
│   ├── Validator.java                   # Regex input validators (Mobile, Email, PAN, IFSC, Amount)
│   ├── StatementFormatter.java          # Multi-line statement string builder
│   ├── AnnotationValidator.java         # Reflection-based object validator
│   └── BankTransactionSession.java      # AutoCloseable session for try-with-resources
│
├── service/                             # Business logic, persistence & processing
│   ├── BankService.java                 # Central service coordinating repository, rules, and logs
│   ├── Repository.java                  # Generic repository with reflective @Id resolution & bounded average
│   ├── AccountStore.java                # HashMap/ArrayList storage with Comparator balance sorting
│   ├── TransactionProcessor.java        # ExecutorService thread pool & ConcurrentHashMap
│   ├── TransactionBuffer.java           # Thread-safe producer-consumer circular buffer (wait/notify)
│   ├── TransactionLog.java              # Append-only transaction logger using Java NIO
│   ├── ReportGenerator.java             # NIO directory stream & attribute-based end-of-day reporter
│   ├── StatePersister.java              # Binary object serializer/deserializer
│   ├── AccountWorker.java               # Multithreaded deposit worker (Runnable)
│   ├── CommandParser.java               # Text command tokenizer & parser
│   └── MiniBank.java                    # Main console application driver & interactive CLI
│
├── test/                                # Capstone unit verification
│   └── BankServiceTest.java             # JUnit 5 automated test suite
│
├── lib/                                 # Testing libraries
│   └── junit-platform-console-standalone-1.10.2.jar # JUnit 5 standalone test launcher
│
├── minibank.jar                         # Pre-built runnable JAR artifact
└── Account_Backup.java                  # Preserved legacy Account implementation
```

---

## 📋 Practicals 1–14 Feature Roadmap

| Practical | Topic | Implemented Features |
| :--- | :--- | :--- |
| **Lab 1** | Language Basics & Menu | Main loop, `BankInfo` record, `MenuOption` enum, modern switch expressions. |
| **Lab 2** | Encapsulation & Classes | `Account` and `Customer` classes, private fields, controlled deposit/withdraw methods. |
| **Lab 3** | Methods & Nested Classes | Overridden `toString()`, `equals()`, `hashCode()`, static nested `Address`, `Cloneable` customer. |
| **Lab 4** | Strings & Regex | `Validator` with precompiled `Pattern` (mobile, email, PAN, IFSC), `CommandParser`, `StatementFormatter`. |
| **Lab 5** | Inheritance & Polymorphism | Abstract `Account`, polymorphic array dispatch, `SavingsAccount`, `CurrentAccount`, `FixedDepositAccount`, pattern matching with `instanceof`. |
| **Lab 6** | Interfaces & Packaging | `Transactable`, `InterestBearing` with default method, `WithdrawRule` functional interface (lambda + anonymous class), `Premium` marker interface, package structure, static imports. |
| **Lab 7** | Annotations & Reflection | `@Id`, `@Positive`, `@MaxLength` custom annotations; `AnnotationValidator` reflecting through class hierarchy. |
| **Lab 8** | Exception Handling | `BankException` hierarchy, `InsufficientFundsException` with shortfall, `transfer()` with `try-catch-finally`, custom `AutoCloseable` for `try-with-resources`. |
| **Lab 9** | Threads & Synchronisation | Multithreaded race condition demonstration, synchronized thread-safe deposit/withdraw, thread lifecycle tracking. |
| **Lab 10** | Concurrency & Deadlocks | `TransactionProcessor` with `Executors.newFixedThreadPool(4)`, `TransactionBuffer` producer-consumer using `wait()`/`notifyAll()`, canonical lock ordering deadlock prevention. |
| **Lab 11** | Serialization & NIO | `Serializable` models, binary state persistence (`StatePersister`), `Files.write()` transaction logging (`TransactionLog`), directory stream report generator (`ReportGenerator`). |
| **Lab 12** | Collections Framework | `AccountStore` with `HashMap` and `ArrayList`, `Comparator` balance sorting, `Comparable<Account>` natural ordering, `ConcurrentHashMap` in processor. |
| **Lab 13** | Generics & Repository | Generic `Repository<T>`, reflective `@Id` lookup, `@SafeVarargs saveAll()`, bounded generic `average()`, demonstrated with `Account` and `Customer`. |
| **Lab 14** | Final Capstone & CLI | Layered `BankService`, interactive CLI menu, startup loading & shutdown persistence, automated JUnit 5 test suite (`BankServiceTest`). |

---

## ⚙️ Prerequisites

- **Java Development Kit (JDK):** JDK 17 or later (verified on JDK 21+ and JDK 26)
- **Terminal:** PowerShell, Command Prompt, or Bash

---

## 🔨 Compilation Instructions

Compile all packages and test suites into a designated output directory (`out/`) including the testing library:

```powershell
javac -cp "lib/*" -d out model/*.java model/annotation/*.java util/*.java exception/*.java service/*.java test/*.java
```

---

## 🚀 Running the Application

### Option A: Direct Class Execution
```powershell
java -cp out service.MiniBank
```

### Option B: Building and Running the Standalone JAR
Package the application into an executable JAR:
```powershell
jar --create --file minibank.jar --main-class service.MiniBank -C out .
```
Run the JAR:
```powershell
java -jar minibank.jar
```

---

## 🧪 Running JUnit 5 Unit Tests

Run the automated JUnit 5 tests via either of the following approaches:

### Approach A: JUnit 5 Standalone Console Launcher (Recommended)
```powershell
java -jar lib/junit-platform-console-standalone-1.10.2.jar --class-path out --select-class test.BankServiceTest
```

### Approach B: Direct Execution
```powershell
java -cp "lib/*;out" test.BankServiceTest
```

**Test Cases Verified:**
1. `testDepositIncreasesBalance`: Verifies that a valid deposit increases the account balance.
2. `testWithdrawalOverLimitThrowsException`: Confirms that withdrawing beyond available balance or below the minimum balance constraint throws `InsufficientFundsException`.
3. `testConcurrentDepositsKeepBalanceCorrect`: Executes 10 concurrent threads each depositing 1,000 times, confirming that thread synchronization guarantees a balance of exactly 10,000.

---

## 🎮 Interactive Menu Options

When launched interactively, MiniBank presents the following options:

1. **Open Account:** Interactively creates Savings, Current, or Fixed Deposit accounts with input validation.
2. **Deposit:** Adds funds to an existing account with positive-amount validation.
3. **Withdraw:** Withdraws funds while enforcing subtype rules (minimum balance, overdraft limits, locked deposits).
4. **Transfer:** Safely transfers funds between accounts using deadlock-free lock ordering.
5. **Exit:** Persists current account states to `data/accounts.dat` and terminates cleanly.
6. **Account Statement:** Formats and prints a multi-line statement for an account.
7. **End-of-Day Audit Report:** Scans transaction log files via Java NIO and generates a financial summary report.
