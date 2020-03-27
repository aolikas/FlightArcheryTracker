package com.example.flightarcherytracker;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;

import androidx.viewpager.widget.ViewPager;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.flightarcherytracker.adapters.ViewPagerAdapter;
import com.example.flightarcherytracker.entity.Shoot;
import com.example.flightarcherytracker.entity.Training;
import com.example.flightarcherytracker.fragments.TrainingsRecordsFragment;
import com.example.flightarcherytracker.fragments.TrainingsSessionFragment;
import com.example.flightarcherytracker.viewModel.ShootViewModel;
import com.example.flightarcherytracker.viewModel.ShootsViewModelFactory;
import com.example.flightarcherytracker.viewModel.TrainingViewModel;
import com.example.flightarcherytracker.viewModel.TrainingViewModelFactory;
import com.google.android.material.tabs.TabLayout;

import java.util.Date;


public class MainActivity extends AppCompatActivity
        implements TrainingsSessionFragment.TrainingListener,
        TrainingsSessionFragment.ShootsListener {

    private static final String TAG = "MainActivity";

    private TrainingViewModel mTrainingViewModel;
    private ShootViewModel mShootViewModel;

    private long trainingId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        TabLayout tabLayout = findViewById(R.id.activity_main_tab_layout);
        ViewPager viewPager = findViewById(R.id.activity_main_view_pager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),
                tabLayout.getTabCount());


        //add fragment
        String titleFragment1 = getString(R.string.fragment_title_1);
        String titleFragment2 = getString(R.string.fragment_title_2);

        viewPagerAdapter.AddFragment(new TrainingsSessionFragment(), titleFragment1);
        viewPagerAdapter.AddFragment(new TrainingsRecordsFragment(), titleFragment2);


        viewPager.setOffscreenPageLimit(1);

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        mTrainingViewModel = new ViewModelProvider(this, new TrainingViewModelFactory(getApplication(), trainingId))
                .get(TrainingViewModel.class);
        mShootViewModel = new ViewModelProvider(this, new ShootsViewModelFactory(getApplication()))
                .get(ShootViewModel.class);
    }

    @Override
    public void onTrainingInputListener(Date timestamp, double lat, double lng) {

        Training currentTraining = new Training(timestamp, lat, lng);

        trainingId = mTrainingViewModel.insertTrainingWithId(currentTraining);

        Log.d(TAG, "onTrainingInputListener: insert " + timestamp + " " + lat + " " + lng);

        Toast.makeText(this, "Training is started saving", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShootsInputListener(double lat, double lng, String description, double distance) {

        Shoot currentShoot = new Shoot(lat, lng, description, distance, trainingId);

        mShootViewModel.insertShoot(currentShoot);

        Log.d(TAG, "onShootsInputListener: inset " + description + " " + lat + " " + lng
                + " " + distance + " " + trainingId);
    }

}
