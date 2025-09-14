// src/test/java/com/example/todo/bdd/StepDefinitions.java
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

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class StepDefinitions extends CucumberSpringConfig {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoService todoService;

    private ObjectMapper objectMapper = new ObjectMapper();
    private MvcResult lastResponse;
    private String title;
    private String description;
    private Long todoId;

    // ======= CREATE TODO =======
    @Given("a todo with title {string} and description {string}")
    public void a_todo_with_title_and_description(String title, String description) {
        this.title = title;
        this.description = description;
    }

    @When("the client creates the todo")
    public void the_client_creates_the_todo() throws Exception {
        if (title != null && !title.trim().isEmpty()) {
            Todo response = new Todo(title, description);
            response.setId(1L);
            when(todoService.createTodo(any(Todo.class))).thenReturn(response);
        } else {
            Mockito.reset(todoService);
        }

        String body = objectMapper.writeValueAsString(new Todo(title, description));
        lastResponse = mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andReturn();
        if (lastResponse.getResponse().getStatus() == 201) {
            Todo created = objectMapper.readValue(lastResponse.getResponse().getContentAsString(), Todo.class);
            todoId = created.getId();
        }
    }

    @Then("the response status should be {int}")
    public void the_response_status_should_be(Integer status) {
        assertThat(lastResponse.getResponse().getStatus()).isEqualTo(status);
    }

    // ======= GET ALL TODOS =======
    @When("the client requests all todos")
    public void the_client_requests_all_todos() throws Exception {
        List<Todo> todos = Arrays.asList(
                new Todo("Task1", "Desc1"),
                new Todo("Task2", "Desc2")
        );
        when(todoService.getAllTodos()).thenReturn(todos);

        lastResponse = mockMvc.perform(get("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    // ======= GET TODO BY ID =======
    @When("the client requests the todo with id {long}")
    public void the_client_requests_the_todo_with_id(Long id) throws Exception {
        Todo todo = new Todo("Sample", "SampleDesc");
        todo.setId(id);
        when(todoService.getTodoById(id)).thenReturn(todo);

        lastResponse = mockMvc.perform(get("/api/todos/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    // ======= UPDATE TODO =======
    @Given("an updated todo with title {string} and description {string} and completed {string}")
    public void an_updated_todo_with_title_description_completed(String title, String description, String completed) {
        this.title = title;
        this.description = description;
        this.completed = Boolean.parseBoolean(completed);
    }

    private boolean completed;

    @When("the client updates the todo with id {long}")
    public void the_client_updates_the_todo_with_id(Long id) throws Exception {
        Todo existing = new Todo("Old", "OldDesc");
        existing.setId(id);

        Todo updated = new Todo(title, description);
        updated.setId(id);
        updated.setCompleted(completed);

        when(todoService.updateTodo(id, any(Todo.class))).thenReturn(updated);

        String body = objectMapper.writeValueAsString(updated);

        lastResponse = mockMvc.perform(put("/api/todos/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andReturn();
    }

    // ======= DELETE TODO =======
    @When("the client deletes the todo with id {long}")
    public void the_client_deletes_the_todo_with_id(Long id) throws Exception {
        when(todoService.deleteTodo(id)).thenReturn(true);

        lastResponse = mockMvc.perform(delete("/api/todos/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Then("the response should contain todo title {string}")
    public void the_response_should_contain_todo_title(String expectedTitle) throws Exception {
        String content = lastResponse.getResponse().getContentAsString();
        Todo todo = objectMapper.readValue(content, Todo.class);
        assertThat(todo.getTitle()).isEqualTo(expectedTitle);
    }
}
