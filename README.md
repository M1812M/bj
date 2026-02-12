# BJ (Blackjack)

Simple command-line Blackjack implementation in Java.

## Features
- Multi-seat table with configurable seat count.
- Betting, hit, stand, double, and split actions.
- Dealer draw logic and payout handling.
- Unit tests for core model/rules behavior.

## Project Structure
- `src/eu/merty/app/java/bj/model`: game model and blackjack rules.
- `src/eu/merty/app/java/bj/controller`: game loop and round flow.
- `src/eu/merty/app/java/bj/view`: command-line UI rendering/input.
- `test/eu/merty/app/java/bj`: unit tests.

## Run (without build tool)
Compile:

```powershell
New-Item -ItemType Directory -Force out | Out-Null
javac -d out (Get-ChildItem -Recurse -Path src -Filter *.java | ForEach-Object { $_.FullName })
```

Start:

```powershell
java -cp out eu.merty.app.java.bj.controller.BJController
```

## Tests
Tests are written with JUnit 5 under `test/`.
If you use a build tool (Maven/Gradle), point it at `src/` and `test/` and run the test task.
