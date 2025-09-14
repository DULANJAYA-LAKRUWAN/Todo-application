//todo-app-backend\src\test\java\com\example\todo\controller\TodoControllerTest.java
package com.example.todo.controller;

import com.example.todo.model.Todo;
import com.example.todo.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.when;

@WebMvcTest(TodoController.class)
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void createTodo_validRequest_returns201() throws Exception {
        Todo request = new Todo("New Task", "Do it");
        Todo response = new Todo("New Task", "Do it");
        response.setId(1L);

        when(todoService.createTodo(any(Todo.class))).thenReturn(response);

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("New Task"));
    }

    @Test
    void createTodo_blankTitle_returns400() throws Exception {
        // Title blank should trigger @NotBlank validation and return 400
        String body = "{\"title\":\"\",\"description\":\"x\"}";

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    // GET all todos
@Test
void getAllTodos_returnsList() throws Exception {
    Todo t1 = new Todo("Task1", "Desc1");
    t1.setId(1L);
    Todo t2 = new Todo("Task2", "Desc2");
    t2.setId(2L);

    when(todoService.getAllTodos()).thenReturn(Arrays.asList(t1, t2));

    mockMvc.perform(get("/api/todos")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].title").value("Task1"))
            .andExpect(jsonPath("$[1].title").value("Task2"));
}

// GET by ID
@Test
void getTodoById_existing_returns200() throws Exception {
    Todo todo = new Todo("Task1", "Desc1");
    todo.setId(1L);

    when(todoService.getTodoById(1L)).thenReturn(todo);

    mockMvc.perform(get("/api/todos/1")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.title").value("Task1"));
}

@Test
void getTodoById_notFound_returns404() throws Exception {
    when(todoService.getTodoById(99L)).thenReturn(null);

    mockMvc.perform(get("/api/todos/99")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
}

// PUT update
@Test
void updateTodo_existing_returns200() throws Exception {
    Todo update = new Todo("Updated", "NewDesc");
    update.setCompleted(true);
    update.setId(1L);

    when(todoService.updateTodo(any(Long.class), any(Todo.class))).thenReturn(update);

    mockMvc.perform(put("/api/todos/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(update)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Updated"))
            .andExpect(jsonPath("$.completed").value(true));
}

@Test
void updateTodo_notFound_returns404() throws Exception {
    Todo update = new Todo("Updated", "NewDesc");

    when(todoService.updateTodo(any(Long.class), any(Todo.class))).thenReturn(null);

    mockMvc.perform(put("/api/todos/99")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(update)))
            .andExpect(status().isNotFound());
}

// DELETE
@Test
void deleteTodo_existing_returns204() throws Exception {
    when(todoService.deleteTodo(1L)).thenReturn(true);

    mockMvc.perform(delete("/api/todos/1")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
}

@Test
void deleteTodo_notFound_returns404() throws Exception {
    when(todoService.deleteTodo(99L)).thenReturn(false);

    mockMvc.perform(delete("/api/todos/99")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
}

@Test
void getAllTodos_returnsList() throws Exception {
    Todo t1 = new Todo("Task1", "Desc1");
    t1.setId(1L);
    Todo t2 = new Todo("Task2", "Desc2");
    t2.setId(2L);

    when(todoService.getAllTodos()).thenReturn(Arrays.asList(t1, t2));

    mockMvc.perform(get("/api/todos")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].title").value("Task1"))
            .andExpect(jsonPath("$[1].title").value("Task2"));
}



}