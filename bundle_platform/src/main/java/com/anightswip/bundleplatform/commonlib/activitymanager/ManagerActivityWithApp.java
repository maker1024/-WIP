package com.anightswip.bundleplatform.commonlib.activitymanager;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

/**
 * 用在Application中的Lifecycle，App的destory事件，销毁所有的Activity
 */
class ManagerActivityWithApp implements LifecycleObserver {

    private static ManagerActivityWithApp mInstance;

    private ManagerActivityWithApp() {
    }

    static void init() {
        try {
            if (mInstance == null) {
                mInstance = new ManagerActivityWithApp();
            } else {
                ProcessLifecycleOwner.get().getLifecycle().removeObserver(mInstance);
            }
            ProcessLifecycleOwner.get().getLifecycle().addObserver(mInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onAppStop(LifecycleOwner source) {
        ManagerActivity.getInstance().exit();
    }
}
