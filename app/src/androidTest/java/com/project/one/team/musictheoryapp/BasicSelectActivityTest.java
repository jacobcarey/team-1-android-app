package com.project.one.team.musictheoryapp;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Espresso Test Class for testing UI elements on {@link BasicSelectActivityV2 BasicSelectActivityV2}.
 * NOTE:
 * ScrollTo actions are finicky for testing Android versions lower than 7.1.
 * Many of the click action may fail if the signed in user does not have all topics unlocked.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class BasicSelectActivityTest {

    @Rule
    public ActivityTestRule<BasicSelectActivityV2> mActivityTestRule = new ActivityTestRule<>(BasicSelectActivityV2.class);

    @Test
    public void canNavigateToIntroTopicPage() {
        onView(withId(R.id.introLayout))
                .perform(scrollTo(), click());

        onView(allOf(withId(R.id.titleTextView), isDisplayed()))
                .check(matches(withText("Intro to music theory")));
    }

    @Test
    public void canNavigateToMusicNotesTopicPage() {
        onView(withId(R.id.mnotesLayout))
                .perform(scrollTo(), click());

        onView(allOf(withId(R.id.titleTextView), isDisplayed()))
                .check(matches(withText("Musical notes and how to find them")));
    }

    @Test
    public void canNavigateToSimpleNotesTopicPage() {
        onView(withId(R.id.smpnotelenLayout))
                .perform(scrollTo(), click());

        onView(allOf(withId(R.id.titleTextView), isDisplayed()))
                .check(matches(withText("Simple Note Lengths")));
    }

    @Test
    public void canNavigateToAdvancedNotesTopicPage() {
        onView(withId(R.id.advnotelenLayout))
                .perform(scrollTo(), click());

        onView(allOf(withId(R.id.titleTextView), isDisplayed()))
                .check(matches(withText("Advanced Note Lengths")));
    }

    @Test
    public void canNavigateToMajorScaleTopicPage() {
        onView(withId(R.id.scaleconmajLayout))
                .perform(scrollTo(), click());

        onView(allOf(withId(R.id.titleTextView), isDisplayed()))
                .check(matches(withText("Scale Construction")));
    }

    @Test
    public void canNavigateToMinorScaleTopicPage() {
        onView(withId(R.id.scaleconminLayout))
                .perform(scrollTo(), click());

        onView(allOf(withId(R.id.titleTextView), isDisplayed()))
                .check(matches(withText("Scale Construction")));
    }
}
