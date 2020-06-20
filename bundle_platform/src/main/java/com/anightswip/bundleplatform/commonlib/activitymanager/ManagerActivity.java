package com.anightswip.bundleplatform.commonlib.activitymanager;

import android.app.Activity;

import androidx.lifecycle.Lifecycle;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity的管理
 * 用链表存储Activity的事例，提供一些方法控制已存在的Activity
 */
public class ManagerActivity {

    private volatile static ManagerActivity mInstance;
    private List<WeakReference<Activity>> mActivityList;

    private ManagerActivity() {
        mActivityList = new ArrayList<>();
    }

    public static ManagerActivity getInstance() {
        if (mInstance == null) {
            synchronized (ManagerActivity.class) {
                if (mInstance == null) {
                    mInstance = new ManagerActivity();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化，用在Application中，提供当app退出时销毁所有的Activity的行为
     * 如果不想要此行为，则此初始化非必须
     */
    public static void initInApp() {
        ManagerActivityWithApp.init();
    }

    /**
     * 初始化，用在BaseActivity中
     */
    public static void initInActivity(Lifecycle lifecycle) {
        if (lifecycle == null) return;
        new ManagerActivityWithActivity(lifecycle);
    }

    /**
     * 增加一个activity记录
     * @param ac
     */
    void add(Activity ac) {
        if (mActivityList == null) return;
        mActivityList.add(new WeakReference<>(ac));
    }

    /**
     * 删除一个activity记录
     * @param ac
     */
    void remove(Activity ac) {
        if (mActivityList == null) return;
        WeakReference<Activity> wAcRemove = null;
        for (WeakReference<Activity> wAc : mActivityList) {
            if (wAc.get() != ac) {
                continue;
            }
            wAcRemove = wAc;
            break;
        }
        if (wAcRemove == null) return;
        mActivityList.remove(wAcRemove);
    }

    /**
     * 销毁所有的Activity
     */
    public void clearAll() {
        for (int i = 0; i < mActivityList.size(); i++) {
            Activity temp = mActivityList.get(i).get();
            if (temp != null && !temp.isFinishing()) {
                temp.finish();
            }
        }
    }

    /**
     * 销毁所有的Activity，并且把存储链表置空
     * 一般用在app退出时
     */
    public void exit() {
        clearAll();
        mInstance = null;
    }
}
