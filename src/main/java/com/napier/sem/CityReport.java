package com.napier.sem;
import java.sql.*;

public class CityReport {
    // Gets all cities in thw world ordered by population
    public void getCities(Connection connection) throws SQLException {

        //SQL query which gets all cities in world, ordered by population
        String query = "SELECT city.name, country.name AS Country, city.District, city.Population " +
                "FROM city " +
                "JOIN country ON city.CountryCode = country.code " +
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
                            results.getString("District") + " | " +
                            results.getInt("Population")
            );


        }
    }
}
