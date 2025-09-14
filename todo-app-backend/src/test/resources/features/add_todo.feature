Feature: Add a new todo
  As a user
  I want to add a new task
  So that I can track my work

  Scenario: Successfully add a todo
    Given a todo with title "Buy milk" and description "2L whole milk"
    When the client creates the todo
    Then the response status should be 201

  Scenario: Validation error when title is blank
    Given a todo with title "" and description "anything"
    When the client creates the todo
    Then the response status should be 400