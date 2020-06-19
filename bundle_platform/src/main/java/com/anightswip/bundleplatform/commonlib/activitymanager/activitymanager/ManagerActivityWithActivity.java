package com.anightswip.bundleplatform.commonlib.activitymanager.activitymanager;

import android.app.Activity;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * author : sangyi475
 * e-mail : SANGYI475@pingan.com.cn
 * desc   :
 * <p>
 * 用在Activity中的Lifecycle，监听create和destory事件，add和remove此Acitvity到ManagerActivity中
 */
class ManagerActivityWithActivity implements LifecycleObserver {

    ManagerActivityWithActivity(Lifecycle lifecycle) {
        lifecycle.addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onContextStop(LifecycleOwner source) {
        if (source instanceof Activity) {
            ManagerActivity.getInstance().add((Activity) source);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onContextDestroy(LifecycleOwner source) {
        if (source instanceof Activity) {
            ManagerActivity.getInstance().remove((Activity) source);
        }
        source.getLifecycle().removeObserver(this);
    }
}
