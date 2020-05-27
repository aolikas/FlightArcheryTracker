package com.example.flightarcherytracker;


import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;

import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.Espresso.onView;


import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;


@RunWith(AndroidJUnit4.class)
public class TabLayoutTest {

    private MainActivity mActivity;

    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        mActivity = mainActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    @Test
    public void swipePage() {
        onView(withId(R.id.view_pager_activity_main))
                .check(matches(isDisplayed()));

        onView(withId(R.id.view_pager_activity_main))
                .perform(swipeRight());
    }

    @Test
    public void checkTabLayoutDisplayed() {
        onView(withId(R.id.tab_layout_activity_main))
                .perform(click())
                .check(matches(isDisplayed()));
    }

    @After
    public void tearDown() {
        mActivity = null;
    }

}