package com.example.flightarcherytracker.fragments;

import android.view.View;
import android.widget.RelativeLayout;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.flightarcherytracker.R;
import com.example.flightarcherytracker.TestActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Objects;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class TrainingsSessionFragmentTest {

    @Rule
    public ActivityTestRule<TestActivity> mActivityTest =
            new ActivityTestRule<>(TestActivity.class);

    private TestActivity mActivity = null;


    @Before
    public void setUp() {
        mActivity = mActivityTest.getActivity();

    }

    @Test
    public void testLaunch() {
        RelativeLayout rlContainer = mActivity.findViewById(R.id.test_container);

        assertNotNull(rlContainer);

        TrainingsSessionFragment fragment = new TrainingsSessionFragment();

        mActivity.getSupportFragmentManager().beginTransaction().add(rlContainer.getId(), fragment)
                .commitAllowingStateLoss();
        //test if the fragment is or not

        getInstrumentation().waitForIdleSync();
        View buttonStart = fragment.getView().findViewById(R.id.training_btn_start);
        View buttonSave = fragment.getView().findViewById(R.id.training_btn_save);

        assertNotNull(buttonStart);
        assertNotNull(buttonSave);
    }

    @After
    public void tearDown() {
        mActivity = null;
    }
}