//todo-app-backend\src\main\java\com\example\todo\service\TodoService.java
package com.example.todo.service;

import com.example.todo.dto.TodoRequestDTO;
import com.example.todo.dto.TodoResponse;
import com.example.todo.exception.TodoNotFoundException;
import com.example.todo.mapper.TodoMapper;
import com.example.todo.model.Todo;
import com.example.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<TodoResponse> getAllTodos() {
        return todoRepository.findAll()
                .stream()
                .map(TodoMapper::toResponse)
                .collect(Collectors.toList());
    }

    public TodoResponse getTodoById(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + id));
        return TodoMapper.toResponse(todo);
    }

    public TodoResponse createTodo(TodoRequestDTO dto) {
        Todo todo = TodoMapper.toEntity(dto);
        Todo saved = todoRepository.save(todo);
        return TodoMapper.toResponse(saved);
    }

    public TodoResponse updateTodo(Long id, TodoRequestDTO dto) {
        Todo existing = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + id));

        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        existing.setCompleted(Boolean.TRUE.equals(dto.getCompleted()));

        Todo saved = todoRepository.save(existing);
        return TodoMapper.toResponse(saved);
    }

    public void deleteTodo(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new TodoNotFoundException("Todo not found with id: " + id);
        }
        todoRepository.deleteById(id);
    }
}
