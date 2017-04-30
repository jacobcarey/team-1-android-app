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
 * <p>Espresso Test Class for testing UI elements on {@link IntermediateSelectActivity}.</p>
 *
 * <h3>NOTE:</h3>
 * <p>ScrollTo actions are finicky for testing Android versions lower than 7.1.
 * Many of the click action may fail if the signed in user does not have all topics unlocked.</p>
 *
 * @author Team One
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class IntermediateSelectActivityTest {

    @Rule
    public ActivityTestRule<IntermediateSelectActivity> mActivityTestRule = new ActivityTestRule<>(IntermediateSelectActivity.class);

    @Test
    public void canNavigateToMajorScaleConstructionTopicPage() {
        onView(withId(R.id.scaleconmajLayout))
                .perform(scrollTo(), click());

        onView(allOf(withId(R.id.titleTextView), isDisplayed()))
                .check(matches(withText("Scale Construction")));
    }

    @Test
    public void canNavigateToMinorScaleConstructionTopicPage() {
        onView(withId(R.id.scaleconminLayout))
                .perform(scrollTo(), click());

        onView(allOf(withId(R.id.titleTextView), isDisplayed()))
                .check(matches(withText("Scale Construction")));
    }

    @Test
    public void canNavigateToChordConstructionTopicPage() {
        onView(withId(R.id.cconstructionLayout))
                .perform(scrollTo(), click());

        onView(allOf(withId(R.id.titleTextView), isDisplayed()))
                .check(matches(withText("Chord Construction")));
    }

}
