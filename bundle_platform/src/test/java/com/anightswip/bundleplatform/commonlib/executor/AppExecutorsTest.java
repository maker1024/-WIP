package com.anightswip.bundleplatform.commonlib.executor;

import android.os.Build;

import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.concurrent.Executors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.O_MR1)
public class AppExecutorsTest {

    @Test
    public void appExecutorsTest() {

        assertThrows(IllegalStateException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                AppExecutors.uiThread();
            }
        });

        assertThrows(IllegalStateException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                AppExecutors.workThread();
            }
        });

        AppExecutors.init(Executors.newSingleThreadExecutor()
                , new UIThreadExecutor(RuntimeEnvironment.systemContext.getMainLooper()));
        AppExecutors.workThread().execute(new Runnable() {
            @Override
            public void run() {
                assertFalse(RuntimeEnvironment.isMainThread());
            }
        });

        AppExecutors.uiThread().execute(new Runnable() {
            @Override
            public void run() {
                assertTrue(RuntimeEnvironment.isMainThread());
            }
        });

        assertThrows(IllegalStateException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                AppExecutors.init(Executors.newSingleThreadExecutor()
                        , new UIThreadExecutor(RuntimeEnvironment.systemContext.getMainLooper()));
            }
        });

        assertThrows(IllegalStateException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                AppExecutors.init();
            }
        });

        assertTrue(AppExecutors.hasInit());
    }

}