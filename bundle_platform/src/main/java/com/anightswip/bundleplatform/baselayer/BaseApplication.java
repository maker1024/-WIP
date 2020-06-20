package com.anightswip.bundleplatform.baselayer;

import android.app.Application;

import com.anightswip.bundleplatform.baselayer.toast.ToastView;
import com.anightswip.bundleplatform.commonlib.activitymanager.ManagerActivity;


public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        ToastView.init(getApplicationContext());
        ManagerActivity.initInApp();
    }
}
