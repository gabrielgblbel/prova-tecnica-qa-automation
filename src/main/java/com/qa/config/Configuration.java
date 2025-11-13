package com.qa.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration {
    
    private static Properties properties;
    private static final String CONFIG_FILE_PATH = "src/test/resources/config.properties";
    
    static {
        loadProperties();
    }
    
    private static void loadProperties() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar arquivo de configuração: " + CONFIG_FILE_PATH, e);
        }
    }
    
    public static String getDatabaseUrl() {
        return properties.getProperty("db.url");
    }
    
    public static String getDatabaseUsername() {
        return properties.getProperty("db.username");
    }
    
    public static String getDatabasePassword() {
        return properties.getProperty("db.password");
    }
    
    public static String getDatabaseDriver() {
        return properties.getProperty("db.driver");
    }
    
    public static String getAppBaseUrl() {
        return properties.getProperty("app.base.url");
    }
    
    public static String getApiBaseUrl() {
        return properties.getProperty("api.base.url");
    }
    
    public static String getBrowser() {
        return properties.getProperty("browser", "chrome");
    }
    
    public static boolean isHeadless() {
        return Boolean.parseBoolean(properties.getProperty("headless", "false"));
    }
    
    public static boolean shouldMaximize() {
        return Boolean.parseBoolean(properties.getProperty("maximize", "true"));
    }
    
    public static int getImplicitWait() {
        return Integer.parseInt(properties.getProperty("implicit.wait", "10"));
    }
    
    public static int getExplicitWait() {
        return Integer.parseInt(properties.getProperty("explicit.wait", "20"));
    }
    
    public static int getPageLoadTimeout() {
        return Integer.parseInt(properties.getProperty("page.load.timeout", "30"));
    }
    
    public static int getScriptTimeout() {
        return Integer.parseInt(properties.getProperty("script.timeout", "30"));
    }
    
    public static String getAdminUsername() {
        return properties.getProperty("user.admin.username");
    }
    
    public static String getAdminPassword() {
        return properties.getProperty("user.admin.password");
    }
    
    public static String getRegularUserUsername() {
        return properties.getProperty("user.regular.username");
    }
    
    public static String getRegularUserPassword() {
        return properties.getProperty("user.regular.password");
    }
    
    public static String getVisitorUsername() {
        return properties.getProperty("user.visitor.username");
    }
    
    public static String getVisitorPassword() {
        return properties.getProperty("user.visitor.password");
    }
    
    public static String getBlockedUsername() {
        return properties.getProperty("user.blocked.username");
    }
    
    public static String getBlockedPassword() {
        return properties.getProperty("user.blocked.password");
    }
    
    public static String getInvalidUsername() {
        return properties.getProperty("user.invalid.username");
    }
    
    public static String getInvalidPassword() {
        return properties.getProperty("user.invalid.password");
    }
    
    public static String getApiContentType() {
        return properties.getProperty("api.content.type", "application/json");
    }
    
    public static int getApiTimeout() {
        return Integer.parseInt(properties.getProperty("api.timeout", "10000"));
    }
    
    public static int getRetryCount() {
        return Integer.parseInt(properties.getProperty("retry.count", "3"));
    }
    
    public static int getRetryDelay() {
        return Integer.parseInt(properties.getProperty("retry.delay", "1000"));
    }
}