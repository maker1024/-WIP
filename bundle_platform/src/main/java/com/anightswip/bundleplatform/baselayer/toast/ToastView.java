package com.anightswip.bundleplatform.baselayer.toast;

import android.content.Context;
import android.view.Gravity;

import com.anightswip.bundleplatform.commonlib.utils.ToastUtil;

/**
 * Toast提示框
 */
public class ToastView {

    private static Context mContext;

    public static void init(Context context) {
        mContext = context.getApplicationContext();
    }

    public static void show(String msg) {
        if(mContext==null) return;
        ToastUtil.show(mContext, null, msg, Gravity.CENTER, 0, 0, 4000);
    }
}
