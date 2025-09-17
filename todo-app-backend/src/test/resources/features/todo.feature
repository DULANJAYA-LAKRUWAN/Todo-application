Feature: Todo Management
  As a user
  I want to manage my todos
  So that I can keep track of my tasks

  Scenario: Create a new todo
    Given the system is running
    When I create a todo with title "Buy milk" and description "2 liters"
    Then the response should have status 201
    And the response should contain a todo with title "Buy milk"

  Scenario: Get a todo by ID
    Given the system has a todo with title "Clean room" and description "Bedroom"
    When I retrieve the todo by its ID
    Then the response should have status 200
    And the response should contain a todo with title "Clean room"
