package com.anights.wip;

import android.content.Intent;
import android.os.Build;
import android.widget.TextView;

import com.anights.wip.frame.ActivityFrame;
import com.anights.wip.loading.ActivitySplash;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.util.Scheduler;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.O_MR1)
public class ActivitySplashTest {

    @Test
    public void uiSplashTest() throws Exception {

        ActivitySplash activity = Robolectric.setupActivity(ActivitySplash.class);
        TextView logoTv = activity.findViewById(R.id.logo);
        assertEquals("wip",
                logoTv.getText().toString());

        Scheduler scheduler = Robolectric.getForegroundThreadScheduler();
        while (scheduler.size() == 0) {
            Thread.sleep(500);
        }
        scheduler.runOneTask();
        Intent expectedIntent = new Intent(activity, ActivityFrame.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }
}