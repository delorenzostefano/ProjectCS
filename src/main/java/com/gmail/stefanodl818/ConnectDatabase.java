package com.gmail.stefanodl818;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

public class ConnectDatabase {

    private static final Logger logger = Logger.getLogger(ConnectDatabase.class.getName());

    public static void main(String[] args) throws FileNotFoundException {

        Scanner sc= new Scanner(System.in);
        System.out.print("Enter the directory of the file to read: ");
        String str= sc.nextLine();

        Map<String, Event> eventMap = Parser.parseFile(str);

        try {
            //Registering the HSQLDB JDBC driver
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            //Creating the connection with HSQLDB
            logger.info("Connecting the Database...");
            Connection con = DriverManager.getConnection("jdbc:hsqldb:file:.\\database/", "SA", "");
            Statement stmt = con.createStatement();

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS events( id VARCHAR(255) NOT NULL, duration INTEGER NOT NULL, host VARCHAR(255), type VARCHAR(255), alert BOOLEAN, PRIMARY KEY (id)); ");


            logger.info("Inserting the data in the database...");
            for (Event event : eventMap.values()) {

                PreparedStatement sta = con.prepareStatement("INSERT INTO events (id, duration, host, type, alert) VALUES (?, ?, ?, ?, ?)");
                sta.setString(1, event.getId());
                sta.setInt(2, event.getDuration());
                sta.setString(3, event.getHost());
                sta.setString(4, event.getHost());
                sta.setBoolean(5, event.getAlert());
                sta.execute();
            }

            logger.info("Data inserted into the database");

        }  catch (Exception e) {
            logger.warning("The creation of the database failed");
            e.printStackTrace(System.out);
        }

    }
}