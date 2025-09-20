package com.example.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createTodo_validRequest_returns201() throws Exception {
        String json = "{\"title\":\"Test Todo\",\"description\":\"Demo task\"}";

        mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Todo"));
    }

    @Test
    void createTodo_blankTitle_returns400() throws Exception {
        String json = "{\"title\":\"\",\"description\":\"invalid\"}";

        mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").exists());
    }

    @Test
    void updateTodo_existing_returns200() throws Exception {
        // create first
        String createJson = "{\"title\":\"Update Todo\",\"description\":\"Before update\"}";
        String response = mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        // extract id using ObjectMapper
        com.fasterxml.jackson.databind.JsonNode node = objectMapper.readTree(response);
        Long id = node.get("id").asLong();

        String updateJson = "{\"title\":\"Update Todo\",\"description\":\"Updated description\",\"completed\":true}";

        mockMvc.perform(put("/api/todos/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Updated description"))
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    void updateTodo_notFound_returns404() throws Exception {
        String updateJson = "{\"title\":\"Non-existent\",\"description\":\"Nothing\"}";

        mockMvc.perform(put("/api/todos/{id}", 9999)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateJson))
                .andExpect(status().isNotFound());
    }
}