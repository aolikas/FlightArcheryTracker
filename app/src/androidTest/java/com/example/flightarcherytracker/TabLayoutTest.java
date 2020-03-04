package com.example.flightarcherytracker;

import androidx.annotation.UiThread;
import androidx.room.PrimaryKey;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.Espresso.onView;

import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;




@RunWith(AndroidJUnit4.class)
public class TabLayoutTest {

  private MainActivity mainActivity;

  @Rule
    public ActivityTestRule<MainActivity> mainActivityRule =
          new ActivityTestRule<>(MainActivity.class);

  @Before
    public void setUp() {
      mainActivity = mainActivityRule.getActivity();
      assertThat(mainActivity, notNullValue());
  }

  @Test
    public void swipePage() {
      onView(withId(R.id.activity_main_view_pager))
      .check(matches(isDisplayed()));

      onView(withId(R.id.activity_main_view_pager))
              .perform(swipeLeft());
  }

  @Test
    public void checkTabLayoutDisplayed() {
      onView(withId(R.id.activity_main_tab_layout))
              .perform(click())
              .check(matches(isDisplayed()));
  }

  @Test
    @UiThread
    public void checkTabSwitch() {
      onView(allOf(withText("test3"), isDescendantOfA(withId(R.id.activity_main_tab_layout))))
              .perform(click())
              .check(matches(isDisplayed()));
  }

}

