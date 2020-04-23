package com.example.flightarcherytracker;



import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
import java.util.Locale;


public class MainActivity extends AppCompatActivity
        implements TrainingsSessionFragment.TrainingListener,
        TrainingsSessionFragment.ShootsListener, TrainingsSessionFragment.ButtonStartListener {

    private static final String TAG = "MainActivity";

    private TrainingViewModel mTrainingViewModel;
    private ShootViewModel mShootViewModel;

    private long trainingId;
    private  boolean mCondition = false;


    private String currentLanguage ="en";
    private String currentLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentLanguage = getIntent().getStringExtra(currentLang);
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
    public void shareCondition(boolean condition) {
        Log.d(TAG, "shareCondition: first " + mCondition);
        mCondition = condition;
        Log.d(TAG, "shareCondition: " + mCondition);

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
                    Toast.makeText(this, R.string.toast_before_delete_all, Toast.LENGTH_LONG).show();
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

            case R.id.language_english:
                if(mCondition) {
                    Toast.makeText(this, R.string.toast_before_change_language, Toast.LENGTH_LONG).show();
                } else {
                    setLocale("en");
                }
                break;

            case R.id.language_russian:
                if(mCondition) {
                    Toast.makeText(this, R.string.toast_before_change_language, Toast.LENGTH_LONG).show();
                } else {
                    setLocale("ru");
                }
                break;

            case R.id.send_email:
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[] {"arisiru@hotmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "feedback");
                try {
                    startActivity(Intent.createChooser(i, "Send"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(this, "There are no email user installed", Toast.LENGTH_SHORT).show();
                }

        }
        return super.onOptionsItemSelected(item);
    }


    public void setLocale(String language) {
        if (!language.equals(currentLanguage)) {
            Locale locale = new Locale(language);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration config = res.getConfiguration();

            if(Build.VERSION.SDK_INT >= 24) {
                config.setLocale(locale);
            } else {
                config.locale = locale;
            }
            res.updateConfiguration(config, dm);
            Intent refresh = new Intent(this, MainActivity.class);
            refresh.putExtra(currentLang, language);
            startActivity(refresh);
        } else {
            Toast.makeText(this, R.string.toast_language_selected, Toast.LENGTH_SHORT).show();
        }
    }


}
