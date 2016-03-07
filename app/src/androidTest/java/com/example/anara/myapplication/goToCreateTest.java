package com.example.anara.myapplication;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;
import android.test.mock.MockApplication;
import android.test.mock.MockContext;
import android.test.suitebuilder.annotation.LargeTest;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import org.junit.*;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.anara.myapplication.getMotivated.getAppContext;
import static org.hamcrest.Matchers.containsString;

import android.support.test.InstrumentationRegistry;
import android.util.Log;

import java.util.concurrent.CountDownLatch;

/**
 * Created by anara on 3/2/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class goToCreateTest extends ApplicationTestCase<getMotivated> {

    @Rule
    public ActivityTestRule<uHomeActivity> activityRule = new ActivityTestRule(uHomeActivity.class);
    private boolean test;

    public goToCreateTest(){
        super(getMotivated.class);

    }

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        super.setContext(this.getContext());
        Firebase.setAndroidContext(getContext());
        Firebase ref = new Firebase("https://burning-fire-7007.firebaseio.com");
       ref.authWithPassword("dummy@test.edu", "testit", new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                System.out.println("Test is true!!");
                System.out.flush();
            }
            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                System.out.println("Test Error!:" + firebaseError);
                System.out.flush();
            }
        });
        Thread.sleep(60000);
    }

    @Test
    public void clickCreateButtonGoesToCreate() {
        activityRule.launchActivity(new Intent());
        if(test) {
            System.out.println("Test is TRUE");
            System.out.flush();
            onView(withId(R.id.create)).perform(click());
            onView(withId(R.id.TextViewTitle)).check(matches(withText("Create New Group")));

            onView(withId(R.id.EditTextName))
                    .perform(typeText("Espresso!"), closeSoftKeyboard());
            onView(withId(R.id.EditTextDesc))
                    .perform(typeText("Have a cup of Espresso."), closeSoftKeyboard());
            onView(withId(R.id.Submit)).perform(click());

            onView(withId(R.id.goal)).check(matches(withText(containsString("Espresso!"))));
        }
        else{
            System.out.println("Test FAIL.");
            System.out.flush();
            onView(withId(R.id.goal)).check(matches(withText(containsString("Espresso!"))));
        }
    }
}