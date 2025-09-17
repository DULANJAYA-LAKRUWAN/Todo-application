package com.example.todo.api;

import com.example.todo.model.Todo;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TodoApiTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    void getTodos_returns200() {
        given()
        .when()
            .get("/api/todos")
        .then()
            .statusCode(200);
    }

    @Test
    void createTodo_returns201() {
        Todo todo = new Todo();
        todo.setTitle("API Test Todo");
        todo.setDescription("Demo via RestAssured");

        given()
            .contentType("application/json")
            .body(todo)
        .when()
            .post("/api/todos")
        .then()
            .statusCode(201)
            .body("title", equalTo("API Test Todo"));
    }
}
