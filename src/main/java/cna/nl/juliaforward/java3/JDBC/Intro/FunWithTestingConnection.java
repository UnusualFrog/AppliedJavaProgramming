package cna.nl.juliaforward.java3.JDBC.Intro;

import cna.nl.juliaforward.java3.JDBC.MariaDBProperties;
import org.w3c.dom.ls.LSOutput;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Simple class to test a connection
 */
public class FunWithTestingConnection {

    public static void main(String[] args) {
        try{
            //Test the connection with a single complete URL
            Connection conn = DriverManager.getConnection(MariaDBProperties.DATABASE_URL_COMPLETE);
            System.out.println("Connection Successful!");
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
