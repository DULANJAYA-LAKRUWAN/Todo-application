// /todo-app-backend\src\test\java\com\example\todo\ui\TodoUiTest.java
package com.example.todo.ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TodoUiTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/index.html");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    // ====== 1. Add Todo ======
    @Test
    void addTodo_createsNewItem() {
        driver.findElement(By.id("title")).sendKeys("Buy milk");
        driver.findElement(By.id("description")).sendKeys("2 liters");
        driver.findElement(By.id("submit")).click();

        WebElement todoItem = driver.findElement(By.xpath("//ul[@id='todo-list']/li"));
        assertTrue(todoItem.getText().contains("Buy milk"));
        assertEquals("false", todoItem.getAttribute("data-completed"));
    }

    // ====== 2. Mark Todo Completed ======
    @Test
    void markTodo_completedOnDoubleClick() throws InterruptedException {
        // Add todo first
        driver.findElement(By.id("title")).sendKeys("Clean room");
        driver.findElement(By.id("description")).sendKeys("Bedroom");
        driver.findElement(By.id("submit")).click();

        WebElement todoItem = driver.findElement(By.xpath("//ul[@id='todo-list']/li"));

        // Double click to mark completed
        todoItem.click();
        todoItem.click(); // simulate double click
        Thread.sleep(500); // wait for UI update

        assertEquals("true", todoItem.getAttribute("data-completed"));
        assertTrue(todoItem.getCssValue("text-decoration").contains("line-through"));
    }
}
