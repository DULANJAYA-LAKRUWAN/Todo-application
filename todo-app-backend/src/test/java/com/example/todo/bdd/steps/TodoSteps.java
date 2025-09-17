package com.example.todo.bdd.steps;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TodoSteps {

    private WebDriver driver;

    @Given("the user wants to add a todo")
    public void user_wants_to_add_todo() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://localhost:8080");
    }

    @When("the user creates a todo with title {string}")
    public void create_todo_with_title(String title) {
        driver.findElement(By.id("new-todo")).sendKeys(title);
        driver.findElement(By.id("add-btn")).click();
    }

    @Then("the todo should be visible in the list")
    public void todo_should_be_visible() {
        String added = driver.findElement(By.xpath("//ul/li[last()]")).getText();
        assertTrue(added.contains("BDD Todo"));
        driver.quit();
    }
}
