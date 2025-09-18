package com.example.todo.service;

import com.example.todo.dto.TodoRequestDTO;
import com.example.todo.dto.TodoResponse;
import com.example.todo.exception.TodoNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TodoServiceTest {

    @Autowired
    private TodoService todoService;

    // ✅ Test: Add valid task
    @Test
    void testAddTask() {
        TodoRequestDTO request = new TodoRequestDTO();
        request.setTitle("Complete Assignment");
        request.setDescription("Finish Spring Boot assignment");
        request.setCompleted(false);

        TodoResponse savedTask = todoService.createTodo(request);

        assertNotNull(savedTask.getId(), "Task ID should be generated");
        assertEquals("Complete Assignment", savedTask.getTitle(), "Task title should match input");
        assertFalse(savedTask.isCompleted(), "Task should not be completed by default");
    }

    // ✅ Test: Invalid input (empty title)
    @Test
    void testAddTask_InvalidInput() {
        TodoRequestDTO request = new TodoRequestDTO();
        request.setTitle(""); // Invalid title
        request.setDescription("Some description");

        Exception exception = assertThrows(Exception.class, () -> {
            todoService.createTodo(request);
        });

        assertTrue(exception.getMessage().contains("Title"), "Error message should mention title");
    }

    // ✅ Test: Update existing task
    @Test
    void testUpdateTask() {
        // First create
        TodoRequestDTO request = new TodoRequestDTO();
        request.setTitle("Old Task");
        request.setDescription("Old Desc");
        request.setCompleted(false);
        TodoResponse created = todoService.createTodo(request);

        // Update it
        TodoRequestDTO updateRequest = new TodoRequestDTO();
        updateRequest.setTitle("Updated Task");
        updateRequest.setDescription("Updated Desc");
        updateRequest.setCompleted(true);

        TodoResponse updated = todoService.updateTodo(created.getId(), updateRequest);

        assertEquals("Updated Task", updated.getTitle());
        assertEquals("Updated Desc", updated.getDescription());
        assertTrue(updated.isCompleted());
    }

    // ✅ Test: Delete task
    @Test
    void testDeleteTask() {
        TodoRequestDTO request = new TodoRequestDTO();
        request.setTitle("Task to delete");
        request.setDescription("Temp task");
        TodoResponse created = todoService.createTodo(request);

        todoService.deleteTodo(created.getId());

        assertThrows(TodoNotFoundException.class, () -> {
            todoService.getTodoById(created.getId());
        });
    }
}
