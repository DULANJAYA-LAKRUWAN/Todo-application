//  File: todo-app-backend/src/main/java/com/example/todo/controller/TodoController.java
package com.example.todo.controller;

import com.example.todo.dto.TodoRequest;
import com.example.todo.dto.TodoResponse;
import com.example.todo.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<List<TodoResponse>> getAllTodos() {
        return ResponseEntity.ok(todoService.getAllTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponse> getTodoById(@PathVariable Long id) {
        return ResponseEntity.ok(todoService.getTodoById(id));
    }

 @PostMapping
public ResponseEntity<Todo> createTodo(@Valid @RequestBody TodoRequestDTO todoDto) {
    Todo todo = new Todo();
    todo.setTitle(todoDto.getTitle().trim());
    todo.setDescription(todoDto.getDescription());
    todo.setCompleted(todoDto.getCompleted() != null ? todoDto.getCompleted() : false);
    Todo createdTodo = todoService.createTodo(todo);
    return new ResponseEntity<>(createdTodo, HttpStatus.CREATED);
}

@PutMapping("/{id}")
public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @Valid @RequestBody TodoRequestDTO todoDto) {
    Todo todo = new Todo();
    todo.setTitle(todoDto.getTitle().trim());
    todo.setDescription(todoDto.getDescription());
    todo.setCompleted(todoDto.getCompleted() != null ? todoDto.getCompleted() : false);
    Todo updatedTodo = todoService.updateTodo(id, todo);
    return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }
}
