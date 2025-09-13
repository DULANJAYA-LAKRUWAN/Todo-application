# Todo Application – Assignment Documentation

## 1. Project Overview

This project is a full-stack Todo application developed for the "Software Testing and Quality for Spring Boot Application" assignment. It demonstrates TDD, BDD, automated testing, and CI/CD practices using Spring Boot (Java), MySQL, React (JavaScript), and supporting tools.

---

## 2. Technologies Used

- **Backend:** Spring Boot, Spring Data JPA, MySQL, JUnit, Cucumber, REST Assured
- **Frontend:** React, JavaScript, Selenium WebDriver
- **DevOps:** GitHub Actions (or Jenkins) for CI/CD

---

## 3. Backend Structure

- **Entities:**  
  - `Todo`: id, title, description, completed, createdAt, updatedAt  
  - `Task`: id, title, dueDate

- **Repositories:**  
  - `TodoRepository` and `TaskRepository` (Spring Data JPA)

- **Services:**  
  - `TodoService` and `TaskService` for business logic

- **Controllers:**  
  - `TodoController` and `TaskController` for RESTful API endpoints

- **Database:**  
  - MySQL (`todo_db`), tables: `todos`, `task`

---

## 4. Frontend Structure

- **Components:**  
  - `Header`, `TodoForm`, `TodoList`, `TodoItem`
- **Features:**  
  - Login, add item, list tasks

---

## 5. Testing

### 5.1 Test-Driven Development (TDD)

- JUnit unit tests for core features (add task, validate input)
- Red-Green-Refactor cycle followed

### 5.2 Behavior-Driven Development (BDD)

- Gherkin feature files (e.g., add new task)
- Cucumber step definitions in Java
- Automated scenario execution

### 5.3 API Testing

- Postman collection for two endpoints (add, get todos)
- REST Assured tests for response codes, payloads, error handling

### 5.4 UI Testing

- Selenium WebDriver scripts for login and add item scenarios

---

## 6. CI/CD Pipeline

- GitHub Actions (or Jenkins) configured to:
  - Build the project
  - Run all unit and automation tests
  - Demonstrate successful pipeline run

---

## 7. Setup Instructions

### 7.1 Backend

1. Install MySQL and create `todo_db` database.
2. Update `src/main/resources/application.properties` with your MySQL credentials.
3. Run `mvn spring-boot:run` to start the backend server.

### 7.2 Frontend

1. Navigate to `todo-frontend` folder.
2. Run `npm install` to install dependencies.
3. Run `npm start` to launch the frontend.

### 7.3 Database Schema

```
CREATE DATABASE IF NOT EXISTS todo_db;
USE todo_db;

CREATE TABLE IF NOT EXISTS todos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    completed BOOLEAN DEFAULT FALSE,
    created_at DATETIME,
    updated_at DATETIME
);

CREATE TABLE IF NOT EXISTS task (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    due_date DATE
);
```

### 7.4 Sample Data

```
INSERT INTO todos (title, description, completed, created_at, updated_at)
VALUES
('Buy groceries', 'Milk, Bread, Eggs', false, NOW(), NOW()),
('Finish assignment', 'Complete the Spring Boot project', false, NOW(), NOW());

INSERT INTO task (title, due_date)
VALUES
('Prepare presentation', '2025-09-15'),
('Call client', '2025-09-14');
```

---

## 8. How Requirements Are Met

- **TDD:** Unit tests written before feature implementation.
- **BDD:** Gherkin feature files and Cucumber step definitions.
- **API/UI Automation:** Postman, REST Assured, Selenium scripts included.
- **CI/CD:** Pipeline configured and demonstrated.
- **Validation:** Input validation for todos and tasks.
- **Documentation:** This file covers all assignment requirements.

---

## 9. Additional Notes

- All code is organized and commented for clarity.
- Test results and pipeline logs are available for review.
- The project is ready for demonstration and viva.

---

## 10. Screenshots, Test Results, and Pipeline Logs

- **Screenshots:**
  - [Insert screenshots of running backend, frontend, and test execution here]
- **Test Results:**
  - [Paste or screenshot results from `mvn test`, Selenium, and Postman]
- **Pipeline Logs:**
  - [Insert screenshots or logs from GitHub Actions/Jenkins pipeline runs]

---

*Attach images and logs in the Word document after exporting this file.*
