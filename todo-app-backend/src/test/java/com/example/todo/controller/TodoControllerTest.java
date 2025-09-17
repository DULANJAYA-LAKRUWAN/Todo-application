package com.example.todo.controller;

import com.example.todo.model.Todo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void createTodo_validRequest_returns201() throws Exception {
        Todo todo = new Todo();
        todo.setTitle("Test Todo");
        todo.setDescription("Demo task");
        
        mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(todo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Todo"));
    }

    @Test
    void updateTodo_existing_returns200() throws Exception {
        Todo todo = new Todo();
        todo.setTitle("Update Todo");
        todo.setDescription("Before update");

        String response = mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(todo)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Todo created = objectMapper.readValue(response, Todo.class);
        created.setDescription("Updated description");

        mockMvc.perform(put("/api/todos/{id}", created.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(created)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Updated description"));
    }

    @Test
    void updateTodo_notFound_returns404() throws Exception {
        Todo todo = new Todo();
        todo.setTitle("Non-existent");
        todo.setDescription("Nothing");

        mockMvc.perform(put("/api/todos/{id}", 9999)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(todo)))
                .andExpect(status().isNotFound());
    }
}
