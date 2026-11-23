package com.napier.sem;
import java.sql.*;

public class CapitalCityReport {

    //Gets capital cities ordered by their population
    public void getCapitalCities(Connection connection) throws SQLException {

        //SQL query which gets capital city, country and its population
        String query = "SELECT city.name, country.name AS Country, city.population " +
                "FROM country " +
                "JOIN city ON country.Capital = city.ID " +
                "ORDER BY city.Population DESC;";

        // Create a statement that executes the query
        Statement statement = connection.createStatement();
        // Execute the query and store returned data in a result set
        ResultSet results = statement.executeQuery(query);

        // Go through each row returned by the query
        while (results.next()) {

            // Displays the information
            System.out.println(
                    results.getString("Name") + " | " +
                            results.getString("Country") + " | " +
                            results.getInt("Population")
            );


        }
    }

    public void getCapitalCitiesInRegion(Connection connection, String region) throws SQLException
    {
        String query = "SELECT city.Name, city.Population " +
                "FROM country " +
                "JOIN city ON country.Capital = city.ID " +
                "WHERE country.Region = ? " +
                "ORDER BY city.Population DESC";

        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1, region);

            try (ResultSet results = statement.executeQuery())
            {
                System.out.println("Capital cities in " + region + ":");

                while (results.next())
                {
                    String cityName = results.getString("Name");
                    int population = results.getInt("Population");

                    System.out.println(cityName + " - Population: " + population);
                }
            }
        }
    }
}
