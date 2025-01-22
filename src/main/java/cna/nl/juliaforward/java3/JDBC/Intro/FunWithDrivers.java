package cna.nl.juliaforward.java3.JDBC.Intro;

import cna.nl.juliaforward.java3.JDBC.MariaDBProperties;

import java.sql.*;

/**
 * Registering Drivers. This is a good way to make sure the JDBC driver that your project needs is in scope.
 * If you use a database manager class then do this in a private method and make sure to communicate failure
 * to the user of the manager correctly.
 *
 * @author Josh
 */
public class FunWithDrivers {

    public static void main(String[] args) {
        System.out.println("Fun trying to registers a driver");

        if(MariaDBProperties.isDriverRegistered(System.err)) {
            System.out.println("Success!");
        } else {
            System.out.println("Fail!");
        }

    }

}
