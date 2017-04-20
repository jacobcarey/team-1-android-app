package com.project.one.team.musictheoryapp;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Espresso Test Class for testing UI elements on {@link com.project.one.team.musictheoryapp.QuizActivity QuizActivity}.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class QuizActivityTest {
    @Rule
    public ActivityTestRule<QuizActivity> mActivityTestRule = new ActivityTestRule<QuizActivity>(QuizActivity.class){
        // Use a customised test rule to launch the QuizActivity with the correct intent.
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

            Intent quizIntent = new Intent(targetContext, QuizActivity.class);
            quizIntent.putExtra(QuizActivity.EXTRA_TOPIC, "basic/quiz/intro");
            return quizIntent;
        }
    };

    @Test
    public void canNavigateThroughEntireQuiz() {
        View returnButton = (mActivityTestRule.getActivity()).findViewById(R.id.returnText);

        // Until we reach the last page with the return button, keep selecting the first option.
        while (returnButton.getVisibility() != View.VISIBLE) {
            onView(withId(R.id.answer1Text))
                    .perform(click());
        }

        onView(withId(R.id.marks))
                .check(matches(isDisplayed()));
    }
}
