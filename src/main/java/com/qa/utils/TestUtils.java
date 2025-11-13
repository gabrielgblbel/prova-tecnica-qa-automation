package com.qa.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestUtils {
    
    private static final Logger logger = LoggerFactory.getLogger(TestUtils.class);
    private static final String SCREENSHOTS_DIR = "target/screenshots/";
    
    public static void waitForElementVisible(WebDriver driver, By locator, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            logger.debug("Elemento visível: {}", locator);
        } catch (TimeoutException e) {
            logger.error("Timeout esperando elemento ficar visível: {}", locator);
            throw e;
        }
    }
    
    public static void waitForElementClickable(WebDriver driver, By locator, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            logger.debug("Elemento clicável: {}", locator);
        } catch (TimeoutException e) {
            logger.error("Timeout esperando elemento ficar clicável: {}", locator);
            throw e;
        }
    }
    
    public static void waitForElementInvisible(WebDriver driver, By locator, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
            logger.debug("Elemento invisível: {}", locator);
        } catch (TimeoutException e) {
            logger.error("Timeout esperando elemento ficar invisível: {}", locator);
            throw e;
        }
    }
    
    public static String takeScreenshot(WebDriver driver, String testName) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = testName + "_" + timestamp + ".png";
        String filePath = SCREENSHOTS_DIR + fileName;
        
        try {
            File screenshotDir = new File(SCREENSHOTS_DIR);
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }
            
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
            File destFile = new File(filePath);
            Files.copy(srcFile.toPath(), destFile.toPath());
            
            logger.info("Screenshot salvo: {}", filePath);
            return filePath;
        } catch (IOException e) {
            logger.error("Erro ao salvar screenshot", e);
            return null;
        }
    }
    
    public static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Sleep interrompido", e);
        }
    }
    
    public static boolean isElementPresent(WebDriver driver, By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    
    public static String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}