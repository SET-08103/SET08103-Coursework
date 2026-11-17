
package com.napier.sem;

import java.sql.*;

public class App
{
    public static void main(String[] args)
    {
        try
        {
            //  This loads Database driver so Java can communicate with MYAQL
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            // Displays error and stops the program if it cannot find the driver.
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        // Connection to the database and sets maximum number of database connection attempts
        Connection con = null;
        int retries = 100;

        // Repeat connection attempt until successfull or it has been retried 100 times.
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");

            try
            {
                // Connect to the database using its address, username and password

                con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:33060/world?allowPublicKeyRetrieval=true&useSSL=false",
                        "root",
                        "example"
                );


                System.out.println("Successfully connected");
                // Query 1: All Countries by Population
                // CountryReport report = new CountryReport();
                // report.getCountries(con);

                // Query 7: All cities by population
                 //   CityReport report = new CityReport();
                 //   report.getCities(con);

                // Query 10: All cities in a country by population
              //  CitiesInCountryReport report = new CitiesInCountryReport();
              //  report.getCitiesInCountry(con);

                // Query 17: All Capital cities by population
                //  CapitalCityReport report = new CapitalCityReport();
                //  report.getCapitalCities(con);

                // Query 18: All capital cities in a continent by population
                CapitalCitiesInContinentReport report = new CapitalCitiesInContinentReport();
                report.getCapitalCitiesInContinent(con);

            // Stop the retry loop as all the connection succeeded
                break;
            }
            catch (SQLException sqle)

                    //Display failed attempt number and database error
            {
                System.out.println("Failed to connect to database attempt " + i);
                System.out.println(sqle.getMessage());
            }
                // Wait two seconds and then retry again
            if (i < retries - 1)
            {
                try
                {
                    Thread.sleep(2000);
                }
                catch (InterruptedException ie)
                {
                    // This restores the interrupted status and stop retrying
                    Thread.currentThread().interrupt();
                    System.out.println("Database connection retry interrupted.");
                    break;
                }
            }
        }
        // Close the database connection once the report is finished
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (SQLException e)
            {
                // Display error if connection cant be closed
                System.out.println("Error closing connection to database");
            }
        }
    }
}
