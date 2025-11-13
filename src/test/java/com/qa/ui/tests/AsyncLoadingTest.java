package com.qa.ui.tests;

import com.qa.ui.pages.DashboardPage;
import com.qa.ui.pages.LoginPage;
import com.qa.config.Configuration;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AsyncLoadingTest extends BaseTest {

    @Test(priority = 1, description = "Teste de carregamento assíncrono")
    public void testAsyncDashboardLoading() {
        logger.info("Iniciando teste: Carregamento assíncrono");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToLoginPage();
        resetUserLoginAttempts(Configuration.getAdminUsername());
        loginPage.login(Configuration.getAdminUsername(), Configuration.getAdminPassword());
        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.waitForLoadingSpinnerDisappear();
        dashboardPage.waitForDashboardLoad();
        Assert.assertTrue(dashboardPage.isWelcomeMessageDisplayed(), "Mensagem não carregada");
        logger.info("Teste concluído: Carregamento OK");
    }
}