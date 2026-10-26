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

        String continent = "Europe";
        //METHODS CALLS FOR SQL HERE:
        a.getCitiesInContinent(continent);

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
     * Displays the top N most populated countries in a specified region,
     * ordered from largest population to smallest.
     *
     * @param region The region to search for.
     * @param n The maximum number of countries to display.
     */
    public void getTopNCountriesByRegion(String region, int n)
    {
        if (n <= 0)
        {
            System.out.println("Please enter a number greater than 0.");
            return;
        }

        String sql = "SELECT * FROM country WHERE Region = ? ORDER BY Population DESC LIMIT ?";

    try (PreparedStatement stmt = con.prepareStatement(sql))
    {
        stmt.setString(1, region);
        stmt.setInt(2, n);
        ResultSet rs = stmt.executeQuery();
        while (rs.next())
        {
            String name = rs.getString("Name");
            int population = rs.getInt("Population");
            String code = rs.getString("Code");
            String continent = rs.getString("Continent");
            String capital = rs.getString("Capital");
            String countryRegion = rs.getString("Region");
            if (capital == null)
            {
                capital = "N/A";
            }

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

}