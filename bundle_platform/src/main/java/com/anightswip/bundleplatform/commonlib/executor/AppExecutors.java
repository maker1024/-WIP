package com.anightswip.bundleplatform.commonlib.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * app的Executor
 * 目前分UI和工作者线程
 */
public class AppExecutors {

    private static Executor WORK_THREAD;
    private static Executor UI_THREAD;

    public static void init(Executor workThread, Executor uiThread) {
        if (WORK_THREAD != null || UI_THREAD != null) {
            throw new IllegalStateException("call init() only once!");
        }
        WORK_THREAD = workThread;
        UI_THREAD = uiThread;
    }

    public static void init() {
        if (WORK_THREAD != null || UI_THREAD != null) {
            throw new IllegalStateException("call init() only once!");
        }
        WORK_THREAD = Executors.newSingleThreadExecutor();
        UI_THREAD = new UIThreadExecutor();
    }

    public static Executor workThread() {
        if (WORK_THREAD == null) {
            throw new IllegalStateException("call init() first!");
        }
        return WORK_THREAD;
    }

    public static Executor uiThread() {
        if (UI_THREAD == null) {
            throw new IllegalStateException("call init() first!");
        }
        return UI_THREAD;
    }

    public static boolean hasInit() {
        return WORK_THREAD != null && UI_THREAD != null;
    }
}
