package com.example.flightarcherytracker.fragments;


import androidx.recyclerview.widget.RecyclerView;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.flightarcherytracker.MainActivity;
import com.example.flightarcherytracker.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Objects;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class RecyclerViewRecordsTest {

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
    public void clickPositionTest() {

        onView(withId(R.id.container_training))
                .check(matches(isDisplayed()));

        onView(withId(R.id.btn_start_training))
                .check(matches(isDisplayed()));

        onView(withId(R.id.view_pager_activity_main))
                .check(matches(isDisplayed()));

        onView(withText("Records"))
                .perform(ViewActions.click());

        onView(allOf(
                withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
                withId(R.id.fragment_records_rv)))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @Test
    public void clickShowShotsTest() {

        onView(withText("Records"))
                .perform(ViewActions.click());

        onView(allOf(
                withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
                withId(R.id.fragment_records_rv)))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.tv_training_show_shots)));

    }

    @Test
    public void clickShowMapShotsTest() {
        onView(withText("Records"))
                .perform(ViewActions.click());

        onView(allOf(
                withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
                withId(R.id.fragment_records_rv)))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.iv_training_show_map_shots)));
    }

    @Test
    public void clickDeleteTrainingTest() {
        onView(withText("Records"))
                .perform(ViewActions.click());
        onView(allOf(
                withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
                withId(R.id.fragment_records_rv)))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.iv_training_delete)));

    }


    @Test
    public void scrollToEnd() {

        RecyclerView recyclerView = mActivityTestRule.getActivity().findViewById(R.id.fragment_records_rv);
        int itemCount = Objects.requireNonNull(recyclerView.getAdapter()).getItemCount();

        onView(withText("Records"))
                .perform(ViewActions.click());

        onView(withId(R.id.fragment_records_rv))
                .perform(RecyclerViewActions.scrollToPosition(itemCount - 1));

    }

    @After
    public void tearDown() {
        mActivity = null;
    }
}