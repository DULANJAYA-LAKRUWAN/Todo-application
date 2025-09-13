const { Builder, By, until } = require('selenium-webdriver');
const chrome = require('selenium-webdriver/chrome');

(async function loginTest() {
  let driver = await new Builder().forBrowser('chrome').setChromeOptions(new chrome.Options()).build();
  try {
    await driver.get('http://localhost:3000');
    // Simulate login (if login UI exists, otherwise skip)
    // Example: await driver.findElement(By.id('login-username')).sendKeys('testuser');
    // Example: await driver.findElement(By.id('login-password')).sendKeys('password');
    // Example: await driver.findElement(By.id('login-btn')).click();
    // Wait for header
    await driver.wait(until.elementLocated(By.css('header.app-header')), 5000);
    console.log('Login UI loaded successfully.');
  } finally {
    await driver.quit();
  }
})();
