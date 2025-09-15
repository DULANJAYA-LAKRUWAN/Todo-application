package com.example.todo.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class TodoApiTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    // ======= 1. Test POST /api/todos =======
    @Test
    public void createTodo_validRequest_returns201() {
        String requestBody = """
            {
                "title": "REST Assured Task",
                "description": "Test API automation"
            }
        """;

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
        .when()
            .post("/api/todos")
        .then()
            .statusCode(201)
            .body("title", equalTo("REST Assured Task"))
            .body("description", equalTo("Test API automation"))
            .body("completed", equalTo(false));
    }

    // ======= 2. Test GET /api/todos =======
    @Test
    public void getAllTodos_returns200() {
        when()
            .get("/api/todos")
        .then()
            .statusCode(200)
            .body("$", not(empty())); // At least one todo exists
    }

    // ======= 3. Negative Test: POST blank title =======
    @Test
    public void createTodo_blankTitle_returns400() {
        String requestBody = """
            {
                "title": "",
                "description": "Invalid todo"
            }
        """;

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
        .when()
            .post("/api/todos")
        .then()
            .statusCode(400);
    }
}
