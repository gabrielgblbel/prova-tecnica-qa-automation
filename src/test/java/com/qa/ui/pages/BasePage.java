package com.qa.ui.pages;

import com.qa.config.Configuration;
import com.qa.utils.TestUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class BasePage {
    
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static final Logger logger = LoggerFactory.getLogger(BasePage.class);
    
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Configuration.getExplicitWait()));
        PageFactory.initElements(driver, this);
    }
    
    protected void click(By locator) {
        try {
            waitForElementClickable(locator);
            driver.findElement(locator).click();
            logger.debug("Clicou no elemento: {}", locator);
        } catch (Exception e) {
            logger.error("Erro ao clicar no elemento: {}", locator, e);
            throw e;
        }
    }
    
    protected void sendKeys(By locator, String text) {
        try {
            waitForElementVisible(locator);
            WebElement element = driver.findElement(locator);
            element.clear();
            element.sendKeys(text);
            logger.debug("Digitou '{}' no elemento: {}", text, locator);
        } catch (Exception e) {
            logger.error("Erro ao digitar no elemento: {}", locator, e);
            throw e;
        }
    }
    
    protected String getText(By locator) {
        try {
            waitForElementVisible(locator);
            String text = driver.findElement(locator).getText();
            logger.debug("Obteve texto '{}' do elemento: {}", text, locator);
            return text;
        } catch (Exception e) {
            logger.error("Erro ao obter texto do elemento: {}", locator, e);
            throw e;
        }
    }
    
    protected boolean isElementDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    protected boolean isElementEnabled(By locator) {
        try {
            return driver.findElement(locator).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }
    
    protected void waitForElementVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    protected void waitForElementClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    protected void waitForElementInvisible(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    
    protected void waitForTextPresent(By locator, String text) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }
    
    protected String getPageTitle() {
        return driver.getTitle();
    }
    
    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
    protected void navigateTo(String url) {
        driver.get(url);
        logger.info("Navegou para: {}", url);
    }
    
    protected void takeScreenshot(String testName) {
        TestUtils.takeScreenshot(driver, testName);
    }
}