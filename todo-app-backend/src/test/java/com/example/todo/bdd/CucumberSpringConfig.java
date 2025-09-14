package com.example.todo.bdd;

import com.example.todo.controller.TodoController;
import com.example.todo.service.TodoService;
import io.cucumber.spring.CucumberContextConfiguration;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@CucumberContextConfiguration
@WebMvcTest(TodoController.class)
@Import(CucumberSpringConfig.TestConfig.class)
public class CucumberSpringConfig {

    @Configuration
    static class TestConfig {
        @Bean
        public TodoService todoService() {
            return Mockito.mock(TodoService.class);
        }
    }
}