package com.anightswip.bundleplatform.baselayer;

import android.app.Application;

import com.anightswip.bundleplatform.baselayer.network.HttpResponseParser;
import com.anightswip.bundleplatform.baselayer.toast.ToastView;
import com.anightswip.bundleplatform.commonlib.activitymanager.ManagerActivity;
import com.anightswip.bundleplatform.commonlib.network.ApiManager;


public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        ToastView.init(getApplicationContext());
        ManagerActivity.initInApp();
        ApiManager.getInstance().init(new HttpResponseParser());
    }
}
