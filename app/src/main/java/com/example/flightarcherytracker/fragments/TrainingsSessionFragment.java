package com.example.flightarcherytracker.fragments;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import android.widget.RelativeLayout;
import android.widget.Toast;


import com.example.flightarcherytracker.R;
import com.example.flightarcherytracker.helpers.CalculateDistanceBetweenMarkers;
import com.example.flightarcherytracker.helpers.LatLngInterpolator;
import com.example.flightarcherytracker.helpers.MarkerAnimation;
import com.example.flightarcherytracker.helpers.SetBitmapDescriptorFromVector;
import com.example.flightarcherytracker.helpers.SetParamsForButtons;
import com.example.flightarcherytracker.helpers.SetParamsForMainButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrainingsSessionFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener {

    private static final String TAG = "TrainingSessionFragment";

    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 9002;
    private static final int PERMISSIONS_REQUEST_ENABLE_GPS = 9003;


    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    private FusedLocationProviderClient mFusedLocationClient;
    private MapView mMapView;
    private GoogleMap mMap;
    private Location mCurrentLocation;
    private Marker mCurrentLocationMarker;
    private boolean mFirstTimeFlag = true;

    private EditText mShootDescriptionEt;
    private Button mStartTrainingButton;
    private Button mSaveShootButton;
    private Button mStopTrainingButton;

    //initial lat and lng for training
    private double mStartLat;
    private double mStartLng;

    private int mShootCount = 0;

    private TrainingListener mTrainingListener;
    private ShootsListener mShootsListener;
    private ButtonStartListener mButtonListener;


    public TrainingsSessionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Log.d(TAG, "TrainingSession: onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(TAG, "TrainingSession: onCreateView");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trainings_session, container, false);

        mMapView = view.findViewById(R.id.map_view);
        initMap(savedInstanceState);

        //put compass margin
        if (mMapView != null && mMapView.findViewById(Integer.parseInt("1")) != null) {
            View compass = ((View) mMapView.findViewById(Integer.parseInt("1")).getParent())
                    .findViewById(Integer.parseInt("5"));

            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    compass.getLayoutParams();

            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            layoutParams.setMargins(20, 380, 20, 0);
        }

        //init button startTraining
        mStartTrainingButton = view.findViewById(R.id.btn_start_training);
        mStartTrainingButton.setOnClickListener(this);

        //init button StopTraining
        mStopTrainingButton = view.findViewById(R.id.btn_stop_training);
        mStopTrainingButton.setOnClickListener(this);
        mStopTrainingButton.setVisibility(View.GONE);

        //init and make invisible button SaveShoot
        mSaveShootButton = view.findViewById(R.id.btn_save_shoots);
        mSaveShootButton.setOnClickListener(this);
        mSaveShootButton.setVisibility(View.GONE);

        SetParamsForMainButton.setParamsForTrainingButton(mStartTrainingButton);

        return view;
    }

    private void initMap(Bundle savedInstanceState) {
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        Log.d(TAG, "TrainingSession: onInitMap");
        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
        //       != PackageManager.PERMISSION_GRANTED
        //     && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
        //   != PackageManager.PERMISSION_GRANTED) {
        //return;
        //  }

        mMap = googleMap;
        Log.d(TAG, "TrainingSession: onMapReady");
        mMap.setMyLocationEnabled(false);
        mMap.getUiSettings().setCompassEnabled(true);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "Training: onSaveInstanceState");

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }

    private final LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if (locationResult.getLastLocation() == null)
                return;
            mCurrentLocation = locationResult.getLastLocation();

            Log.d(TAG, "LocationCallback: " + mCurrentLocation.getLatitude()
                    + mCurrentLocation.getLongitude());

            if (mFirstTimeFlag && mMap != null) {
                Log.d(TAG, "LocationCallback: map is not null");
                animateCamera(mCurrentLocation);
                mFirstTimeFlag = false;
            }
            Log.d(TAG, "TrainingSession: onShowMarker");
            showMarker(mCurrentLocation);
        }
    };

    private void startCurrentLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(3000);
        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(Objects.requireNonNull(getActivity())), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            return;
        }
        Log.d(TAG, "TrainingSession: onStartCurrentLocationUpdates");
        mFusedLocationClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());
    }


    // get start/new location for training
    private void getTrainingStartLocation() {
        Log.d(TAG, "onGetTrainingStartLocation: getting the device current location");

        mMap.clear();

        mStartLat = mCurrentLocation.getLatitude();
        mStartLng = mCurrentLocation.getLongitude();

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(mStartLat, mStartLng))
                .title(getString(R.string.marker_start_title))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        animateCamera(mCurrentLocation);

        mCurrentLocationMarker = mMap.addMarker(getMarker(mStartLng, mStartLng));

        Log.d(TAG, "getTrainingStartLocation: animateCamera with start: " + mStartLat + " "
                + mStartLng);

        mTrainingListener.onTrainingInputListener(new Date(), mStartLat, mStartLng);

        Log.d(TAG, "onCompleteTrainingStartLocation: training start location: lat: " + mStartLat +
                ", lng: " + mStartLng);

    }

    private MarkerOptions getMarker(double lat, double lng) {
        return new MarkerOptions()
                .position(new LatLng(lat, lng))
                .icon(SetBitmapDescriptorFromVector.bitmapDescriptorFromVector(getActivity(),
                        R.drawable.ic_my_location));
    }

    private void getShootsLocation() {

        Log.d(TAG, "getShootsLocation: getting shoots location");

        double shootLat = mCurrentLocation.getLatitude();
        double shootLng = mCurrentLocation.getLongitude();

        double distance = CalculateDistanceBetweenMarkers.distanceTo(mStartLat, mStartLng, shootLat, shootLng);

        DecimalFormat df = new DecimalFormat("#.#");

        Marker shootMarker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(shootLat, shootLng))
                .title(getString(R.string.marker_shoot_part1) + mShootCount + getString(R.string.marker_shoot_part_2) + Double.parseDouble(df.format(distance)))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));


        Log.d(TAG, "onCompleteShoot: latitude: + " + shootLat +
                "longitude: " + shootLng);

        animateCamera(mCurrentLocation);

        showDialogForSaveShoot(shootLat, shootLng, distance, shootMarker);

        Toast.makeText(getActivity(), getString(R.string.toast_save_shoot), Toast.LENGTH_SHORT).show();
    }

    private void showDialogForSaveShoot(final double lat, final double lng, final double distance, final Marker shootMarker) {

        View messageView = Objects.requireNonNull(getActivity()).getLayoutInflater().inflate(R.layout.shoot_message_dialog,
                new LinearLayout(getActivity()), false);

        mShootDescriptionEt = messageView.findViewById(R.id.dialog_shoot_message_description);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(messageView);
        alertDialogBuilder.setCancelable(true);

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setTitle(getString(R.string.dialog_shoot_message_title));


        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.dialog_shoot_message_positive),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String description = mShootDescriptionEt.getText().toString();

                        mShootsListener.onShootsInputListener(lat, lng, description,
                                distance);

                        Log.d(TAG, "sendShoots: " + lat + " " + lng);
                    }
                });

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.dialog_shoot_message_negative),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        shootMarker.remove();
                    }
                });

        alertDialog.show();
    }


    private void animateCamera(@NonNull Location location) {
        final LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        //statt animateCamera
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(getCameraPositionWithBearing(latLng)));
    }

    @NonNull
    private CameraPosition getCameraPositionWithBearing(LatLng latLng) {
        return new CameraPosition.Builder().target(latLng).zoom(20).bearing(180).tilt(45).build();
    }

    private void showMarker(@NonNull Location currentLocation) {
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        if (mCurrentLocationMarker == null) {
            mCurrentLocationMarker = mMap.addMarker(getMarker(currentLocation.getLatitude(), currentLocation.getLongitude()));
            animateCamera(currentLocation);
        }
            //  mCurrentLocationMarker = mMap.addMarker(new MarkerOptions()
            //        .icon(SetBitmapDescriptorFromVector.bitmapDescriptorFromVector(getActivity(),
            //              R.drawable.ic_my_location)).position(latLng));
        else
            MarkerAnimation.animateMarkerToGB(mCurrentLocationMarker, latLng, new LatLngInterpolator.Spherical());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_training:
                mButtonListener.shareCondition(true);

                mShootCount = 0;
                if (mMap != null && mCurrentLocation != null) {
                    Log.d(TAG, "onClick training location: " + mCurrentLocation.getLatitude()
                            + mCurrentLocation.getLongitude());
                    getTrainingStartLocation();
                }
                //changes params for buttons two instead of one
                mStartTrainingButton.setVisibility(View.GONE);
                SetParamsForButtons.setParamsForButtons(mStopTrainingButton, mSaveShootButton);
                Toast.makeText(getActivity(), R.string.toast_start_training, Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_save_shoots:
                mShootCount += 1;
                getShootsLocation();
                break;

            case R.id.btn_stop_training:
                mButtonListener.shareCondition(false);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

                alertDialogBuilder.setTitle(R.string.alert_dialog_stop_training_title);
                alertDialogBuilder.setMessage(R.string.alert_dialog_stop_training_message);
                alertDialogBuilder.setPositiveButton(R.string.alert_dialog_stop_training_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mMap.clear();
                        mCurrentLocationMarker = mMap.addMarker(getMarker(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()));

                        Toast.makeText(getActivity(), R.string.toast_stop_training, Toast.LENGTH_SHORT).show();

                        mStopTrainingButton.setVisibility(View.GONE);
                        mSaveShootButton.setVisibility(View.GONE);
                        mStartTrainingButton.setVisibility(View.VISIBLE);
                        SetParamsForMainButton.setParamsForTrainingButton(mStartTrainingButton);
                    }
                });

                alertDialogBuilder.setNegativeButton(R.string.alert_dialog_stop_training_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alertDialogBuilder.show();

                break;
        }

    }

    public int getShootsCount() {
        return mShootCount;
    }


    //interface for communication
    public interface TrainingListener {

        void onTrainingInputListener(Date timestamp, double lat, double lng);
    }

    public interface ShootsListener {

        void onShootsInputListener(double lat, double lng, String description,
                                   double distance);
    }

    public interface ButtonStartListener {
        void shareCondition(boolean condition);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        if (checkMapServices()) {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getActivity()));
            startCurrentLocationUpdates();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        mFusedLocationClient = null;
        mMap = null;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "Training: onAttach");
        if (context instanceof TrainingListener) {
            mTrainingListener = (TrainingListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement mTrainingListener");
        }
        if (context instanceof ShootsListener) {
            mShootsListener = (ShootsListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement mShootsListener");
        }

        if (context instanceof ButtonStartListener) {
            mButtonListener = (ButtonStartListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement mShootsListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "Training: onDetach");
        mTrainingListener = null;
        mShootsListener = null;
        mButtonListener = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: is called");
        if (requestCode == PERMISSIONS_REQUEST_ENABLE_GPS) {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getActivity()));
            startCurrentLocationUpdates();
        }
    }

    // *************************** Google play services checking **************************** \\


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED)
                Toast.makeText(getActivity(), R.string.toast_deny_request_permission, Toast.LENGTH_SHORT).show();
            else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) ;
            startCurrentLocationUpdates();

        }
    }

    private boolean checkMapServices() {
        if (isServicesOk()) {
            return isMapEnabled();
        }
        return false;
    }

    private boolean isServicesOk() {
        Log.d(TAG, "isServicesOk: checking google services version");

        int status = GoogleApiAvailability.getInstance()
                .isGooglePlayServicesAvailable(getActivity());
        if (status == ConnectionResult.SUCCESS) {
            //everything is fine and the user can make map request
            Log.d(TAG, "isServicesOk: Google paly services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(status)) {
            // an error is happened but still resolving
            Log.d(TAG, "isServicesOk: an error is happened but still resolving");
            Dialog dialog = GoogleApiAvailability.getInstance()
                    .getErrorDialog(getActivity(), status, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(getActivity(), R.string.toast_no_google_play_service, Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private boolean isMapEnabled() {
        Log.d(TAG, "isMapEnabled: checking map is enabled");
        final LocationManager manager =
                (LocationManager) Objects.requireNonNull(getActivity()).getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGPS();
            return false;
        }
        return true;
    }

    private void buildAlertMessageNoGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getString(R.string.alert_dialog_no_gps_message_part_1) +
                getString(R.string.alert_dialog_no_gps_message_part_2))
                .setCancelable(false)
                .setPositiveButton(R.string.alert_dialog_no_gps_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog,
                                        @SuppressWarnings("unused") final int id) {
                        Intent enableGPSIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGPSIntent, PERMISSIONS_REQUEST_ENABLE_GPS);

                    }
                });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}