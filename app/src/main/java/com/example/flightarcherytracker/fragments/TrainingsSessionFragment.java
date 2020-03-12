package com.example.flightarcherytracker.fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flightarcherytracker.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrainingsSessionFragment extends Fragment implements View.OnClickListener  {

    private static final String TAG = "TrainingSessionFragment";

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 9002;

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    private FusedLocationProviderClient mFusedLocationClient;
    private MapView mMapView;
    private GoogleMap mMap;
    private Location mCurrentLocation;
    private boolean mFirstTimeFlag = true;

    private Button mStartTrainingButton;
    private Button mSaveShootButton;


    public TrainingsSessionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Log.d(TAG, "TrainingSession: onCreate");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(TAG, "TrainingSession: onCreateView");
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_trainings_session, container, false);

        TextView tv = view.findViewById(R.id.training);
        tv.setText("Hello trainings");

        //init button startTraining
        mStartTrainingButton = view.findViewById(R.id.training_btn_start);
        mStartTrainingButton.setOnClickListener(this);

        //make button StartTraining full screen
        mStartTrainingButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        //init and make invisible button SaveShoot
        mSaveShootButton = view.findViewById(R.id.training_btn_save);
        mSaveShootButton.setOnClickListener(this);
        mSaveShootButton.setVisibility(View.GONE);

        return view;
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.training_btn_start:
                setParamsForButtons();
                Toast.makeText(getActivity(), "Button Training is clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.training_btn_save:
                Toast.makeText(getActivity(), "Button Shoot is clicked", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    private void setParamsForButtons() {

        mSaveShootButton.setVisibility(View.VISIBLE);

        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 50f);
        params1.setMarginEnd(5);
        mStartTrainingButton.setLayoutParams(params1);
        mStartTrainingButton.setText(R.string.button_start_new_training);


        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 50f);
        params2.setMarginStart(5);
        mSaveShootButton.setLayoutParams(params2);
    }

}
