package cna.nl.juliaforward.java3.JDBC.Intro;

import cna.nl.juliaforward.java3.JDBC.MariaDBProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Simple example that creates a Database called "STUDENTS".
 */
public class FunWithCreatingDBs {

    public static void main(String[] args) {
        // Open a connection
        try(Connection conn = DriverManager.getConnection(
                MariaDBProperties.DATABASE_URL, MariaDBProperties.DATABASE_USER, MariaDBProperties.DATABASE_PASSWORD);
            Statement stmt = conn.createStatement();
        ) {
            String sql = "CREATE DATABASE Test";
            stmt.executeUpdate(sql);
            System.out.println("Database created successfully...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
