package com.napier.sem;
import java.sql.*;

public class CitiesInCountryReport {
    // Gets all cities in a selected country, ordered by population.
    public void getCitiesInCountry(Connection connection) throws SQLException {

        //SQL query which gets city names, districts and populations
       // for cities belonging to the United Kingdom.
        String query = "SELECT city.name, city.district, city.population  " +
                "FROM city " +
                "JOIN country ON city.CountryCode = country.code " +
                "WHERE country.name = 'United Kingdom' " +
                "ORDER BY city.population DESC;";


        // Create a statement that executes the query
        Statement statement = connection.createStatement();
        // Execute the query and store returned data in a result set
        ResultSet results = statement.executeQuery(query);

        // Go through each row returned by the query
        while (results.next()) {

            // Displays the information
            System.out.println(
                    results.getString("Name") + " | " +
                            results.getString("District") + " | " +
                            results.getInt("Population")
            );


        }
    }
}
