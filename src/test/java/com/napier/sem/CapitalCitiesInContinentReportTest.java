package com.napier.sem;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CapitalCitiesInContinentReportTest {

    @Test
    public void testCapitalCitiesReport() {

        CapitalCitiesInContinentReport report =
                new CapitalCitiesInContinentReport();

        assertNotNull(report);
    }
}