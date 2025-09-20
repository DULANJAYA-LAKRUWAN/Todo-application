Feature: Add a new todo
  As a user
  I want to add a new task
  So that I can track my work

  Scenario: Successfully add a todo
    Given the system is running
    When I create a todo with title "Buy milk" and description "2L whole milk"
    Then the response should have status 201
    And the response should contain a todo with title "Buy milk"

  Scenario: Validation error when title is blank
    Given the system is running
    When I create a todo with title "" and description "anything"
    Then the response should have status 400
