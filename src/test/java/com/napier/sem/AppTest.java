package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest
{
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
    }

    /**
     *  TESTING THAT UNIT TESTS ARE WORKING AS INTENDED (TEST FOR TEST)
     */
    @Test
    void unitTest3()
    {
        assertEquals(5, 5, "Messages are equal");
    }

    /** ISSUE #8
     * TESTING INVALID INPUTS ARE HANDLED (continent = null, N = 0)
     */
    @Test
    void unitTestCitiesInContinent() {app.getCitiesInContinent(null, 0); }

    /** ISSUE #9
    * TESTING INVALID INPUTS ARE HANDLED (region = Null, N = 0)
     */
    @Test
    void unitTestCitiesInRegion() {app.getCitiesByRegion(null, 0); }

    /**
    *  ISSUE #13
     * TESTING INVALID INPUTS ARE HANDLED (continent = Null, N = 0)
     */
    @Test
    void unitTestTopCitiesByContinent() {app.getTopNCitiesInContinent(null, 0); }

    /** ISSUE #2
     * TESTING INVALID INPUTS ARE HANDLED CORRECTLY
     */
    @Test
    void testCountriesByContinent()
    {
        app.getCountriesByContinent(null);
    }

    /** ISSUE #4
     * TESTING INVALID INPUTS ARE HANDLED CORRECTLY
     */
    @Test
    void testTopPopulatedCountriesZero()
    {
        app.getTopPopulatedCountries(0);
    }

    /** ISSUE #12
     * TESTING INVALID INPUTS ARE HANDLED (N = 0)
     */
    @Test
    void unitTestgetTopNPopulatedCitiesInWorld() {app.getTopNPopulatedCitiesInWorld(0);}

    // ---------- Issue #5: top N countries in a continent ----------

    /**
     * A null continent should be rejected without throwing an exception.
     */
    @Test
    void testTopNCountriesInContinentNullContinent()
    {
        assertDoesNotThrow(() -> app.printTopNCountriesInContinent(null, 5));
    }

    /**
     * N of zero should be rejected without throwing an exception.
     */
    @Test
    void testTopNCountriesInContinentZeroN()
    {
        assertDoesNotThrow(() -> app.printTopNCountriesInContinent("Europe", 0));
    }

    /**
     * A negative N should be rejected without throwing an exception.
     */
    @Test
    void testTopNCountriesInContinentNegativeN()
    {
        assertDoesNotThrow(() -> app.printTopNCountriesInContinent("Europe", -1));
    }

    /**
     * Valid inputs with no database connection should be handled gracefully.
     */
    @Test
    void testTopNCountriesInContinentNoConnection()
    {
        assertDoesNotThrow(() -> app.printTopNCountriesInContinent("Europe", 5));
    }

    // ---------- Issue #16: top N cities in a district ----------

    /**
     * A null district should be rejected without throwing an exception.
     */
    @Test
    void testTopNCitiesInDistrictNullDistrict()
    {
        assertDoesNotThrow(() -> app.printTopNCitiesInDistrict(null, 5));
    }

    /**
     * A blank district should be rejected without throwing an exception.
     */
    @Test
    void testTopNCitiesInDistrictBlankDistrict()
    {
        assertDoesNotThrow(() -> app.printTopNCitiesInDistrict("   ", 5));
    }

    /**
     * N of zero should be rejected without throwing an exception.
     */
    @Test
    void testTopNCitiesInDistrictZeroN()
    {
        assertDoesNotThrow(() -> app.printTopNCitiesInDistrict("Scotland", 0));
    }

    /**
     * Valid inputs with no database connection should be handled gracefully.
     */
    @Test
    void testTopNCitiesInDistrictNoConnection()
    {
        assertDoesNotThrow(() -> app.printTopNCitiesInDistrict("Scotland", 5));
    }

    // ---------- Issue #24: population in and out of cities by region ----------

    /**
     * The region population report should be handled gracefully with no database connection.
     */
    @Test
    void testRegionPopulationReportNoConnection()
    {
        assertDoesNotThrow(() -> app.printRegionPopulationReport());
    }

    /**
     *  Issue 14.
     *  Checks that invalid regions and values of N are handled.
     */
    @Test
    void TestTopPopulatedCitiesInRegion()
    {
        app.getTopPopulatedCitiesInRegion(null, 5);
        app.getTopPopulatedCitiesInRegion("Caribbean", 0);
        app.getTopPopulatedCitiesInRegion("Caribbean", -1);
    }

    /**
     *  Issue 20
     *  tests invalid inputs are handled correctly
     */
    @Test
    void testTopPopulatedCapitalCites()
    {
        app.getTopPopulatedCapitalCites(0);
        app.getTopPopulatedCapitalCites(-1);
    }


    /**
     *  Issue 21
     *  tests invalid inputs are handled correctly
     */
    @Test
    void testTopPopulatedCapitalCitiesInContinent()
    {
        app.getTopPopulatedCapitalCitiesInContinent(null,5);
        app.getTopPopulatedCapitalCitiesInContinent("", 5);
        app.getTopPopulatedCapitalCitiesInContinent("Europe", 0);
        app.getTopPopulatedCapitalCitiesInContinent("Europe", -1);
    }
}