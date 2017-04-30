package com.project.one.team.musictheoryapp;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.view.ViewPager;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * <p>Espresso Test Class for testing UI elements on {@link com.project.one.team.musictheoryapp.ContentActivity ContentActivity}.</p>
 *
 * @author Team One
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class ContentActivityTest {

    @Rule
    public ActivityTestRule<ContentActivity> mActivityTestRule = new ActivityTestRule<ContentActivity>(ContentActivity.class){
        // Use a customised test rule to launch the ContentActivity with the correct intent.
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

            Intent contentIntent = new Intent(targetContext, ContentActivity.class);
            contentIntent.putExtra(BasicSelectActivityV2.EXTRA_TOPIC, "basic/content/intro");
            return contentIntent;
        }
    };

    @Test
    public void quizButtonShouldOpenQuiz() {
        // Get number of pages in order to swipe to final page.
        ViewPager vp = (ViewPager)(mActivityTestRule.getActivity()).findViewById(R.id.viewPager);
        vp.getAdapter().getCount();

        while(vp.getCurrentItem() != vp.getAdapter().getCount() - 1) {
            onView(withId(R.id.viewPager)).perform(swipeLeft());
        }

        // There is apparently a delay in opening the quiz that Espresso cannot wait for automatically.
        try {
            Thread.sleep(450);
        } catch (InterruptedException ex) {
            Assert.fail("Thread.sleep() Interrupted!\n" + ex.getMessage());
        }

        onView(withId(R.id.quizButton))
                .perform(click());

        onView(withId(R.id.marks))
                .check(matches(withText("Marks: 0")));
    }


}
