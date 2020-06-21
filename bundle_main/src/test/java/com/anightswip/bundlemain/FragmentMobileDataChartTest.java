package com.anightswip.bundlemain;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class FragmentMobileDataChartTest {

    @Test
    public void uiChartTest() throws Exception {
        FragmentScenario fs = FragmentScenario.launchInContainer(FragmentMobileDataChart.class);
        fs.moveToState(Lifecycle.State.CREATED);
        fs.moveToState(Lifecycle.State.STARTED);
        fs.moveToState(Lifecycle.State.RESUMED);
        onView(withId(R.id.lineChart)).check(matches(isDisplayed()));
    }

}