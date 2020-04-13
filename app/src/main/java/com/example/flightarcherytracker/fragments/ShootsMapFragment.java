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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShootsMapFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "ShowShootsOnMapFragment";

    // private ShootViewModel mShootViewModel;
    private static final String MAPVIEW_BUNDLE_KEY_SHOW = "MapViewBundleKeyShow";

    private MapView mMapView;
    private GoogleMap mMap;
    private double startLat;
    private double startLng;
    private RelativeLayout mLayout;


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
        ImageButton arrowBack = getActivity().findViewById(R.id.btn_toolbar_arrow_back);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getActivity()).onBackPressed();
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.fragment_shoots_map_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, true));
        recyclerView.setHasFixedSize(true);

        final ShootMapRecyclerViewAdapter adapter = new ShootMapRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        assert getArguments() != null;
        final long trainingId = getArguments().getLong("id");
        startLat = getArguments().getDouble("startLat");
        startLng = getArguments().getDouble("startLng");


        ViewModelProvider.Factory factory = ViewModelProvider.AndroidViewModelFactory
                .getInstance(Objects.requireNonNull(getActivity()).getApplication());
        ViewModelProvider viewModelProvider = new ViewModelProvider(requireActivity(), factory);

        final ShootViewModel shootViewModel = viewModelProvider.get(ShootViewModel.class);

        shootViewModel.getAllShootsLatLngByTrainingId(trainingId).observe(getViewLifecycleOwner(),
                new Observer<List<Shoot>>() {
                    @Override
                    public void onChanged(List<Shoot> list) {
                        adapter.setShoots(list, mMap);

                        createStartMarker(startLat, startLng);
                        final LatLng startLocation = new LatLng(startLat, startLng);
                        CameraPosition newCameraPosition = new CameraPosition.Builder()
                                .target(startLocation)
                                .zoom(20f)
                                .build();
                        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(newCameraPosition));

                        for(int i = 0; i < list.size(); i += 1) {
                            createMarker(list.get(i).getShootLat()
                                    , list.get(i).getShootLng());
                        }
                    }
        });

        return view;
    }

    private void createMarker(double lat, double lng) {
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lng))
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
    }

    private void createStartMarker(double lat, double lng) {
         mMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lng))
                .title("start")
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.defaultMarker()));
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

}
