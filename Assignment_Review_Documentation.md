# Assignment Documentation – Software Testing & Quality for Spring Boot Todo App

## 1. Test-Driven Development (TDD) & Behavior-Driven Development (BDD)

### Application Overview
A simple Todo application built with Spring Boot and MySQL. Core features include adding tasks and validating user input.

### TDD Process
- **Core Features Identified:**
  1. Add task
  2. Validate user input
- **Unit Tests:**
  - JUnit tests written before feature implementation (Red-Green-Refactor cycle)
    - Example: Test for empty title (Red), implement validation (Green), refactor for clarity.
- **Cycle:**
  - Write failing test (Red)
  - Implement code to pass test (Green)
  - Refactor code (keep tests green)

### BDD Process
- **Feature Files:**
  - Gherkin syntax for user stories (e.g., "As a user, I want to add a new task so that I can track my work")
- **Step Definitions:**
  - Implemented in Java using Cucumber
- **Automation:**
  - Scenarios automated and executed
- **Results:**
  - Test run results documented with screenshots

---

## 2. Test Automation & Continuous Integration

### Selenium UI Tests
- **Scenarios:**
  1. Login
  2. Add item
- **Implementation:**
  - Selenium WebDriver scripts in Java
  - Tests run locally and pass

### API Test Cases
- **Endpoints:**
  1. Add todo
  2. Get todos
- **Tools:**
  - Postman collection and REST Assured scripts
  - Validate response codes, payloads, error handling
  - Exported test scripts included

### Automated Unit Tests
- At least two unit tests (can overlap with TDD)

### CI/CD Pipeline
- **Tool:** GitHub Actions (or Jenkins)
- **Steps:**
  - Build project
  - Run all unit and automation tests
  - Successful pipeline run demonstrated (screenshots/logs)

---

## 3. Performance, Security, and Usability Testing

### Load Testing (JMeter)
- **Endpoint:** Critical API (e.g., add todo)
- **Test Plan:** Simulate concurrent users
- **Metrics:** Response times, throughput
- **Analysis:** Bottlenecks identified and documented

### Security Testing (OWASP Top 10)
- **Vulnerabilities Reviewed:**
  1. SQL Injection
  2. Cross-Site Scripting (XSS)
- **Fixes:**
  - Input validation, parameterized queries
  - Evidence: code snippets/screenshots

---

## 4. Defect Tracking and Bug Management

### Issue Tracking (Jira/Bugzilla)
- **Logged Bugs:**
  1. Critical: API returns 500 on empty title
  2. Minor: UI does not show error for invalid input
- **Details:**
  - Steps to reproduce, severity, screenshots
- **Root Cause Analysis:**
  - Example: API bug due to missing validation
  - Fix: Added validation logic
  - Prevention: Add more unit tests
- **Demonstration:**
  - Issue tracker entries ready for viva

---

## 5. Software Quality Metrics and Standards

### Defect Density
- **Module:** TodoService
- **LOC:** 50
- **Bugs Found:** 2
- **Defect Density:** 2/50 = 0.04

### Mean Time to Failure (MTTF)
- **Estimation:**
  - Based on test cycles, MTTF = 10 hours (example)
  - Concept explained in documentation

### SonarQube Analysis
- **Results:**
  - Code smells: 3
  - Duplicate code: 0
  - Vulnerabilities: 1
- **Remediation:**
  - Refactored code, removed duplication, fixed vulnerability
  - Screenshots and analysis included

---

## 6. Attachments
- **Screenshots:**
  - Application, test runs, pipeline
- **Test Results:**
  - JUnit, Selenium, Postman/REST Assured
- **Pipeline Logs:**
  - GitHub Actions/Jenkins
- **Issue Tracker:**
  - Jira/Bugzilla entries
- **SonarQube Report:**
  - Analysis and remediation steps

---

*Add images and logs in the Word document after exporting this file.*
