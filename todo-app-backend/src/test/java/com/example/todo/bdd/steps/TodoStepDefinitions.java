// File: src/test/java/com/example/todo/bdd/steps/TodoStepDefinitions.java
package com.example.todo.bdd.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TodoStepDefinitions {

    @LocalServerPort
    private int port;

    private Response response;

    @Given("the todo API is available")
    public void the_todo_api_is_available() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @When("I request all todos")
    public void i_request_all_todos() {
        response = RestAssured.get("/api/todos");
    }

    @Then("I should get a list of todos")
    public void i_should_get_a_list_of_todos() {
        response.then().statusCode(200);
        assertThat(response.jsonPath().getList("$"), is(not(empty())));
    }
}
