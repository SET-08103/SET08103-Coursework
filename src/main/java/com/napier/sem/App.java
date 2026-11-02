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
        int n = 5;
        String continent = "Europe";
        String region = "Caribbean";


        //METHODS CALLS FOR SQL HERE:
        a.getCountriesByContinent(continent);
        a.getTopPopulatedCountries(4);
        a.getCitiesInContinent(continent, n);
        a.getCitiesByRegion(region, n);

        // Report: top N populated countries in a continent (issue #5)
        a.printTopNCountriesInContinent(continent, 5);

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

    /**
     *  Issue #2, All the countries in a continent organised by largest population to smallest
     */

    public void getCountriesByContinent(String continent)
    {
        if(continent == null || continent.isBlank())
        {
            System.out.println("Invalid continent");
            return;
        }

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
        if(n <= 0)
        {
            System.out.println("Invalid number of countries");
            return;
        }


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

    /**
     *     ISSUE #8
     *     METHOD TO PRINT CITIES BY CONTINENT BY POPULATION IN DESCENDING ORDER
     */

    public void getCitiesInContinent(String continent, int n)
    {

        try
        {
            // Check the inputs are valid before querying the database
            if (continent == null || n < 1)
            {
                System.out.println("Please provide a continent and a value of N greater than 0.");
                return;
            }

            //CREATE AN SQL STATEMENT
            Statement stmt = con.createStatement();


            //STRING FOR THE SQL STATEMENT THAT GETS city AND population GIVEN THE continent IN descending ORDER
            String strSelect = "SELECT city.Name, city.Population " +
                    "FROM city " +
                    "JOIN country ON city.CountryCode = country.Code " +
                    "WHERE country.Continent ='" + continent + "' " +
                    "ORDER BY city.Population DESC " +
                    "LIMIT " + n;


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

    /**
     *      ISSUE #9
     *      METHOD TO PRINT ALL CITIES IN A REGION ORGANISED BY LARGEST POPULATION TO SMALLEST
     */

    public void getCitiesByRegion(String region, int n)
    {
        try
        {
            // Check the inputs are valid before querying the database
            if (region == null || n < 1)
            {
                System.out.println("Please provide a region and a value of N greater than 0.");
                return;
            }

            System.out.println("ISSUE #9 METHOD STARTED");

            // CREATE AN SQL STATEMENT
            Statement stmt = con.createStatement();

            // STRING FOR THE SQL STATEMENT THAT GETS CITY NAME AND POPULATION
            // GIVEN THE REGION, IN DESCENDING ORDER OF POPULATION
            String strSelect = "SELECT city.Name, city.Population " +
                    "FROM city " +
                    "JOIN country ON city.CountryCode = country.Code " +
                    "WHERE country.Region = '" + region + "' " +
                    "ORDER BY city.Population DESC " +
                    "LIMIT " + n;

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

    /**
     * Issue #5: Prints a Country Report of the top N most populated countries in a continent.
     * Columns: Code, Name, Continent, Region, Population and Capital.
     *
     * @param continent the continent to search, e.g. "Europe"
     * @param n         the number of countries to show
     */
    public void printTopNCountriesInContinent(String continent, int n)
    {
        // Check the inputs are valid before querying the database
        if (continent == null || n < 1)
        {
            System.out.println("Please provide a continent and a value of N greater than 0.");
            return;
        }

        // SQL query: look up each country's capital name, filter by continent,
        // sort largest first, and keep the top N
        String sql =
                "SELECT country.Code, country.Name, country.Continent, country.Region, "
                        + "country.Population, city.Name AS Capital "
                        + "FROM country "
                        + "LEFT JOIN city ON country.Capital = city.ID "
                        + "WHERE country.Continent = ? "
                        + "ORDER BY country.Population DESC "
                        + "LIMIT ?";

        try (PreparedStatement stmt = con.prepareStatement(sql))
        {
            // Fill in the ? placeholders with the continent and N
            stmt.setString(1, continent);
            stmt.setInt(2, n);

            ResultSet rset = stmt.executeQuery();

            // Print the column headings
            System.out.println("Top " + n + " populated countries in " + continent + ":");
            System.out.println(String.format("%-5s %-45s %-15s %-27s %15s %-30s",
                    "Code", "Name", "Continent", "Region", "Population", "Capital"));

            // Print each row straight from the results
            while (rset.next())
            {
                // Some countries have no capital, so show a dash instead of "null"
                String capital = rset.getString("Capital");
                if (capital == null)
                {
                    capital = "-";
                }

                System.out.println(String.format("%-5s %-45s %-15s %-27s %,15d %-30s",
                        rset.getString("Code"),
                        rset.getString("Name"),
                        rset.getString("Continent"),
                        rset.getString("Region"),
                        rset.getInt("Population"),
                        capital));
            }
            System.out.println();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get top N countries in continent");
        }
    }
}