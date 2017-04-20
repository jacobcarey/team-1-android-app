package com.project.one.team.musictheoryapp;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.filters.LargeTest;

import org.hamcrest.Matchers;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Espresso Test Class for testing UI elements on {@link com.project.one.team.musictheoryapp.MainPageActivity MainPageActivity}.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainPageActivity> mActivityTestRule = new ActivityTestRule<>(MainPageActivity.class);

    @Test
    public void uiElementsShouldExist() {
        // Basic Button
        onView(withId(R.id.basic))
                .check(matches(isDisplayed()));
        // Intermediate Button
        onView(withId(R.id.intermediate))
                .check(matches(isDisplayed()));
        // Advanced Button
        onView(withId(R.id.advanced))
                .check(matches(isDisplayed()));
        // Settings Button
        onView(withId(R.id.settingsCog))
                .check(matches(isDisplayed()));
        // Piano Roll Button
        onView(withId(R.id.pianoRollBtn))
                .check(matches(isDisplayed()));
        // Main Logo
        onView(withId(R.id.main_Logo))
                .check(matches(isDisplayed()));
    }

    @Test
    public void basicButtonShouldNavigate() {
        onView(allOf(withId(R.id.basic), isDisplayed()))
                .perform(click());

        // Check if header text of the newly displayed activity reads "BASIC".
        onView(allOf(withId(R.id.headerText), isDisplayed()))
                .check(matches(withText("BASIC")));
    }

    @Test
    public void pianoButtonShouldNavigate() {
        onView(allOf(withId(R.id.pianoRollBtn), isDisplayed()))
                .perform(click());

        // Check if Piano view object exists.
        onView(withClassName(Matchers.equalTo(Piano.class.getName())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void logoShouldNavigate() {
        onView(allOf(withId(R.id.main_Logo), isDisplayed()))
                .perform(click());

        // Check if text of the displayed credits activity reads "Made by".
        onView(allOf(withId(R.id.textView), isDisplayed()))
                .check(matches(withText("Made by")));
    }

    @Test
    public void settingsButtonShouldNavigate() {
        onView(allOf(withId(R.id.settingsCog), isDisplayed()))
                .perform(click());

        // Check if the night mode button exists.
        onView(withId(R.id.nightModeToggle))
                .check(matches(isDisplayed()));
    }
}
