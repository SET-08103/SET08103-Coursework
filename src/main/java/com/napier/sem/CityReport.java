package com.napier.sem;
import java.sql.*;

public class CityReport {
    public void getCities(Connection connection) throws SQLException {

        String query = "SELECT city.name, country.name AS Country, city.District, city.Population " +
                "FROM city " +
                "JOIN country ON city.CountryCode = country.code " +
                "ORDER BY city.Population DESC;";


        Statement statement = connection.createStatement();
        ResultSet results = statement.executeQuery(query);

        while (results.next()) {
            System.out.println(
                    results.getString("Name") + " | " +
                            results.getString("Country") + " | " +
                            results.getString("District") + " | " +
                            results.getInt("Population")
            );


        }
    }
}
