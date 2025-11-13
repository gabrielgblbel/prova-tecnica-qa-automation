package com.qa.api.tests;

import com.qa.config.Configuration;
import com.qa.database.DatabaseHelper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class LoginApiTest {
    
    private static final Logger logger = LoggerFactory.getLogger(LoginApiTest.class);
    private DatabaseHelper dbHelper;
    
    @BeforeMethod
    public void setUp() {
        RestAssured.baseURI = Configuration.getApiBaseUrl();
        dbHelper = new DatabaseHelper();
        logger.info("Configuração API Test iniciada");
    }
    
    @AfterMethod
    public void tearDown() {
        if (dbHelper != null) {
            dbHelper.cleanupTestData();
            dbHelper.close();
        }
        logger.info("API Test finalizado");
    }
    
    @Test(priority = 1, description = "Teste API de login com credenciais válidas")
    public void testApiLoginWithValidCredentials() {
        logger.info("Iniciando teste: API Login válido");
        
        String requestBody = String.format(
            "{\"username\":\"%s\", \"password\":\"%s\"}", 
            Configuration.getAdminUsername(),
            Configuration.getAdminPassword()
        );
        
        Response response = given()
            .header("Content-Type", "application/json")
            .body(requestBody)
            .when()
            .post("/api/login")
            .then()
            .extract().response();
        
        Assert.assertEquals(response.getStatusCode(), 200, 
            "Status code deveria ser 200");
        Assert.assertNotNull(response.jsonPath().getString("token"), 
            "Token não retornado");
        
        logger.info("Teste concluído: API Login bem-sucedido");
    }
    
    @Test(priority = 2, description = "Teste API de login com credenciais inválidas")
    public void testApiLoginWithInvalidCredentials() {
        logger.info("Iniciando teste: API Login inválido");
        
        String requestBody = String.format(
            "{\"username\":\"%s\", \"password\":\"%s\"}", 
            Configuration.getInvalidUsername(),
            Configuration.getInvalidPassword()
        );
        
        Response response = given()
            .header("Content-Type", "application/json")
            .body(requestBody)
            .when()
            .post("/api/login")
            .then()
            .extract().response();
        
        Assert.assertEquals(response.getStatusCode(), 401, 
            "Status code deveria ser 401");
        
        logger.info("Teste concluído: API retornou erro corretamente");
    }
}