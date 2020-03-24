package com.example.flightarcherytracker.fragments;

import android.app.Instrumentation;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import com.example.flightarcherytracker.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TrainingSessionMapTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);
    private UiDevice mDevice;


    @Before
    public void setUp() {
        mActivityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction();
        final Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        mDevice = UiDevice.getInstance(instrumentation);

    }

    @Test
    public void mapTest() {

        mDevice.wait(Until.hasObject(By.desc("Map is ready")), 5000);

        UiObject marker = mDevice.findObject(new UiSelector()
                .descriptionContains("You current position"));
        UiObject markerStart = mDevice.findObject(new UiSelector()
        .descriptionContains("Start"));

        try{
            marker.click();
            markerStart.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

        mDevice.click(52, 13);
    }

}
