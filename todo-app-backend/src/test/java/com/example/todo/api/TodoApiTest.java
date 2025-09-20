package com.example.todo.api;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TodoApiTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
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
        String json = "{\"title\":\"API Test Todo\",\"description\":\"Demo via RestAssured\"}";

        given()
            .contentType("application/json")
            .body(json)
        .when()
            .post("/api/todos")
        .then()
            .statusCode(201)
            .body("title", equalTo("API Test Todo"));
    }
}