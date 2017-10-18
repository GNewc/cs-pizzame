package com.newcomb.pizzame.view;


import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.location.Location;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.google.android.gms.location.LocationServices;
import com.newcomb.pizzame.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public IntentsTestRule<MainActivity> _intentsTestRule = new IntentsTestRule<>(MainActivity.class);

    @Before
    public void grantPermission() {
        getInstrumentation().getUiAutomation().executeShellCommand(
                "pm grant " + getTargetContext().getPackageName()
                        + " android.permission.ACCESS_FINE_LOCATION");
    }

    @Before
    public void stubAllExternalIntents() {
        intending(not(isInternal()))
                .respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }

    @Before
    public void setLocation() {
        Location location = new Location(MainActivityTest.class.getSimpleName());
        location.setLatitude(30.39537);
        location.setLongitude(-97.74545);
        location.setSpeed(0.0f);
        location.setBearing(0.0f);
        location.setAltitude(0.0);
        location.setTime(System.currentTimeMillis());
        location.setElapsedRealtimeNanos(1);
        location.setAccuracy(0.1f);
        LocationServices.getFusedLocationProviderClient(_intentsTestRule.getActivity())
                .setMockMode(true);
        LocationServices.getFusedLocationProviderClient(_intentsTestRule.getActivity())
                .setMockLocation(location);
    }

    @Test
    public void mainActivityTest() {
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.action_refresh), withText("Refresh"),
                      childAtPosition(
                              childAtPosition(
                                      withId(R.id.toolbar),
                                      1),
                              0),
                      isDisplayed()));
        actionMenuItemView.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction recyclerView = onView(withId(R.id.options_recycler_view));
        recyclerView.perform(actionOnItemAtPosition(1, click()));

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.map_button), withText("Map"),
                      childAtPosition(
                              childAtPosition(
                                      withClassName(is("android.widget.LinearLayout")),
                                      4),
                              1),
                      isDisplayed()));
        appCompatButton.perform(click());

        intended(allOf(
                IntentMatchers.hasAction(equalTo(Intent.ACTION_VIEW)),
                IntentMatchers.toPackage("com.google.android.apps.maps")));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.call_button), withText("Call"),
                      childAtPosition(
                              childAtPosition(
                                      withClassName(is("android.widget.LinearLayout")),
                                      4),
                              0),
                      isDisplayed()));
        appCompatButton2.perform(click());

        intended(allOf(
                IntentMatchers.hasAction(equalTo(Intent.ACTION_VIEW)),
                IntentMatchers.toPackage("com.android.dialer")));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pressBack();

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
