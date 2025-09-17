package com.example.todo.bdd;

import com.example.todo.model.Todo;
import com.example.todo.repository.TodoRepository;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TodoStepDefinitions {

    @Autowired
    private TodoRepository todoRepository;

    private Response response;
    private Long savedTodoId;

    @Given("the system is running")
    public void the_system_is_running() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @When("I create a todo with title {string} and description {string}")
    public void i_create_a_todo(String title, String description) {
        String body = String.format("{\"title\":\"%s\", \"description\":\"%s\"}", title, description);
        response = given()
                .contentType("application/json")
                .body(body)
                .when()
                .post("/api/todos");
    }

    @Then("the response should have status {int}")
    public void the_response_should_have_status(Integer status) {
        response.then().statusCode(status);
    }

    @Then("the response should contain a todo with title {string}")
    public void the_response_should_contain_a_todo_with_title(String title) {
        String actualTitle = response.jsonPath().getString("title");
        assertThat(actualTitle, equalTo(title));
    }

    @Given("the system has a todo with title {string} and description {string}")
    public void the_system_has_a_todo(String title, String description) {
        Todo todo = new Todo(title, description);
        Todo saved = todoRepository.save(todo);
        savedTodoId = saved.getId();
    }

    @When("I retrieve the todo by its ID")
    public void i_retrieve_the_todo_by_its_id() {
        response = given()
                .when()
                .get("/api/todos/" + savedTodoId);
    }
}
