package com.example.flightarcherytracker.helpers;

/**
 * Small class that provides approximate distance in meters between
 * two points using the Haversine formula
 */
public class CalculateDistanceBetweenMarkers {

    public static double distanceTo(double startLat, double startLng, double endLat, double endLng) {

        double earthRadius = 6371000; // approx Earth radius in meters

        // distance between latitudes and longitudes
        double dLat = Math.toRadians(endLat - startLat);
        double dLng = Math.toRadians(endLng - startLng);

        // convert to radians
        startLat = Math.toRadians(startLat);
        endLat = Math.toRadians(endLat);

        // apply formula
        double a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLng);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt((1 - a)));
        return earthRadius * c;
    }

    private static double haversin(double value) {
        return Math.pow(Math.sin(value / 2), 2);
    }
}