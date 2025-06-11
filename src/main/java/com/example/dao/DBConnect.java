package com.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    protected static Connection conn = null;

    public DBConnect() {
        try {
            String url = "jdbc:oracle:thin:@//0.tcp.ap.ngrok.io:16932/ORCL";
            String user = "QLSV";
            String pass = "abc123";
            Class.forName("oracle.jdbc.OracleDriver");
            conn = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Oracle JDBC Driver not found.", e);
        } catch (SQLException e) {
            throw new RuntimeException("Connection failed.", e);
        }
    }

    public static Connection getConnection() {
        if (conn == null) {
            new DBConnect();
        }
        return conn;
    }
}
