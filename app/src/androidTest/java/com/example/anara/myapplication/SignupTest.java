package com.example.anara.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.*;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by anara on 3/3/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SignupTest extends ApplicationTestCase<getMotivated> {

    private final long DEFAULT_WAKE_LOCK_DURATION = 30 * 1000;

    @Rule
    public ActivityTestRule<StartActivity> activityRule = new ActivityTestRule<>(StartActivity.class, true, false);

    public SignupTest(){
        super(getMotivated.class);
    }

    protected long keepDeviceAwakeForAtLeastMilliseconds() {
        return DEFAULT_WAKE_LOCK_DURATION;
    }

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        activityRule.launchActivity(new Intent());
        Context content = InstrumentationRegistry.getTargetContext().getApplicationContext();
        super.setContext(content);
        PowerManager pm = (PowerManager) getInstrumentation().getTargetContext().getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, SignupTest.class.getSimpleName());
        wakeLock.acquire(keepDeviceAwakeForAtLeastMilliseconds());
    }

    /* !! Be sure to delete signup@test.com after test fore reuse !! */
    @Test
    public void testSignUpNewUser() {

        onView(withText("Sign Up")).check(matches(isClickable())).perform(click());

        onView(withId(R.id.TextViewTitle)).check(matches(withText("New User Sign Up")));

        onView(withId(R.id.EditTextEmail))
                  .perform(typeText("signup@test.com"), closeSoftKeyboard());

        onView(withId(R.id.EditTextPass))
                  .perform(typeText("signup"), closeSoftKeyboard());

        onView(withId(R.id.EditTextPass1))
                  .perform(typeText("signup"), closeSoftKeyboard());

        onView(withId(R.id.Submit))
                  .perform(click(click()));

        onView(withId(R.id.email_sign_in_button)).check(matches(withText("Login")));

        onView(withId(R.id.email))
                  .perform(typeText("signup@test.com"), closeSoftKeyboard());

        onView(withId(R.id.password))
                  .perform(typeText("signup"), closeSoftKeyboard());

        onView(withId(R.id.email_sign_in_button))
                  .perform(click(click()));

        onView(withId(R.id.create)).check(matches(withText("Create a Group")));

        onView(withId(R.id.logout))
                  .perform(click());
    }


}
