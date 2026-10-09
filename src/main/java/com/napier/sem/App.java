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
     * Displays all countries in a specified region,
     * ordered from largest population to smallest.
     *
     * @param region The region to search for.
     */
    public void getCountriesByRegion(String region)
    {
        String sql = "SELECT * FROM country WHERE Region = ? ORDER BY Population DESC";

        try (PreparedStatement stmt = con.prepareStatement(sql))
        {
            stmt.setString(1, region);
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                String code = rs.getString("Code");
                String continent = rs.getString("Continent");
                String name = rs.getString("Name");
                int population = rs.getInt("Population");
                String capital = rs.getString("Capital");
                if (capital == null)
                {
                    capital = "N/A";
                }
                String countryRegion = rs.getString("Region");
                System.out.println(code + " - " + name + " - " + continent + " - " + countryRegion + " - " + population + " - " + capital);
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error retrieving countries: " + e.getMessage());
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






}