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
        return isElementDisplayed(adminPanel);
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
        return isAdminPanelDisplayed();
    }
    
    public boolean verifyUserAccess() {
        waitForDashboardLoad();
        return isUserPanelDisplayed() && !isAdminPanelDisplayed();
    }
}