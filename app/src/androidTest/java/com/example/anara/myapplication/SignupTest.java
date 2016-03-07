package com.example.anara.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.*;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;

/**
 * Created by anara on 3/3/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SignupTest extends ApplicationTestCase<getMotivated> {


    @Rule
    public ActivityTestRule<StartActivity> activityRule = new ActivityTestRule(StartActivity.class, true, false);

    public SignupTest(){
        super(getMotivated.class);

    }

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        Context content = InstrumentationRegistry.getTargetContext();;
        super.setContext(content);
    }

    @Test
    public void testSignUpNewUser() {

        activityRule.launchActivity(new Intent());

        onView(withText("Sign Up")).check(matches(isClickable())).perform(click());

        onView(withId(R.id.TextViewTitle)).check(matches(withText("New User Sign Up")));

        onView(withId(R.id.EditTextEmail))
                  .perform(typeText("signup@test.com"), closeSoftKeyboard());

        onView(withId(R.id.EditTextPass))
                  .perform(typeText("signup"), closeSoftKeyboard());

        onView(withId(R.id.EditTextPass1))
                  .perform(typeText("signup"), closeSoftKeyboard());

        onView(withId(R.id.Submit)).perform(click());

        onView(withId(R.id.email)).check(matches(withHint("Email")));

    }


}
