package com.codecool.bruteforce.users.repository;

import com.codecool.bruteforce.logger.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrackedUsers {
    private final String dbFile;
    private final Logger logger;

    public CrackedUsers(String dbFile, Logger logger) {
        this.dbFile = dbFile;
        this.logger = logger;
    }

    private Connection getConnection() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:" + dbFile;
            conn = DriverManager.getConnection(url);

            logger.logInfo("Connection to SQLite has been established.");

            return conn;

        } catch (SQLException e) {
            logger.logError(e.getMessage());
        }

        return null;
    }
    public void deleteAll() {
        String sql = "DELETE FROM cracked_users";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
            logger.logInfo("All records have been successfully deleted.");

        } catch (SQLException e) {
            logger.logError("Error deleting database records: " + e.getMessage());
        }
    }

    public void add(long timestamp, String password) {
        String sql = "INSERT INTO cracked_users(duration,password) VALUES(?,?)";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, timestamp);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
