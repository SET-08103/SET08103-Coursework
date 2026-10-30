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
        String continent = "Europe";
        String region = "Caribbean";


        //METHODS CALLS FOR SQL HERE:
        a.getCountriesByContinent(continent);
        a.getTopPopulatedCountries(4);
        a.getCitiesInContinent(continent);
        a.getCitiesByRegion(region);

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
     *  Issue #2, All the countries in a continent organised by largest population to smallest
     */

    public void getCountriesByContinent(String continent)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT Name, Population "
                            + "FROM country "
                            + "WHERE Continent = '" + continent + "' "
                            + "ORDER BY population DESC ";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Display all countries in continent
            // Check one is returned
            while (rset.next())
            {
                System.out.println("Country: " + rset.getString("Name") + ", Population: " + rset.getInt("Population"));
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
        }

    }

    /**
     *  Issue #4, Top N populated countries in the world, ordered by population descending.
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

    //ISSUE #8
    //METHOD TO PRINT CITIES BY CONTINENT BY POPULATION IN DESCENDING ORDER
    public void getCitiesInContinent(String continent)
    {
        try
        {
            //CREATE AN SQL STATEMENT
            Statement stmt = con.createStatement();


            //STRING FOR THE SQL STATEMENT THAT GETS city AND population GIVEN THE continent IN descending ORDER
            String strSelect = "SELECT city.Name, city.Population " +
                    "FROM city " +
                    "JOIN country ON city.CountryCode = country.Code " +
                    "WHERE country.Continent ='" + continent + "' " +
                    "ORDER BY city.Population DESC";

            //EXECUTE SQL STATEMENT
            ResultSet rset = stmt.executeQuery(strSelect);

            //HEADER PRINT STATEMENT
            System.out.println("Cities in " + continent + " ordered by largest popluation size to smallest: ");

            //WHILE LOOP THAT GOES THROUGH EACH ROW OF THE RESULT SET
            while(rset.next())
            {
                String cityName = rset.getString("Name");
                int population = rset.getInt("Population");

                //PRINT CITY NAME AND POPULATION
                System.out.println(cityName + " - Population: " + population);
            }

            rset.close();
            stmt.close();
        }

        catch(Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get cities.");
        }
    }

    // ISSUE #9
    // METHOD TO PRINT ALL CITIES IN A REGION ORGANISED BY LARGEST POPULATION TO SMALLEST
    public void getCitiesByRegion(String region)
    {
        try
        {
            System.out.println("ISSUE #9 METHOD STARTED");

            // CREATE AN SQL STATEMENT
            Statement stmt = con.createStatement();

            // STRING FOR THE SQL STATEMENT THAT GETS CITY NAME AND POPULATION
            // GIVEN THE REGION, IN DESCENDING ORDER OF POPULATION
            String strSelect = "SELECT city.Name, city.Population " +
                    "FROM city " +
                    "JOIN country ON city.CountryCode = country.Code " +
                    "WHERE country.Region = '" + region + "' " +
                    "ORDER BY city.Population DESC";

            // EXECUTE SQL STATEMENT
            ResultSet rset = stmt.executeQuery(strSelect);

            // HEADER PRINT STATEMENT
            System.out.println("Cities in " + region +
                    " ordered by largest population size to smallest:");

            // WHILE LOOP THAT GOES THROUGH EACH ROW OF THE RESULT SET
            while (rset.next())
            {
                String cityName = rset.getString("Name");
                int population = rset.getInt("Population");

                // PRINT CITY NAME AND POPULATION
                System.out.println(cityName + " - Region Population: " + population);
            }

            rset.close();
            stmt.close();
        }

        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get cities.");
        }
    }
}




