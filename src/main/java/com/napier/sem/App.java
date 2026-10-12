package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

public class App
{
    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();

        // Report: top N populated capital cities in a region
        ArrayList<CapitalCity> capitals = a.getTopNCapitalCitiesInRegion("Caribbean", 5);
        a.printCapitalCities(capitals);

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
     * Gets the top N most populated capital cities in a region.
     *
     * @param region the region to search, e.g. "Caribbean"
     * @param n      the number of capital cities to return
     * @return a list of capital cities ordered by population (largest first),
     *         or null if an error occurs
     */
    public ArrayList<CapitalCity> getTopNCapitalCitiesInRegion(String region, int n)
    {
        // Check the inputs are valid before querying the database
        if (region == null || n < 1)
        {
            System.out.println("Please provide a region and a value of N greater than 0.");
            return null;
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
            ArrayList<CapitalCity> capitals = new ArrayList<>();

            // Turn each row of results into a CapitalCity object
            while (rset.next())
            {
                CapitalCity capital = new CapitalCity();
                capital.name = rset.getString("Name");
                capital.country = rset.getString("Country");
                capital.population = rset.getInt("Population");
                capitals.add(capital);
            }
            return capitals;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get top N capital cities in region");
            return null;
        }
    }

    /**
     * Prints a Capital City Report with the columns Name, Country and Population.
     *
     * @param capitals the list of capital cities to print
     */
    public void printCapitalCities(ArrayList<CapitalCity> capitals)
    {
        // Handle the case where the query failed or returned nothing
        if (capitals == null)
        {
            System.out.println("No capital cities to display.");
            return;
        }

        // Print the column headings
        System.out.println(String.format("%-35s %-40s %15s", "Name", "Country", "Population"));

        // Print one line per capital city
        for (CapitalCity capital : capitals)
        {
            if (capital == null)
            {
                continue;
            }
            System.out.println(String.format("%-35s %-40s %,15d",
                    capital.name, capital.country, capital.population));
        }
    }
}