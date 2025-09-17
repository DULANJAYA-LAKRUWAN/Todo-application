package com.example.todo.service;

import com.example.todo.model.Todo;
import com.example.todo.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTodo_savesEntity() {
        Todo toSave = new Todo("Title", "Desc");
        when(todoRepository.save(any(Todo.class))).thenAnswer(inv -> inv.getArgument(0));

        Todo created = todoService.createTodo(toSave);

        verify(todoRepository, times(1)).save(any(Todo.class));
        assertThat(created.getTitle()).isEqualTo("Title");
        assertThat(created.getDescription()).isEqualTo("Desc");
        assertThat(created.isCompleted()).isFalse();
    }

    @Test
    void updateTodo_updatesFieldsAndUpdatedAt_whenExistingId() {
        Todo existing = new Todo("Old", "OldDesc");
        existing.setId(1L);
        LocalDateTime previousUpdatedAt = existing.getUpdatedAt();

        when(todoRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(todoRepository.save(any(Todo.class))).thenAnswer(inv -> inv.getArgument(0));

        Todo patch = new Todo("New", "NewDesc");
        patch.setCompleted(true);

        Todo updated = todoService.updateTodo(1L, patch);

        assertThat(updated).isNotNull();
        assertThat(updated.getTitle()).isEqualTo("New");
        assertThat(updated.getDescription()).isEqualTo("NewDesc");
        assertThat(updated.isCompleted()).isTrue();
        assertThat(updated.getUpdatedAt()).isAfter(previousUpdatedAt);

        ArgumentCaptor<Todo> captor = ArgumentCaptor.forClass(Todo.class);
        verify(todoRepository).save(captor.capture());
        assertThat(captor.getValue().getId()).isEqualTo(1L);
    }

    @Test
    void updateTodo_returnsNull_whenIdNotFound() {
        when(todoRepository.findById(99L)).thenReturn(Optional.empty());

        Todo patch = new Todo("Any", "Any");
        Todo updated = todoService.updateTodo(99L, patch);

        assertThat(updated).isNull();
        verify(todoRepository, never()).save(any());
    }

    @Test
void getAllTodos_returnsAllTodos() {
    Todo t1 = new Todo("Task1", "Desc1");
    Todo t2 = new Todo("Task2", "Desc2");
    when(todoRepository.findAll()).thenReturn(Arrays.asList(t1, t2));

    List<Todo> todos = todoService.getAllTodos();

    assertThat(todos).hasSize(2);
    assertThat(todos.get(0).getTitle()).isEqualTo("Task1");
    assertThat(todos.get(1).getTitle()).isEqualTo("Task2");
    verify(todoRepository, times(1)).findAll();
}

}