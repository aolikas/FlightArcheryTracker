package com.example.flightarcherytracker.fragments;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.flightarcherytracker.MainActivity;
import com.example.flightarcherytracker.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class TrainingDeleteAlertDialog {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);
    private MainActivity mActivity;

    @Before
    public void init() {
        mActivity = mActivityTestRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    @Test
    public void testCheckDialogDisplayed() {

        onView(withText("Records"))
                .perform(ViewActions.click());

        onView(withId(R.id.fragment_records_rv)).perform(
                RecyclerViewActions.actionOnItemAtPosition
                        (0, MyViewAction.clickChildViewWithId(R.id.iv_training_delete)));

        onView(withText(R.string.alert_dialog_delete_title))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));
    }

    @Test
    public void testClickYesButton() {

        onView(withText("Records"))
                .perform(ViewActions.click());

        onView(withId(R.id.fragment_records_rv)).perform(
                RecyclerViewActions.actionOnItemAtPosition
                        (0, MyViewAction.clickChildViewWithId(R.id.iv_training_delete)));


        onView(withText(R.string.alert_dialog_delete_positive))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());
    }

    @Test
    public void testClickNoButton() {

        onView(withText("Records"))
                .perform(ViewActions.click());

        onView(withId(R.id.fragment_records_rv)).perform(
                RecyclerViewActions.actionOnItemAtPosition
                        (0, MyViewAction.clickChildViewWithId(R.id.iv_training_delete)));

        onView(withText(R.string.alert_dialog_delete_negative))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());
    }

    @After
    public void tearDown() {
        mActivity = null;
    }
}
