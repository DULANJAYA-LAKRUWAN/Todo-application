Feature: Todo Management
  As a user
  I want to manage my todos
  So that I can track my tasks efficiently

  Scenario: Create a new todo
    Given a todo with title "Buy milk" and description "2 liters"
    When the client creates the todo
    Then the response status should be 201

  Scenario: Get all todos
    When the client requests all todos
    Then the response status should be 200

  Scenario: Get todo by ID
    When the client requests the todo with id 1
    Then the response status should be 200

  Scenario: Update an existing todo
    Given an updated todo with title "Buy milk" and description "1 liter" and completed "true"
    When the client updates the todo with id 1
    Then the response status should be 200

  Scenario: Delete a todo
    When the client deletes the todo with id 1
    Then the response status should be 204
