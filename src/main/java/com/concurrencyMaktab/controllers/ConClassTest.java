package com.concurrencyMaktab.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConClassTest {

    private static ConClassTest conClass;
    private Connection connection;

    private ConClassTest() throws SQLException{
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.
                    getConnection("jdbc:postgresql://localhost:5432/twitter_test",
                            "intellij", "intellij");
        }
        catch (SQLException e){
            System.out.println("SQL Exception");
        }
        catch (ClassNotFoundException e){
            System.out.println("Class not found Exception");
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static ConClassTest getInstance() {
        try {
            if (conClass == null) {
                conClass = new ConClassTest();
            } else if (conClass.getConnection().isClosed()) {
                conClass = new ConClassTest();
            }
            return conClass;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return conClass;
    }

}
