package com.example.todo.ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TodoUiTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://localhost:8080");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    void addTodo_createsNewItem() {
        driver.findElement(By.id("new-todo")).sendKeys("UI Test Todo");
        driver.findElement(By.id("add-btn")).click();

        String added = driver.findElement(By.xpath("//ul/li[last()]")).getText();
        assertTrue(added.contains("UI Test Todo"));
    }

    @Test
    void markTodo_completedOnDoubleClick() {
        driver.findElement(By.id("new-todo")).sendKeys("DoubleClick Todo");
        driver.findElement(By.id("add-btn")).click();

        By lastTodo = By.xpath("//ul/li[last()]");
        driver.findElement(lastTodo).click();
        driver.findElement(lastTodo).click();

        String cssClass = driver.findElement(lastTodo).getAttribute("class");
        assertTrue(cssClass.contains("completed"));
    }
}
