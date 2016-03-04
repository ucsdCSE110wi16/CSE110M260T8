package com.example.anara.myapplication;


import android.support.test.runner.AndroidJUnit4;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.LargeTest;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import org.junit.*;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;

/**
 * Created by anara on 3/2/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class goToSearchTest extends ApplicationTestCase<getMotivated> {

    private boolean test;

    public goToSearchTest() {
        super(getMotivated.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        createApplication();
        Firebase.setAndroidContext(getContext());
        Firebase ref = new Firebase("https://burning-fire-7007.firebaseio.com");
        ref.authWithPassword("dummy@test.edu", "testit", new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                test = true;
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                System.out.println("Test Error!:" + firebaseError);
            }
        });
    }


    @Test
    public void clickSearchButtonGoesToSearch() {
        if(test) {
            onView(withId(R.id.searchG)).perform(click());
            onView(withId(R.id.txtsearch)).check(matches(withHint("Search")));

            onView(withId(R.id.txtsearch))
                    .perform(typeText("tupac"), closeSoftKeyboard()).perform(pressKey(13));

            onView(withId(R.id.listview)).check(matches(withText(containsString("tupac"))));
        }
    }
}
