
package com.napier.sem;

import java.sql.*;

public class App
{
    public static void main(String[] args)
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        // Connection to the database
        Connection con = null;
        int retries = 100;

        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");

            try
            {
                // Connect to the database

                con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:33060/world?allowPublicKeyRetrieval=true&useSSL=false",
                        "root",
                        "example"
                );


                System.out.println("Successfully connected");

                CountryReport report = new CountryReport();
                report.getCountries(con);

                // Exit for loop
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + i);
                System.out.println(sqle.getMessage());
            }

            if (i < retries - 1)
            {
                try
                {
                    Thread.sleep(2000);
                }
                catch (InterruptedException ie)
                {
                    Thread.currentThread().interrupt();
                    System.out.println("Database connection retry interrupted.");
                    break;
                }
            }
        }

        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (SQLException e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }
}
