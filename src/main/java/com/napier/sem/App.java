package com.napier.sem;

import java.sql.*;

public class App
{
    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();

        //EXAMPLE STRINGS FOR METHOD CALLS

        //METHODS CALLS FOR SQL HERE:
        a.getTopPopulatedCountries(4);

        // Disconnect from database
        a.disconnect();
    }

    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect()
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

        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/world?allowPublicKeyRetrieval=true&useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }   

    //ISSUE #4
    /**
     *  Issue 4, Top N populated countries in the world where N is provided by the user
     */
    public void getTopPopulatedCountries(int n)
    {
        try
        {
            //CREATE AN SQL STATEMENT
            Statement stmt = con.createStatement();


            String strSelect = "SELECT name, population " +
                    "FROM country " +
                    "ORDER BY Population DESC " +
                    "LIMIT " + n;


            //EXECUTE SQL STATEMENT
            ResultSet rset = stmt.executeQuery(strSelect);

            //HEADER PRINT STATEMENT
            System.out.println("Top " + n + " populated countries");

            //WHILE LOOP THAT GOES THROUGH EACH ROW OF THE RESULT SET
            while(rset.next())
            {
                String name = rset.getString("Name");
                int population = rset.getInt("Population");

                //PRINT CITY NAME AND POPULATION
                System.out.println("Country: " + name + ", population: " + population);
            }

        }

        catch(Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countries and population.");
        }
    }



}