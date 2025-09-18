// File: todo-app-backend/src/main/java/com/example/todo/controller/TodoController.java
package com.example.todo.controller;

import com.example.todo.dto.TodoRequestDTO;
import com.example.todo.dto.TodoResponse;
import com.example.todo.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    // GET /api/todos
    @GetMapping
    public ResponseEntity<List<TodoResponse>> getAllTodos() {
        List<TodoResponse> todos = todoService.getAllTodos();
        return ResponseEntity.ok(todos);
    }

    // GET /api/todos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<TodoResponse> getTodoById(@PathVariable("id") Long id) {
        TodoResponse todo = todoService.getTodoById(id);
        return ResponseEntity.ok(todo);
    }

    // POST /api/todos
    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(@Valid @RequestBody TodoRequestDTO dto) {
        TodoResponse created = todoService.createTodo(dto);
        // include Location header for REST best practice
        URI location = URI.create(String.format("/api/todos/%d", created.getId()));
        return ResponseEntity.created(location).body(created);
    }

    // PUT /api/todos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<TodoResponse> updateTodo(
            @PathVariable("id") Long id,
            @Valid @RequestBody TodoRequestDTO dto) {
        TodoResponse updated = todoService.updateTodo(id, dto);
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/todos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable("id") Long id) {
        todoService.deleteTodo(id); // will throw TodoNotFoundException if not found
        return ResponseEntity.noContent().build();
    }
}
