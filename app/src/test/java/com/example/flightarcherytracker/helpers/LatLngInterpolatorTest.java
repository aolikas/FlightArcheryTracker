package com.example.flightarcherytracker.helpers;

import com.google.android.gms.maps.model.LatLng;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;

public class LatLngInterpolatorTest {

    LatLngInterpolator test = spy(LatLngInterpolator.class);

    LatLng a = new LatLng(37.4219983, -122.084);
    LatLng b = new LatLng(67.4219983, 121.084);
    LatLng expectedResult = new LatLng(1, 1);

    private float v;


    @Test
    public void testNotEquals() {
        v = 1.578927F;
        LatLng result = test.interpolate(v, a, b);
        assertNotEquals(expectedResult, result);
    }

}