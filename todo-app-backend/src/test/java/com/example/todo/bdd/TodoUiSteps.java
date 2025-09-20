package com.example.todo.bdd;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TodoUiSteps {

    private WebDriver driver;
    private String addedText;

    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new", "--disable-gpu", "--window-size=1920,1200");
        driver = new ChromeDriver(options);

        // If your frontend is React on 3000, change the URL accordingly:
        // driver.get("http://localhost:3000");
        driver.get("http://localhost:8080");
    }

    @After
    public void teardown() {
        if (driver != null) driver.quit();
    }

    @Given("the user wants to add a todo")
    public void user_wants_to_add_todo() {
        // already navigated in setup
    }

    @When("the user creates a todo with title {string}")
    public void create_todo_with_title(String title) {
        driver.findElement(By.id("new-todo")).sendKeys(title);
        driver.findElement(By.id("add-btn")).click();
    }

    @Then("the todo should be visible in the list")
    public void todo_should_be_visible() {
        addedText = driver.findElement(By.xpath("//ul/li[last()]")).getText();
        assertTrue(addedText.length() > 0);
    }
}
