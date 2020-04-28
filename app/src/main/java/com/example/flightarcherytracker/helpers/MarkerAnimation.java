package com.example.flightarcherytracker.helpers;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;


public class MarkerAnimation {

    private static final String TAG = "Marker Animation TEST";

    public static void animateMarkerToGB(final Marker marker, final LatLng finalPosition,
                                         final LatLngInterpolator latLngInterpolator) {
        final LatLng startPosition = marker.getPosition();

        Log.d(TAG, "animateMarkerToGB: start position: " + startPosition);
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final Interpolator interpolator = new AccelerateDecelerateInterpolator();
        final float durationImMs = 2000;

        handler.post(new Runnable() {
            long elapsed;
            float t;
            float v;

            @Override
            public void run() {
                // calculate progress using interpolator
                elapsed = SystemClock.uptimeMillis() - start;
                t = elapsed / durationImMs;
                v = interpolator.getInterpolation(t);


                marker.setPosition(latLngInterpolator.interpolate(v, startPosition, finalPosition));

                // repeat till progress is complete
                if (t < 1) {
                    //post again 16ms later
                    handler.postDelayed(this, 16);
                }

            }
        });
    }
}