package com.example.anara.myapplication;


import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import org.junit.*;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.startsWith;

/**
 * Created by anara on 3/2/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class goToSearchTest extends ApplicationTestCase<getMotivated> {

    private final long DEFAULT_WAKE_LOCK_DURATION = 30 * 1000;

    @Rule
    public ActivityTestRule<uHomeActivity> activityRule = new ActivityTestRule<>(uHomeActivity.class,true,false);

    public  goToSearchTest(){
        super(getMotivated.class);
    }

    protected long keepDeviceAwakeForAtLeastMilliseconds() {
        return DEFAULT_WAKE_LOCK_DURATION;
    }

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        Context content = InstrumentationRegistry.getTargetContext().getApplicationContext();
        super.setContext(content);
        PowerManager pm = (PowerManager) getInstrumentation().getTargetContext().getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, SignupTest.class.getSimpleName());
        wakeLock.acquire(keepDeviceAwakeForAtLeastMilliseconds());
        Firebase.setAndroidContext(getContext());
        Firebase ref = new Firebase("https://burning-fire-7007.firebaseio.com");
        ref.authWithPassword("dummy@test.edu", "testit", new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                System.out.println("Authenticated Successfully!!");
                System.out.flush();
            }
            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                System.out.println("Authentication Error!:" + firebaseError);
                System.out.flush();
            }
        });
        Thread.sleep(3000);
        activityRule.launchActivity(new Intent());
    }


    @Test
    public void clickSearchButtonGoesToSearch() {
        onView(withId(R.id.searchG)).perform(click());
        onView(withId(R.id.txtsearch)).check(matches(withHint("Search")));

        onView(withId(R.id.txtsearch))
                    .perform(typeText("tupac"));

        onView(withId(R.id.txtsearch))
                    .perform(pressKey(KeyEvent.KEYCODE_ENTER));
        try { //Sleep so resluts can populate
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.txtsearch))
                    .perform(closeSoftKeyboard());

        onData(hasToString(startsWith("tupac")))
                .inAdapterView(withId(R.id.listview)).atPosition(0)
                .perform(click());

        onView(withId(R.id.goal)).check(matches(withText(containsString("tupac"))));

        pressBack();
        pressBack();

        onView(withId(R.id.logout)).perform(click());
    }
}
