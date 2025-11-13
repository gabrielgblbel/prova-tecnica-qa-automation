package com.qa.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    
    // Locators
    private By usernameField = By.id("username");
    private By passwordField = By.id("password");
    private By loginButton = By.id("loginButton");
    private By errorMessage = By.id("errorMessage");
    private By successMessage = By.id("successMessage");
    private By usernameError = By.id("usernameError");
    private By passwordError = By.id("passwordError");
    private By lockoutMessage = By.id("lockoutMessage");
    
    public LoginPage(WebDriver driver) {
        super(driver);
    }
    
    public void navigateToLoginPage() {
        navigateTo(com.qa.config.Configuration.getAppBaseUrl() + "/login");
        logger.info("Navegou para página de login");
    }
    
    public void enterUsername(String username) {
        sendKeys(usernameField, username);
        logger.info("Inseriu username: {}", username);
    }
    
    public void enterPassword(String password) {
        sendKeys(passwordField, password);
        logger.info("Inseriu password");
    }
    
    public void clickLoginButton() {
        click(loginButton);
        logger.info("Clicou no botão de login");
    }
    
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        logger.info("Executou login com username: {}", username);
    }
    
    public String getErrorMessage() {
        return getText(errorMessage);
    }
    
    public String getSuccessMessage() {
        return getText(successMessage);
    }
    
    public String getUsernameError() {
        return getText(usernameError);
    }
    
    public String getPasswordError() {
        return getText(passwordError);
    }
    
    public String getLockoutMessage() {
        return getText(lockoutMessage);
    }
    
    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(errorMessage);
    }
    
    public boolean isSuccessMessageDisplayed() {
        return isElementDisplayed(successMessage);
    }
    
    public boolean isUsernameErrorDisplayed() {
        return isElementDisplayed(usernameError);
    }
    
    public boolean isPasswordErrorDisplayed() {
        return isElementDisplayed(passwordError);
    }
    
    public boolean isLockoutMessageDisplayed() {
        return isElementDisplayed(lockoutMessage);
    }
    
    public boolean isLoginButtonEnabled() {
        return isElementEnabled(loginButton);
    }
    
    public void waitForErrorMessage() {
        waitForElementVisible(errorMessage);
    }
    
    public void waitForSuccessMessage() {
        waitForElementVisible(successMessage);
    }
    
    public void clearFields() {
        driver.findElement(usernameField).clear();
        driver.findElement(passwordField).clear();
        logger.info("Limpou campos de login");
    }
}