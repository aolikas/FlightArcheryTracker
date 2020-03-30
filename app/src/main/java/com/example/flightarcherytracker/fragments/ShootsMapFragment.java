package com.example.flightarcherytracker.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.flightarcherytracker.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShootsMapFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "ShowShootsOnMapFragment";

    // private ShootViewModel mShootViewModel;
    private static final String MAPVIEW_BUNDLE_KEY_SHOW = "MapViewBundleKeyShow";

    private MapView mMapView;
    private GoogleMap mMap;
    private MarkerOptions mOptions = new MarkerOptions();
    private ArrayList<LatLng> latLngs = new ArrayList<>();

    public ShootsMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shoots_map, container, false);
        mMapView = view.findViewById(R.id.map_view_shoots);
        initMap(savedInstanceState);


        long trainingId = getArguments().getLong("id");
        double shootLat = getArguments().getDouble("lat");
        double shootLng = getArguments().getDouble("lng");

        latLngs.add(new LatLng(shootLat, shootLng));

        Log.d(TAG, "onCreateView: received " + trainingId +
                " lat: " + shootLat + ", lng " + shootLng);

        return view;
    }

    private void initMap(Bundle savedInstanceState) {
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY_SHOW);
        }

        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "Training: onSaveInstanceState");

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY_SHOW);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY_SHOW, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        googleMap.setMyLocationEnabled(false);
        for (LatLng point : latLngs) {
            mOptions.position(point);
            mOptions.title("some");
            mOptions.icon(BitmapDescriptorFactory.defaultMarker());
            mMap.addMarker(mOptions);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "Training: onResume");
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "Training: onStart");
        mMapView.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "Training: onStop");
        mMapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "Training: onStop");
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Training: onDestroy");
        mMapView.onDestroy();
        mMap = null;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d(TAG, "Training: onLowMemory");
        mMapView.onLowMemory();
    }

}
