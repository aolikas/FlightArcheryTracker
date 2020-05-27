package com.example.flightarcherytracker;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flightarcherytracker.fragments.TrainingsSessionFragment;

import java.util.Date;

public class TestActivity extends AppCompatActivity implements
        TrainingsSessionFragment.TrainingListener,
        TrainingsSessionFragment.ShootsListener,
        TrainingsSessionFragment.ButtonStartListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);
    }

    @Override
    public void onTrainingInputListener(Date timestamp, double lat, double lng) {

    }

    @Override
    public void onShootsInputListener(double lat, double lng, String description, double distance) {

    }

    @Override
    public void shareCondition(boolean condition) {

    }
}
