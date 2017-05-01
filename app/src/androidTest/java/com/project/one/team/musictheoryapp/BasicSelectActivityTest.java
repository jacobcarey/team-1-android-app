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
 * <p>Espresso Test Class for testing UI elements on {@link BasicSelectActivityV2}.</p>
 *
 * <h3>NOTE:</h3>
 * <p>ScrollTo actions are finicky for testing Android versions lower than 7.1.
 * Many of the click action may fail if the signed in user does not have all topics unlocked.</p>
 *
 * @author Team One
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
        onView(withId(R.id.notelengthsLayout))
                .perform(scrollTo(), click());

        onView(allOf(withId(R.id.titleTextView), isDisplayed()))
                .check(matches(withText("Simple Note Lengths")));
    }

    @Test
    public void canNavigateToSheetMusicTopicPage() {
        onView(withId(R.id.sheetmusicLayout))
                .perform(scrollTo(), click());

        onView(allOf(withId(R.id.titleTextView), isDisplayed()))
                .check(matches(withText("Sheet Music")));
    }

    @Test
    public void canNavigateToNotePitchTopicPage() {
        onView(withId(R.id.notepitchLayout))
                .perform(scrollTo(), click());

        onView(allOf(withId(R.id.titleTextView), isDisplayed()))
                .check(matches(withText("Note Pitch")));
    }

    @Test
    public void canNavigateToTimeSignaturesTopicPage() {
        onView(withId(R.id.tsignaturesLayout))
                .perform(scrollTo(), click());

        onView(allOf(withId(R.id.titleTextView), isDisplayed()))
                .check(matches(withText("Time Signatures")));
    }

}
