package com.project.one.team.musictheoryapp;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.filters.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
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
        onView(allOf(withId(R.id.basic), isDisplayed()))
                .check(matches(isDisplayed()));
        // Intermediate Button
        onView(allOf(withId(R.id.intermediate), isDisplayed()))
                .check(matches(isDisplayed()));
        // Advanced Button
        onView(allOf(withId(R.id.advanced), isDisplayed()))
                .check(matches(isDisplayed()));
        // Settings Button
        onView(allOf(withId(R.id.settingsCog), isDisplayed()))
                .check(matches(isDisplayed()));
        // Piano Roll Button
        onView(allOf(withId(R.id.pianoRollBtn), isDisplayed()))
                .check(matches(isDisplayed()));
        // Main Logo
        onView(allOf(withId(R.id.main_Logo), isDisplayed()))
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
        onView(allOf(withClassName(Matchers.equalTo(Piano.class.getName())), isDisplayed()))
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
        onView(allOf(withId(R.id.nightModeToggle), isDisplayed()))
                .check(matches(isDisplayed()));
    }
}
