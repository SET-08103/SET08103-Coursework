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

    /**
     * Issue #2, All the countries in a continent organised by largest population to smallest
     */
    @Test
    void testGetCountriesByContinent()
    {
        app.getCountriesByContinent("Europe");
    }

    /**
     * Issue #4, Top N populated countries in the world, ordered by population descending.
     */
    @Test
    void testGetTopPopulatedCountries()
    {
        app.getTopPopulatedCountries(2);
    }

    /**
     * ISSUE #8 METHOD TO PRINT CITIES BY CONTINENT BY POPULATION IN DESCENDING ORDER
     */
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

    /**
     *  ISSUE #14, gets top populated cities in a region
     *
     */
    @Test
    void testTopPopulatedCitiesInRegion()
    {
        // Add Intergration test for issue 14
        app.getTopPopulatedCitiesInRegion("Caribbean", 3);
    }

    /**
     *  Issue #20, Top N populated capital cities where N is provided.
     *
     */
    @Test
    void testGetTopPopulatedCapitalCites()
    {
        app.getTopPopulatedCapitalCites(5);
    }

    /**
     * Issue #21, Top N populated capital cities in continent where N is provided.
     *
     */
    @Test
    void testgetTopPopulatedCapitalCitiesInContinent()
    {
        // Add intergration tests for issue 21
        app.getTopPopulatedCapitalCitiesInContinent("Europe", 2);
    }
}