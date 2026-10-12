
package com.napier.sem;

import java.sql.*;

public class CountryReport {

    public void getCountries(Connection connection) throws SQLException {

        String query = "SELECT Code, Name, Continent, Region, Population, Capital " +
                "FROM country " +
                "ORDER BY Population DESC;";

        Statement statement = connection.createStatement();
        ResultSet results = statement.executeQuery(query);

        while (results.next()) {
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
