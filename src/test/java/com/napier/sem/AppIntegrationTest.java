package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppIntegrationTest
{
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
        app.connect("localhost:33060", 30000);

    }

    @Test
    void testGetCountriesByContinent()
    {
        app.getCountriesByContinent("Europe");
    }

    @Test
    void testGetTopPopulatedCountries()
    {
        app.getTopPopulatedCountries(2);
    }

    @Test
    void testGetCitiesInContinent()
    {
        app.getCitiesInContinent("Europe" , 6);
    }

    /**
     * Issue #5: runs the top N countries in a continent report against the real database.
     */
    @Test
    void testPrintTopNCountriesInContinent()
    {
        app.printTopNCountriesInContinent("Europe", 5);
    }

    /**
     * Issue #16: runs the top N cities in a district report against the real database.
     */
    @Test
    void testPrintTopNCitiesInDistrict()
    {
        app.printTopNCitiesInDistrict("Scotland", 5);
    }

    /**
     * Issue #24: runs the population report for each region against the real database.
     */
    @Test
    void testPrintRegionPopulationReport()
    {
        app.printRegionPopulationReport();
    }

    /**
     * ISSUE #13 Gets top N cities in a continent from user input
     */
    @Test
    void testgetTopNCitiesInContinent() {app.getTopNCitiesInContinent("Europe", 5);}

    /**
     * ISSUE #12 Gets top N cities in the world, n from user input
     *
     */
    @Test
    void testgetTopNPopulatedCitiesInWorld() {app.getTopNPopulatedCitiesInWorld(5);}
}