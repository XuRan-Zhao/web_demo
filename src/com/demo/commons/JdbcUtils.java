package com.demo.commons;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class JdbcUtils {

    private static String driver;
    private static String url;
    private static String username;
    private static String password;
    static {
        try {
        ResourceBundle bundle = ResourceBundle.getBundle("db");
        driver = bundle.getString("jdbc.driver");
        url = bundle.getString("jdbc.url");
        username = bundle.getString("jdbc.username");
        password = bundle.getString("jdbc.password");
        Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    //获取连接
    public static Connection getConnection() throws SQLException {
        //获取数据库连接
        Connection conn = null;
        conn = DriverManager.getConnection(url, username, password);
        return conn;
    }
    public static void closeConnection(Connection conn) throws SQLException {
        conn.close();
    }

    public static void main(String[] args) throws SQLException {
        Connection connection = getConnection();
        System.out.println(connection);
    }
}
