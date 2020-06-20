package com.anightswip.bundleplatform.baselayer;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.anightswip.bundleplatform.baselayer.page.GeneralPageManager;
import com.anightswip.bundleplatform.commonlib.activitymanager.ManagerActivity;
import com.anightswip.bundleplatform.commonlib.page.IPageAble;


public class BaseActivity extends AppCompatActivity implements IPageAble {

    protected Context mContext;
    private GeneralPageManager mPageManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        ManagerActivity.initInActivity(getLifecycle());
    }

    @Override
    public GeneralPageManager page() {
        if (mPageManager == null) {
            mPageManager = new GeneralPageManager(mContext);
        }
        return mPageManager;
    }

}
