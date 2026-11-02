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

        // Report: top N populated capital cities in a region
        a.printTopNCapitalCitiesInRegion("Caribbean", 5);

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

    /**
     * Prints a Capital City Report of the top N most populated capital cities in a region.
     * Columns: Name, Country and Population.
     *
     * @param region the region to search, e.g. "Caribbean"
     * @param n      the number of capital cities to show
     */
    public void printTopNCapitalCitiesInRegion(String region, int n)
    {
        // Check the inputs are valid before querying the database
        if (region == null || n < 1)
        {
            System.out.println("Please provide a region and a value of N greater than 0.");
            return;
        }

        // SQL query: join cities to countries on the capital ID,
        // filter by region, sort largest first, and keep the top N
        String sql =
                "SELECT city.Name, country.Name AS Country, city.Population "
                        + "FROM city "
                        + "JOIN country ON city.ID = country.Capital "
                        + "WHERE country.Region = ? "
                        + "ORDER BY city.Population DESC "
                        + "LIMIT ?";

        try (PreparedStatement stmt = con.prepareStatement(sql))
        {
            // Fill in the ? placeholders with the region and N
            stmt.setString(1, region);
            stmt.setInt(2, n);

            ResultSet rset = stmt.executeQuery();

            // Print the column headings
            System.out.println("Top " + n + " populated capital cities in " + region + ":");
            System.out.println(String.format("%-35s %-40s %15s", "Name", "Country", "Population"));

            // Print each row straight from the results
            while (rset.next())
            {
                System.out.println(String.format("%-35s %-40s %,15d",
                        rset.getString("Name"),
                        rset.getString("Country"),
                        rset.getInt("Population")));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get top N capital cities in region");
        }
    }
}