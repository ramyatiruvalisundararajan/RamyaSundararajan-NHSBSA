# NHS Jobs Search Functional Automation Suite

---

## Overview
This project automates the functional acceptance tests for the NHS Jobs search functionality. It uses modern tools and follows BDD best practices to ensure robust, maintainable tests.

### Technologies Used
* **Java 21**
* **Selenium WebDriver** (latest stable)
* **Cucumber BDD framework**
* **WebDriverManager** for automatic driver management
* Supports **Chrome** and **Firefox** browsers

---

## Prerequisites
Make sure you have the following installed and configured:
* **Java 21** (added to your system PATH)
* **Maven**
* **Git**
* **Internet connection** (required for WebDriverManager to download browser drivers)

---

## Running the Tests

### Run tests on Chrome (default)
```bash
mvn clean test 
```
---

### Run tests on Firefox
```bash
mvn clean test -Dbrowser=firefox
```

---

## Project Structure

* **src/main/java/com/nhsbsa/test/pages** (Page Object classes)
* **src/test/java/com/nhsbsa/test/steps** (Cucumber step definitions)
* **src/test/resources/features** (Feature files for BDD scenarios)
* **pom.xml** ( Maven build and dependency configuration)

---

## Notes
* The project uses **WebDriverManager**, so you do not need to download browser drivers manually.
* Tests run in **headful mode** (browser UI visible) by default. To run headless, update the WebDriver setup in `NHSJobsSearchSteps.java` (e.g., add ChromeOptions/FirefoxOptions for headless mode).
* Scenarios are designed with user-centric BDD principles for clarity, reusability, and maintainability.
