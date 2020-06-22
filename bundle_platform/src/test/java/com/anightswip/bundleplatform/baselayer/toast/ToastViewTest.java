package com.anightswip.bundleplatform.baselayer.toast;

import android.os.Build;
import android.widget.TextView;

import com.anightswip.bundleplatform.R;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import java.util.Timer;
import java.util.TimerTask;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.O_MR1)
public class ToastViewTest {

    @Test
    public void showToastTest() {
        ActivityToastTest activity = Robolectric.setupActivity(ActivityToastTest.class);
        TextView logoTv = activity.findViewById(R.id.img);
        logoTv.performClick();
        assertEquals("success show", ShadowToast.getTextOfLatestToast());
    }

}