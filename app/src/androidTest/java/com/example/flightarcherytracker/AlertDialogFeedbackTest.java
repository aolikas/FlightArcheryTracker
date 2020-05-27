package com.example.flightarcherytracker;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class AlertDialogFeedbackTest {

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
    public void testCheckDialogDisplayed() {

        try {
            onView(withId(R.id.feedback)).perform(click());
        } catch (NoMatchingViewException e) {
            openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
            onView(withText(R.string.menu_main_feedback)).perform(click());
        }

        onView(withText(R.string.alert_dialog_feedback_title))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));
    }

    @Test
    public void testClickEmailButton() {

        try {
            onView(withId(R.id.feedback)).perform(click());
        } catch (NoMatchingViewException e) {
            openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
            onView(withText(R.string.menu_main_feedback)).perform(click());
        }

        onView(withText(R.string.alert_dialog_feedback_button_email))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));
    }

    @Test
    public void testClickTelegramButton() {

        try {
            onView(withId(R.id.feedback)).perform(click());
        } catch (NoMatchingViewException e) {
            openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
            onView(withText(R.string.menu_main_feedback)).perform(click());
        }

        onView(withText(R.string.alert_dialog_feedback_button_telegram))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));
    }

    @Test
    public void testClickDismissButton() {

        try {
            onView(withId(R.id.feedback)).perform(click());
        } catch (NoMatchingViewException e) {
            openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
            onView(withText(R.string.menu_main_feedback)).perform(click());
        }

        onView(withText(R.string.alert_dialog_feedback_button_dismiss))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));
    }

    @After
    public void tearDown() {
        mActivity = null;
    }
}
