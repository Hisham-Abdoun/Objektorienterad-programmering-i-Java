package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {


    private static final String URL =
            "jdbc:mysql://localhost:3306/webbshop?" +
                    "useSSL=false&" +
                    "serverTimezone=UTC&" +
                    "allowPublicKeyRetrieval=true";

    private static final String USER = "dbUserPPtest";
    private static final String PASSWORD = "secretPassword";


    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver hittades inte!", e);
        }
    }


    public static void main(String[] args) {
        try {
            Connection conn = getConnection();
            System.out.println("✓ Anslutningen fungerar!");
            conn.close();
        } catch (SQLException e) {
            System.err.println("✗ Anslutningen misslyckades: " + e.getMessage());
            e.printStackTrace();
        }
    }
}