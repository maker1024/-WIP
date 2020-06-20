package com.anightswip.bundleplatform.commonlib.executor;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;

/**
 * ui线程execute形式工具类
 */
public class UIThreadExecutor implements Executor {

    private Handler uiThreadHandler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(@NonNull Runnable command) {
        uiThreadHandler.post(command);
    }
}
