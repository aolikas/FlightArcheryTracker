package com.example.flightarcherytracker;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
    private boolean mCondition = false;


    private String currentLanguage = "en";
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

    }

    @Override
    public void onShootsInputListener(double lat, double lng, String description, double distance) {

        Shoot currentShoot = new Shoot(lat, lng, description, distance, trainingId);

        mShootViewModel.insertShoot(currentShoot);
    }

    @Override
    public void shareCondition(boolean condition) {
        mCondition = condition;
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
                if (mCondition) {
                    Toast.makeText(this, R.string.toast_before_delete_all, Toast.LENGTH_LONG).show();
                } else {

                    View deleteAllTrainingsView = getLayoutInflater().inflate(R.layout.dialog_delete_all_trainings, new LinearLayout(this), false);

                    AlertDialog.Builder deleteAllDialogBuilder = new AlertDialog.Builder(this);
                    deleteAllDialogBuilder.setView(deleteAllTrainingsView);
                    deleteAllDialogBuilder.setCancelable(true);

                    Button deleteAllPositive = deleteAllTrainingsView.findViewById(R.id.dialog_delete_all_trainings_yes);
                    Button deleteAllNegative = deleteAllTrainingsView.findViewById(R.id.dialog_delete_all_trainings_no);

                    final AlertDialog dialogDeleteAll = deleteAllDialogBuilder.create();

                    deleteAllPositive.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mTrainingViewModel.deleteAllTrainings();
                            dialogDeleteAll.dismiss();
                        }
                    });

                    deleteAllNegative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogDeleteAll.cancel();
                        }
                    });

                    dialogDeleteAll.show();
                }
                break;

            case R.id.language_english:
                if (mCondition) {
                    Toast.makeText(this, R.string.toast_before_change_language, Toast.LENGTH_LONG).show();
                } else {
                    setLocale("en");
                }

                break;

            case R.id.language_russian:
                if (mCondition) {
                    Toast.makeText(this, R.string.toast_before_change_language, Toast.LENGTH_LONG).show();
                } else {
                    setLocale("ru");
                }

                break;

            case R.id.feedback:

                AlertDialog.Builder feedbackDialogBuilder = new AlertDialog.Builder(this);
                LayoutInflater inflaterFeedback = getLayoutInflater();
                View dialogViewFeedback = inflaterFeedback.inflate(R.layout.dialog_feedback_message, new LinearLayout(this));
                feedbackDialogBuilder.setView(dialogViewFeedback);
                Button feedbackDismiss = dialogViewFeedback.findViewById(R.id.dialog_feedback_button_dismiss);
                Button feedbackTelegram = dialogViewFeedback.findViewById(R.id.dialog_feedback_button_telegram);
                Button feedbackEmail = dialogViewFeedback.findViewById(R.id.dialog_feedback_button_email);

                final AlertDialog dialogFeedback = feedbackDialogBuilder.create();

                feedbackDismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogFeedback.dismiss();
                    }
                });

                feedbackTelegram.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent telegramIntent = new Intent(Intent.ACTION_VIEW);
                        telegramIntent.setData(Uri.parse("http://telegram.me/code_aborigene"));
                        try {
                            startActivity(telegramIntent);
                        } catch (Exception e) {
                            Toast.makeText(getApplication(), R.string.toast_feedback_no_telegram, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                feedbackEmail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.setType("message/rfc822");
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.feedback_email)});
                        try {
                            startActivity(Intent.createChooser(emailIntent, getString(R.string.feedback_chooser_message)));
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(getApplication(), R.string.toast_feedback_no_email, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialogFeedback.show();
                break;

            case R.id.support_developer:
                AlertDialog.Builder supportDialogBuilder = new AlertDialog.Builder(this);
                LayoutInflater inflaterSupport = getLayoutInflater();
                View dialogViewSupport = inflaterSupport.inflate(R.layout.dialog_support_developer_message, new LinearLayout(this));
                supportDialogBuilder.setView(dialogViewSupport);
                supportDialogBuilder.setCancelable(true);

                Button supportPositive = dialogViewSupport.findViewById(R.id.dialog_support_developer_button_paypal);
                Button supportNegative = dialogViewSupport.findViewById(R.id.dialog_support_developer_button_dismiss);

                final AlertDialog dialogSupport = supportDialogBuilder.create();

                supportPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent paypalIntent = new Intent(Intent.ACTION_VIEW);
                        paypalIntent.setData(Uri.parse("https://paypal.me/aolika"));
                        try {
                            startActivity(paypalIntent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });


               supportNegative.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       dialogSupport.cancel();
                   }
               });

                dialogSupport.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void setLocale(String language) {
        if (!language.equals(currentLanguage)) {
            Locale locale = new Locale(language);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration config = res.getConfiguration();

            if (Build.VERSION.SDK_INT >= 24) {
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