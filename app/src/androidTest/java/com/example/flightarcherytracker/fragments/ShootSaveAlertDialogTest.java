package com.example.flightarcherytracker.fragments;

import android.app.AlertDialog;
import android.app.Application;

import androidx.annotation.ContentView;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.rule.ActivityTestRule;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import com.example.flightarcherytracker.MainActivity;
import com.example.flightarcherytracker.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ShootSaveAlertDialogTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void init() {
        activityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction();
    }

    @Test
    public void testCheckDialogDisplayed() {

        onView(withId(R.id.training_btn_start)).perform(click()).check(matches(isDisplayed()));
        onView(withId(R.id.training_btn_save)).check(matches(allOf(isDisplayed(), isClickable())));
        onView(withId(R.id.training_btn_save)).perform(click());

        onView(withText(R.string.shoot_message_dialog_title))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));
    }

    @Test
    public void testClickSaveButton() {

        onView(withId(R.id.training_btn_start)).perform(click()).check(matches(isDisplayed()));
        onView(withId(R.id.training_btn_save)).check(matches(allOf(isDisplayed(), isClickable())));
        onView(withId(R.id.training_btn_save)).perform(click());

        onView(withId(android.R.id.button1))
                .inRoot(isDialog())
                .check(matches(withText(R.string.shoot_message_dialog_positive)))
                .check(matches(isDisplayed()));
        onView(withId(android.R.id.button1)).perform(click());
    }

    @Test
    public void testClickCancelButton() {

        onView(withId(R.id.training_btn_start)).perform(click()).check(matches(isDisplayed()));
        onView(withId(R.id.training_btn_save)).check(matches(allOf(isDisplayed(), isClickable())));
        onView(withId(R.id.training_btn_save)).perform(click());

        onView(withId(android.R.id.button2))
                .inRoot(isDialog())
                .check(matches(withText(R.string.shoot_message_dialog_negative)))
                .check(matches(isDisplayed()));
        onView(withId(android.R.id.button2)).perform(click());
    }
}
