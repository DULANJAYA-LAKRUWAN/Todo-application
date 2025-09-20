package com.example.todo.api;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TodoStepDefinitions {

    @LocalServerPort
    private int port;
    
    private Response response;

    @Given("the system is running")
    public void the_system_is_running() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
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
}