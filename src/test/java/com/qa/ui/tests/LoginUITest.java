package com.qa.ui.tests;

import com.qa.config.Configuration;
import com.qa.ui.pages.DashboardPage;
import com.qa.ui.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginUITest extends BaseTest {
    
    @Test(priority = 1, description = "Teste de login com credenciais válidas - Admin")
    public void testLoginWithValidAdminCredentials() {
        logger.info("Iniciando teste: Login com Admin válido");
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToLoginPage();
        
        resetUserLoginAttempts(Configuration.getAdminUsername());
        
        loginPage.login(Configuration.getAdminUsername(), Configuration.getAdminPassword());
        
        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.waitForDashboardLoad();
        
        Assert.assertTrue(dashboardPage.isWelcomeMessageDisplayed(), 
            "Mensagem de boas-vindas não exibida");
        Assert.assertTrue(dashboardPage.verifyAdminAccess(), 
            "Painel de admin não acessível");
        
        logger.info("Teste concluído: Login Admin bem-sucedido");
    }
    
    @Test(priority = 2, description = "Teste de login com usuário regular")
    public void testLoginWithValidRegularUserCredentials() {
        logger.info("Iniciando teste: Login com usuário regular válido");
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToLoginPage();
        
        resetUserLoginAttempts(Configuration.getRegularUserUsername());
        
        loginPage.login(Configuration.getRegularUserUsername(), Configuration.getRegularUserPassword());
        
        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.waitForDashboardLoad();
        
        Assert.assertTrue(dashboardPage.isWelcomeMessageDisplayed(), 
            "Mensagem de boas-vindas não exibida");
        
        logger.info("Teste concluído: Login usuário regular bem-sucedido");
    }
}