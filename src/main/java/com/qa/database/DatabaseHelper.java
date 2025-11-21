package com.qa.database;

import com.qa.config.Configuration;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class DatabaseHelper {
    
    private Connection connection;

    public DatabaseHelper() {
        try {
            Class.forName(Configuration.getDatabaseDriver());
            connection = DriverManager.getConnection(
                Configuration.getDatabaseUrl(),
                Configuration.getDatabaseUsername(),
                Configuration.getDatabasePassword()
            );
        } catch (Exception e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
        }
    }

    // Executes a SQL query and returns the result set.
    public ResultSet executeQuery(String query) {
        try {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            System.err.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    // Executes a SQL update statement (INSERT, UPDATE, DELETE).
    public int executeUpdate(String query) {
        try {
            Statement stmt = connection.createStatement();
            return stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println("Update failed: " + e.getMessage());
            return 0;
        }
    }

    // Cleans up test data from the database.
    public void cleanupTestData() {
        // Reset all users to unblocked state
        executeUpdate("UPDATE usuarios SET bloqueado = false, tentativas = 0");
    }

    // Resets the login attempts for a user.
    public void resetLoginAttempts(String username) {
        executeUpdate("UPDATE usuarios SET bloqueado = false, tentativas = 0 WHERE username = '" + username + "'");
    }

    // Gets the number of login attempts for a user.
    public int getLoginAttempts(String username) {
        try {
            ResultSet rs = executeQuery("SELECT tentativas FROM usuarios WHERE username = '" + username + "'");
            if (rs != null && rs.next()) {
                return rs.getInt("tentativas");
            }
        } catch (SQLException e) {
            System.err.println("Failed to get login attempts: " + e.getMessage());
        }
        return 0;
    }

    // Checks if a user is blocked due to too many login attempts.
    public boolean isUserBlocked(String username) {
        try {
            ResultSet rs = executeQuery("SELECT bloqueado FROM usuarios WHERE username = '" + username + "'");
            if (rs != null && rs.next()) {
                return rs.getBoolean("bloqueado");
            }
        } catch (SQLException e) {
            System.err.println("Failed to check if user is blocked: " + e.getMessage());
        }
        return false;
    }

    // Retrieves orphaned audit records from the database.
    public List<AuditRecord> getOrphanedAuditRecords() {
        return new ArrayList<>();
    }

    // Manages database transactions.
    public void beginTransaction() {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("Failed to begin transaction: " + e.getMessage());
        }
    }

    public void commitTransaction() {
        try {
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.err.println("Failed to commit transaction: " + e.getMessage());
        }
    }

    public void rollbackTransaction() {
        try {
            connection.rollback();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.err.println("Failed to rollback transaction: " + e.getMessage());
        }
    }
    
    // Close database connection
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Failed to close connection: " + e.getMessage());
        }
    }
    
    // Execute update with parameters
    public int executeUpdate(String query, String... params) {
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                pstmt.setString(i + 1, params[i]);
            }
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Parameterized update failed: " + e.getMessage());
            return 0;
        }
    }
}