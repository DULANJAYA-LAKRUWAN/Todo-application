package com.example.todo.service;

import com.example.todo.exception.TodoNotFoundException;
import com.example.todo.model.Todo;
import com.example.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    // Get all todos
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    // Get a todo by ID
    public Todo getTodoById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id " + id));
    }

    // Create a todo
    public Todo createTodo(Todo todo) {
        validateTodoForCreate(todo);
        return todoRepository.save(todo);
    }

    private void validateTodoForCreate(Todo todo) {
        if (todo == null || !StringUtils.hasText(todo.getTitle())) {
            throw new IllegalArgumentException("Title is mandatory");
        }
        if (todo.getTitle().length() > 100) {
            throw new IllegalArgumentException("Title must be between 1 and 100 characters");
        }
    }

    // Update todo
    public Todo updateTodo(Long id, Todo todo) {
        Todo existingTodo = getTodoById(id);
        existingTodo.setTitle(todo.getTitle());
        existingTodo.setDescription(todo.getDescription());
        existingTodo.setCompleted(todo.isCompleted());
        return todoRepository.save(existingTodo);
    }

    // Delete a todo
    public void deleteTodo(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new TodoNotFoundException("Todo not found with id " + id);
        }
        todoRepository.deleteById(id);
    }
}
