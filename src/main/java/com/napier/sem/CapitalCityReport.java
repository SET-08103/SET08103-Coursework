package com.napier.sem;
import java.sql.*;

public class CapitalCityReport {
    public void getCapitalCities(Connection connection) throws SQLException {

        String query = "SELECT city.name, country.name AS Country, city.population " +
                "FROM country " +
                "JOIN city ON country.Capital = city.ID " +
                "ORDER BY city.Population DESC;";


        Statement statement = connection.createStatement();
        ResultSet results = statement.executeQuery(query);

        while (results.next()) {
            System.out.println(
                    results.getString("Name") + " | " +
                            results.getString("Country") + " | " +
                            results.getInt("Population")
            );


        }
    }
}
