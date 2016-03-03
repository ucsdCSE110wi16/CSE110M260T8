package com.example.anara.myapplication;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.*;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by anara on 3/2/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class groupCreateTest {
    @Rule
    public ActivityTestRule<CreatGroupActivity> uActivityRule= new ActivityTestRule(CreatGroupActivity.class);

    @Test
    public void clickCreateButtonGoesToCreate() {
        onView(withId(R.id.Submit)).perform(click());
        onView(withId(R.id.goal)).check(matches(withText("CGroup Description")));
    }
}
