package com.qa.ui.tests;

import com.qa.config.Configuration;
import com.qa.database.DatabaseHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {
    
    protected WebDriver driver;
    protected DatabaseHelper dbHelper;
    protected static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    
    @BeforeMethod
    public void setUp() {
        logger.info("Iniciando configuração do teste");
        
        // Inicializar banco de dados
        dbHelper = new DatabaseHelper();
        
        // Configurar WebDriver
        String browser = Configuration.getBrowser();
        driver = initializeDriver(browser);
        
        // Configurar timeouts
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Configuration.getImplicitWait()));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Configuration.getPageLoadTimeout()));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(Configuration.getScriptTimeout()));
        
        // Maximizar janela se configurado
        if (Configuration.shouldMaximize()) {
            driver.manage().window().maximize();
        }
        
        logger.info("Configuração do teste concluída");
    }
    
    @AfterMethod
    public void tearDown() {
        logger.info("Finalizando teste");
        
        // Limpar dados de teste
        if (dbHelper != null) {
            dbHelper.cleanupTestData();
            dbHelper.close();
        }
        
        // Fechar navegador
        if (driver != null) {
            driver.quit();
        }
        
        logger.info("Teste finalizado");
    }
    
    private WebDriver initializeDriver(String browser) {
        WebDriver webDriver;
        
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (Configuration.isHeadless()) {
                    chromeOptions.addArguments("--headless");
                }
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                webDriver = new ChromeDriver(chromeOptions);
                logger.info("Chrome WebDriver inicializado");
                break;
                
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (Configuration.isHeadless()) {
                    firefoxOptions.addArguments("--headless");
                }
                webDriver = new FirefoxDriver(firefoxOptions);
                logger.info("Firefox WebDriver inicializado");
                break;
                
            default:
                throw new IllegalArgumentException("Browser não suportado: " + browser);
        }
        
        return webDriver;
    }
    
    protected void resetUserLoginAttempts(String username) {
        dbHelper.resetLoginAttempts(username);
        logger.info("Tentativas de login resetadas para: {}", username);
    }
}