package com.qa.database.tests;

import com.qa.config.Configuration;
import com.qa.database.DatabaseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class DatabaseValidationTest {
    
    private static final Logger logger = LoggerFactory.getLogger(DatabaseValidationTest.class);
    private DatabaseHelper dbHelper;
    
    @BeforeMethod
    public void setUp() {
        dbHelper = new DatabaseHelper();
        logger.info("Configuração Database Test iniciada");
    }
    
    @AfterMethod
    public void tearDown() {
        if (dbHelper != null) {
            dbHelper.cleanupTestData();
            dbHelper.close();
        }
        logger.info("Database Test finalizado");
    }
    
    @Test(priority = 1, description = "Teste de bloqueio de usuário")
    public void testUserBlocking() {
        logger.info("Iniciando teste: Bloqueio de usuário");
        
        String username = Configuration.getRegularUserUsername();
        dbHelper.resetLoginAttempts(username);
        
        dbHelper.executeUpdate(
            "UPDATE usuarios SET tentativas = 5, bloqueado = true WHERE username = ?", 
            username
        );
        
        boolean isBlocked = dbHelper.isUserBlocked(username);
        Assert.assertTrue(isBlocked, "Usuário deveria estar bloqueado");
        
        logger.info("Teste concluído: Bloqueio OK");
    }
}