package com.anightswip.bundleplatform.baselayer.toast;

import android.content.Context;
import android.view.Gravity;

import com.anightswip.bundleplatform.commonlib.utils.ToastUtil;

import java.lang.ref.WeakReference;

/**
 * Toast提示框
 */
public class ToastView {

    private static WeakReference<Context> mContext;

    public static void init(Context context) {
        mContext = new WeakReference<>(context.getApplicationContext());
    }

    public static void show(String msg) {
        if (mContext == null || mContext.get() == null) return;
        ToastUtil.show(
                mContext.get(),
                null, msg,
                Gravity.CENTER,
                0,
                0,
                4000);
    }
}
