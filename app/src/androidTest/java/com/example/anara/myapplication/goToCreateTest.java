package com.example.anara.myapplication;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

import com.firebase.client.Firebase;

import org.junit.*;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import android.support.test.InstrumentationRegistry;
/**
 * Created by anara on 3/2/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class goToCreateTest extends ActivityInstrumentationTestCase2<uHomeActivity> {

    private uHomeActivity uActivity;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        uActivity = getActivity();
    }

    public goToCreateTest() {
        super(uHomeActivity.class);
    }

    @Test
    public void clickCreateButtonGoesToCreate() {
        Firebase.setAndroidContext(uActivity);
        onView(withId(R.id.create)).perform(click());
        onView(withId(R.id.TextViewTitle)).check(matches(withText("Create New Group")));
    }
}