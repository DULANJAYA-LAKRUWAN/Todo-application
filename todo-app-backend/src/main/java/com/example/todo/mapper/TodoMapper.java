//todo-app-backend\src\main\java\com\example\todo\mapper\TodoMapper.java
package com.example.todo.mapper;

import com.example.todo.dto.TodoRequestDTO;
import com.example.todo.dto.TodoResponse;
import com.example.todo.model.Todo;

public class TodoMapper {

    public static Todo toEntity(TodoRequestDTO dto) {
        Todo todo = new Todo();
        todo.setTitle(dto.getTitle());
        todo.setDescription(dto.getDescription());
        todo.setCompleted(Boolean.TRUE.equals(dto.getCompleted()));
        return todo;
    }

    public static TodoResponse toResponse(Todo todo) {
        return new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.isCompleted(),
                todo.getCreatedAt(),
                todo.getUpdatedAt()
        );
    }
}
