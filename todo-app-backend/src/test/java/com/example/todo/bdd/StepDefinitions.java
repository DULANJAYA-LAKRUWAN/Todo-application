package com.example.todo.bdd;

import com.example.todo.model.Todo;
import com.example.todo.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class StepDefinitions extends CucumberSpringConfig {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoService todoService;

    private ObjectMapper objectMapper = new ObjectMapper();
    private MvcResult lastResponse;

    private String title;
    private String description;

    @Given("a todo with title {string} and description {string}")
    public void a_todo_with_title_and_description(String title, String description) {
        this.title = title;
        this.description = description;
    }

    @When("the client creates the todo")
    public void the_client_creates_the_todo() throws Exception {
        // Mock service response when valid title provided
        if (title != null && !title.trim().isEmpty()) {
            Todo response = new Todo(title, description);
            response.setId(1L);
            when(todoService.createTodo(any(Todo.class))).thenReturn(response);
        } else {
            // For invalid title, controller validation will trigger 400 before service call
            Mockito.reset(todoService);
        }

        String body = objectMapper.writeValueAsString(new Todo(title, description));
        lastResponse = mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)).andReturn();
    }

    @Then("the response status should be {int}")
    public void the_response_status_should_be(Integer status) {
        assertThat(lastResponse.getResponse().getStatus()).isEqualTo(status);
    }
}