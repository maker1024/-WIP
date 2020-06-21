package com.anights.wip;

import android.os.Build;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.anights.wip.loading.ActivitySplash;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ActivitySplashUITest {

    @Rule
    public ActivityScenarioRule<ActivitySplash> activitySplashRule =
            new ActivityScenarioRule<>(ActivitySplash.class);

    @Test
    public void activitySplashTest() {
        onView(withId(R.id.logo))
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.app_name)));
    }
}
