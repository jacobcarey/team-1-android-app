package com.project.one.team.musictheoryapp;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * <p>Espresso Test Class for testing Firebase authentication.</p>
 *
 * @author Team One
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class AuthenticationTest {

    @Rule
    public ActivityTestRule<MainPageActivity> mActivityTestRule = new ActivityTestRule<>(MainPageActivity.class);

    @Before
    public void setUp() {
        // Before each test, go to the settings and ensure that we are signed out.
        onView(allOf(withId(R.id.settingsCog), isDisplayed()))
                .perform(click());
        try {
            onView(allOf(withText("Sign Out"), isDisplayed()))
                    .perform(click());
        } catch (NoMatchingViewException ex) {
            Log.d("AuthenticationTest", "User already signed out.");
        }
    }

    @Test
    public void canLogIntoApp() {
        String testEmail = "webtest@test.com";
        String testPassword = "test123";

        onView(allOf(withId(R.id.userEmail), isDisplayed()))
            .perform(click(), typeText(testEmail), closeSoftKeyboard());
        onView(allOf(withId(R.id.userPass), isDisplayed()))
                .perform(click(), typeText(testPassword), closeSoftKeyboard());
        onView(allOf(withText("Sign In"), isDisplayed()))
                .perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Assert.fail("Thread.sleep() Interrupted!\n" + ex.getMessage());
        }
        pressBack();
        onView(withText("Logged in as: WebTest"))
                .check(matches(isDisplayed()));
    }

    @Test
    public void canCreateAccount() {
        Random r = new Random();
        String testUsername = "espressotest" + r.nextDouble();
        String testEmail = "espressotest" + r.nextDouble() + "@test.com";
        String testPassword = "test123";

        onView(allOf(withText("Sign Up"), isDisplayed()))
                .perform(click());

        onView(allOf(withId(R.id.userName), isDisplayed()))
                .perform(click(), typeText(testUsername), closeSoftKeyboard());
        onView(allOf(withId(R.id.userEmail), isDisplayed()))
                .perform(click(), typeText(testEmail), closeSoftKeyboard());
        onView(allOf(withId(R.id.userPass), isDisplayed()))
                .perform(click(), typeText(testPassword), closeSoftKeyboard());
        onView(allOf(withText("Sign Up"), isDisplayed()))
                .perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Assert.fail("Thread.sleep() Interrupted!\n" + ex.getMessage());
        }
        pressBack();
        onView(withText("Logged in as: " + testUsername))
                .check(matches(isDisplayed()));
    }
}
