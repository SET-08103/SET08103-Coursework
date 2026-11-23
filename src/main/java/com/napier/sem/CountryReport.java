
package com.napier.sem;

import java.sql.*;

public class CountryReport {
    // Gets all countries in world, ordered by population
    public void getCountries(Connection connection) throws SQLException {

        //SQL query which gets countries in world, ordered by popul;ation
        String query = "SELECT Code, Name, Continent, Region, Population, Capital " +
                "FROM country " +
                "ORDER BY Population DESC;";

        // Create a statement that executes the query
        Statement statement = connection.createStatement();
        // Execute the query and store returned data in a result set
        ResultSet results = statement.executeQuery(query);

        // Go through each row returned by the query
        while (results.next()) {

            // Displays the information
            System.out.println(
                    results.getString("Code") + " | " +
                            results.getString("Name") + " | " +
                            results.getString("Continent") + " | " +
                            results.getString("Region") + " | " +
                            results.getInt("Population") + " | " +
                            results.getInt("Capital")
            );
        }
    }
}
