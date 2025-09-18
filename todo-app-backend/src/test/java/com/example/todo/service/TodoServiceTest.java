package com.example.todo.service;

import com.example.todo.model.Todo;
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
        Todo task = new Todo();
        task.setTitle("Complete Assignment");
        task.setDescription("Finish Spring Boot assignment");

        Todo savedTask = todoService.createTodo(task);

        assertNotNull(savedTask.getId(), "Task ID should be generated");
        assertEquals("Complete Assignment", savedTask.getTitle(), "Task title should match input");
    }

    // ❌ Test: Invalid input
    @Test
    void testAddTask_InvalidInput() {
        Todo task = new Todo();
        task.setTitle(""); // Invalid title

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            todoService.createTodo(task);
        });

        assertEquals("Title is mandatory", exception.getMessage());
    }
}
