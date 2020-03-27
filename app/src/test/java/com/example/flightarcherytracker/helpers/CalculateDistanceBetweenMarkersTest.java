package com.example.flightarcherytracker.helpers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CalculateDistanceBetweenMarkersTest {

    private double startLat, startLtn, endLat, endLng;
    private double expectedResult, result;
    private CalculateDistanceBetweenMarkers testClass;

    @Before
    public void setUp() throws Exception {
        testClass = new CalculateDistanceBetweenMarkers();
    }

    @After
    public void tearDown() throws Exception {
        testClass = null;
    }

    @Test
    public void testDistanceEmptyVal() {
        expectedResult = 12.34;
        result = CalculateDistanceBetweenMarkers.distanceTo(startLat, startLtn, endLat, endLng);
        assertNotEquals(expectedResult, result, 0.0);
    }

    @Test
    public void testDistanceNegVal() {
        expectedResult = -255221.80906283788;
        result = CalculateDistanceBetweenMarkers.distanceTo(52.5244, 13.4105, 53.5753, 10.0153);
        assertNotEquals(expectedResult, result, 0.0);
    }

    @Test
    public void testDistance() {
        expectedResult = 255221.80906283788;
        result = CalculateDistanceBetweenMarkers.distanceTo(52.5244, 13.4105, 53.5753, 10.0153);
        assertEquals(expectedResult, result, 0.0);

    }
}