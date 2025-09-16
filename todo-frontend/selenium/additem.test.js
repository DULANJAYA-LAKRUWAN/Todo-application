//todo-frontend\selenium\additem.test.js
const { Builder, By, until } = require('selenium-webdriver');
const chrome = require('selenium-webdriver/chrome');

(async function addItemTest() {
  let driver = await new Builder().forBrowser('chrome').setChromeOptions(new chrome.Options()).build();
  try {
    await driver.get('http://localhost:3000');
    await driver.wait(until.elementLocated(By.css('.todo-form')), 5000);
    await driver.findElement(By.id('title')).sendKeys('Selenium Test Todo');
    await driver.findElement(By.id('description')).sendKeys('Created by Selenium');
    await driver.findElement(By.css('.todo-form button[type="submit"]')).click();
    await driver.wait(until.elementLocated(By.xpath("//h3[text()='Selenium Test Todo']")), 5000);
    console.log('Todo item added successfully.');
  } finally {
    await driver.quit();
  }
})();
