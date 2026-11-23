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

    @Test
    void unitTest3()
    {
        assertEquals(5, 5, "Messages are equal");
    }

    @Test
    void unitTestCitiesInContinent() {app.getCitiesInContinent(null, 0); }

    @Test
    void unitTestCitiesInRegion() {app.getCitiesByRegion(null, 0); }

    @Test
    void unitTestTopCitiesByContinent() {app.getTopNCitiesInContinent(null, 0); }

    @Test
    void testCountriesByContinent()
    {
        app.getCountriesByContinent(null);
    }

    @Test
    void testTopPopulatedCountriesZero()
    {
        app.getTopPopulatedCountries(0);
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

    @Test
    void unitTestgetTopNPopulatedCitiesInWorld() {app.getTopNPopulatedCitiesInWorld(0);}
}