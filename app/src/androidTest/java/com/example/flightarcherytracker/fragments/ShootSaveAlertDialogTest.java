package com.example.flightarcherytracker.fragments;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;

import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import com.example.flightarcherytracker.MainActivity;
import com.example.flightarcherytracker.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ShootSaveAlertDialogTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void init() {
        mActivityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction();
    }

    @Test
    public void testCheckDialogDisplayed() {

        onView(withId(R.id.btn_start_training)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_start_training)).perform(click());

        onView(withId(R.id.btn_save_shoots)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_save_shoots)).perform(click());

        onView(withText(R.string.dialog_shoot_message_title))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));
    }

    @Test
    public void testClickSaveButton() {

        onView(withId(R.id.btn_start_training)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_start_training)).perform(click());

        onView(withId(R.id.btn_save_shoots)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_save_shoots)).perform(click());

        onView(withText(R.string.dialog_shoot_message_positive))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());
    }

    @Test
    public void testClickCancelButton() {

        onView(withId(R.id.btn_start_training)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_start_training)).perform(click());

        onView(withId(R.id.btn_save_shoots)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_save_shoots)).perform(click());

        onView(withText(R.string.dialog_shoot_message_negative))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());
    }
}
