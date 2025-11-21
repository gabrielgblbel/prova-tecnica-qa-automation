package com.qa.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends BasePage {
    
    // Locators
    private By welcomeMessage = By.id("welcomeMessage");
    private By userNameLabel = By.id("userName");
    private By userRoleLabel = By.id("userRole");
    private By logoutButton = By.id("logoutButton");
    private By adminPanel = By.id("adminPanel");
    private By userPanel = By.id("userPanel");
    private By dashboardTitle = By.id("dashboardTitle");
    private By loadingSpinner = By.id("loadingSpinner");
    
    public DashboardPage(WebDriver driver) {
        super(driver);
    }
    
    public String getWelcomeMessage() {
        waitForElementVisible(welcomeMessage);
        return getText(welcomeMessage);
    }
    
    public String getUserName() {
        return getText(userNameLabel);
    }
    
    public String getUserRole() {
        return getText(userRoleLabel);
    }
    
    public String getDashboardTitle() {
        return getText(dashboardTitle);
    }
    
    public boolean isWelcomeMessageDisplayed() {
        return isElementDisplayed(welcomeMessage);
    }
    
    public boolean isLogoutButtonDisplayed() {
        return isElementDisplayed(logoutButton);
    }
    
    public boolean isAdminPanelDisplayed() {
        try {
            // Wait for the page to fully load and JavaScript to execute
            Thread.sleep(2000);
            
            // Check if element exists
            boolean exists = driver.findElements(adminPanel).size() > 0;
            logger.info("Admin panel exists: {}", exists);
            
            if (exists) {
                String display = driver.findElement(adminPanel).getCssValue("display");
                logger.info("Admin panel display style: {}", display);
                return !"none".equals(display);
            }
            return false;
        } catch (Exception e) {
            logger.error("Error checking admin panel: {}", e.getMessage());
            return false;
        }
    }
    
    public boolean isUserPanelDisplayed() {
        return isElementDisplayed(userPanel);
    }
    
    public boolean isLoadingSpinnerDisplayed() {
        return isElementDisplayed(loadingSpinner);
    }
    
    public void clickLogout() {
        click(logoutButton);
        logger.info("Clicou no botão de logout");
    }
    
    public void waitForDashboardLoad() {
        waitForElementVisible(dashboardTitle);
        logger.info("Dashboard carregado completamente");
    }
    
    public void waitForLoadingSpinnerDisappear() {
        waitForElementInvisible(loadingSpinner);
        logger.info("Loading spinner desapareceu");
    }
    
    public void waitForWelcomeMessage() {
        waitForElementVisible(welcomeMessage);
    }
    
    public boolean verifyAdminAccess() {
        waitForDashboardLoad();
        // Wait a bit for JavaScript to update the admin panel visibility
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            logger.warn("Sleep interrupted", e);
        }
        return isAdminPanelDisplayed();
    }
    
    public boolean verifyUserAccess() {
        waitForDashboardLoad();
        return isUserPanelDisplayed() && !isAdminPanelDisplayed();
    }
}