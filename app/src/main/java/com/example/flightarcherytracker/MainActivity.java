package com.example.flightarcherytracker;



import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;


import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.example.flightarcherytracker.adapters.ViewPagerAdapter;
import com.example.flightarcherytracker.entity.Shoot;
import com.example.flightarcherytracker.entity.Training;
import com.example.flightarcherytracker.fragments.TrainingsRecordsFragment;
import com.example.flightarcherytracker.fragments.TrainingsSessionFragment;
import com.example.flightarcherytracker.viewModel.ShootViewModel;
import com.example.flightarcherytracker.viewModel.ShootsViewModelFactory;
import com.example.flightarcherytracker.viewModel.TrainingFactory;
import com.example.flightarcherytracker.viewModel.TrainingViewModel;

import com.google.android.material.tabs.TabLayout;

import java.util.Date;
import java.util.Objects;


public class MainActivity extends AppCompatActivity
        implements TrainingsSessionFragment.TrainingListener,
        TrainingsSessionFragment.ShootsListener, TrainingsSessionFragment.ButtonStartListener {

    private static final String TAG = "MainActivity";

    private TrainingViewModel mTrainingViewModel;
    private ShootViewModel mShootViewModel;

    private long trainingId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        TabLayout tabLayout = findViewById(R.id.tab_layout_activity_main);
        ViewPager viewPager = findViewById(R.id.view_pager_activity_main);

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

        //init toolbar
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_activity_main));
        setTitle(R.string.app_name);
        RelativeLayout layout = findViewById(R.id.toolbar_back_arrow_container);
        layout.setVisibility(View.INVISIBLE);


        mTrainingViewModel = new ViewModelProvider(this, new TrainingFactory(getApplication())).get(TrainingViewModel.class);
        mShootViewModel = new ViewModelProvider(this, new ShootsViewModelFactory(getApplication()))
                .get(ShootViewModel.class);
    }

    @Override
    public void onTrainingInputListener(Date timestamp, double lat, double lng) {

        Training currentTraining = new Training(timestamp, lat, lng);

        trainingId = mTrainingViewModel.insertTrainingWithId(currentTraining);

        Log.d(TAG, "onTrainingInputListener: insert " + timestamp + " " + lat + " " + lng);
    }



    @Override
    public void onShootsInputListener(double lat, double lng, String description, double distance) {

        Shoot currentShoot = new Shoot(lat, lng, description, distance, trainingId);

        mShootViewModel.insertShoot(currentShoot);

        Log.d(TAG, "onShootsInputListener: inset " + description + " " + lat + " " + lng
                + " " + distance + " " + trainingId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.delete_all:
                if(mCondition) {
                    Toast.makeText(this, "Please make sure that you stop your current training", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                    alertDialogBuilder.setTitle(R.string.alert_dialog_delete_all_title);
                    alertDialogBuilder.setMessage(R.string.alert_dialog_delete_all_message);
                    alertDialogBuilder.setPositiveButton(R.string.alert_dialog_delete_all_positive, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mTrainingViewModel.deleteAllTrainings();
                        }
                    });

                    alertDialogBuilder.setNegativeButton(R.string.alert_dialog_delete_all_negative, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    alertDialogBuilder.show();

                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    boolean mCondition = false;



    @Override
    public void shareCondition(boolean condition) {
        Log.d(TAG, "shareCondition: first " + mCondition);

        mCondition = condition;
        Log.d(TAG, "shareCondition: " + mCondition);

    }
}
