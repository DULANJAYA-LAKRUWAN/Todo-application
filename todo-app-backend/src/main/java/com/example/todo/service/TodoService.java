package com.example.todo.service;

import com.example.todo.model.Todo;
import com.example.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    // ✅ Get all todos
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    // ✅ Get a single todo by ID
    public Todo getTodoById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));
    }

    // ✅ Create a new todo
    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    // ✅ Update an existing todo
    public Todo updateTodo(Long id, Todo updatedTodo) {
        Todo existingTodo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));

        // Update fields
        existingTodo.setTitle(updatedTodo.getTitle());
        existingTodo.setDescription(updatedTodo.getDescription());
        existingTodo.setCompleted(updatedTodo.isCompleted());

        return todoRepository.save(existingTodo);
    }

    // ✅ Delete a todo
    public void deleteTodo(Long id) {
        Todo existingTodo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));

        todoRepository.delete(existingTodo);
    }
}
