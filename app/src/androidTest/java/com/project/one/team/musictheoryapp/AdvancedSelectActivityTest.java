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
 * <p>Espresso Test Class for testing UI elements on {@link AdvancedSelectActivity}.</p>
 *
 * <h3>NOTE:</h3>
 * <p>ScrollTo actions are finicky for testing Android versions lower than 7.1.
 * Many of the click action may fail if the signed in user does not have all topics unlocked.</p>
 *
 * @author Team One
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class AdvancedSelectActivityTest {

    @Rule
    public ActivityTestRule<AdvancedSelectActivity> mActivityTestRule = new ActivityTestRule<>(AdvancedSelectActivity.class);

    @Test
    public void canNavigateToHarmoniesTopicPage() {
        onView(withId(R.id.harmoniesLayout))
                .perform(scrollTo(), click());

        onView(allOf(withId(R.id.titleTextView), isDisplayed()))
                .check(matches(withText("Harmonies")));
    }

}
