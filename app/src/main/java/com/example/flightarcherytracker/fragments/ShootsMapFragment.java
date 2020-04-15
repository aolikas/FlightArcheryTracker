package com.example.flightarcherytracker.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.flightarcherytracker.R;
import com.example.flightarcherytracker.adapters.ShootMapRecyclerViewAdapter;
import com.example.flightarcherytracker.entity.Shoot;
import com.example.flightarcherytracker.viewModel.ShootViewModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShootsMapFragment extends Fragment implements OnMapReadyCallback,
        View.OnClickListener {

    private static final String TAG = "ShowShootsOnMapFragment";

    // private ShootViewModel mShootViewModel;
    private static final String MAPVIEW_BUNDLE_KEY_SHOW = "MapViewBundleKeyShow";

    private MapView mMapView;
    private GoogleMap mMap;
    private double mStartLat;
    private double mStartLng;
    private RelativeLayout mLayout;
    private Marker mStartMarker;
    private ArrayList<Marker> mMarkers = new ArrayList<>();

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

       // Toolbar toolbar = getActivity().findViewById(R.id.toolbar_activity_main);
        mLayout = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar_back_arrow_container);
        mLayout.setVisibility(View.VISIBLE);
        ImageButton backArrow = getActivity().findViewById(R.id.btn_toolbar_arrow_back);
        backArrow.setOnClickListener(this);


        RecyclerView recyclerView = view.findViewById(R.id.fragment_shoots_map_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, true));
        recyclerView.setHasFixedSize(true);

        final ShootMapRecyclerViewAdapter adapter = new ShootMapRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        assert getArguments() != null;
        final long trainingId = getArguments().getLong("id");
        mStartLat = getArguments().getDouble("startLat");
        mStartLng = getArguments().getDouble("startLng");

        ViewModelProvider.Factory factory = ViewModelProvider.AndroidViewModelFactory
                .getInstance(Objects.requireNonNull(getActivity()).getApplication());
        ViewModelProvider viewModelProvider = new ViewModelProvider(requireActivity(), factory);

        final ShootViewModel shootViewModel = viewModelProvider.get(ShootViewModel.class);

        shootViewModel.getAllShootLatLngDistByTrainingId(trainingId).observe(getViewLifecycleOwner(),
                new Observer<List<Shoot>>() {
                    @Override
                    public void onChanged(List<Shoot> list) {
                        adapter.setShoots(list, mMap);

                        mStartMarker = createStartMarker(mStartLat, mStartLng);

                        for(int i = 0; i <list.size(); i += 1) {
                            Marker shootMarker = createShootMarker(list.get(i).getShootLat()
                                                , list.get(i).getShootLng());
                            mMarkers.add(shootMarker);
                        }
                        showAllMarkers(mStartMarker, mMarkers);
                    }
                });

        return view;
    }

    private void showAllMarkers(Marker startMarker, ArrayList<Marker> markers) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(startMarker.getPosition());

        for(int i = 0; i < markers.size(); i += 1) {
            builder.include(markers.get(i).getPosition());
        }

        LatLngBounds bounds = builder.build();

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.10); // offset from edges of the map 10% of screen

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);

        mMap.animateCamera(cu);

    }

    private Marker createShootMarker(double lat, double lng) {
       return mMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lng))
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
    }

    private Marker createStartMarker(double lat, double lng) {
       return   mMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lng))
                .title(getString(R.string.marker_start_title))
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_toolbar_arrow_back:
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;
        }

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

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "Training: onResume");
        mMapView.onResume();
        mLayout.setVisibility(View.VISIBLE);
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
        mLayout.setVisibility(View.INVISIBLE);


    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "Training: onStop");
        mMapView.onPause();
        mLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Training: onDestroy");
        mMapView.onDestroy();
        mMap = null;
        mLayout.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d(TAG, "Training: onLowMemory");
        mMapView.onLowMemory();
    }



    //     shootViewModel.getAllShootsLatLngByTrainingId(trainingId).observe(getViewLifecycleOwner(),
    //           new Observer<List<Shoot>>() {
    //             @Override
    //           public void onChanged(List<Shoot> list) {
    //             adapter.setShoots(list, mMap);

    //           createStartMarker(startLat, startLng);
    //         final LatLng startLocation = new LatLng(startLat, startLng);
    //       CameraPosition newCameraPosition = new CameraPosition.Builder()
    //             .target(startLocation)
    //           .zoom(20f)
    //         .build();
    //      mMap.moveCamera(CameraUpdateFactory.newCameraPosition(newCameraPosition));

    //    for(int i = 0; i < list.size(); i += 1) {
    //      createMarker(list.get(i).getShootLat()
    //            , list.get(i).getShootLng());
    //      }
    //}
    //  });

}
