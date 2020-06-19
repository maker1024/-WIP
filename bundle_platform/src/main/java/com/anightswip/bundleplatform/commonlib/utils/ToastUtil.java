package com.anightswip.bundleplatform.commonlib.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

/**
 * android Toast的工具类，增强了自定义显示的duration
 * 系统自带Toast长显示为7秒，短显示为4秒
 * 因此自定义的显示时间最多不超过7秒，大于7为7，小于0为4
 */
public class ToastUtil {

    private static Toast toast;
    private static Handler handler = new Handler();

    private static Runnable run = new Runnable() {
        public void run() {
            toast.cancel();
            toast = null;
        }
    };

    /**
     * @param context  application
     * @param view     为null表示启用系统自带的view
     * @param msg      当参数view不为null时此值被忽略
     * @param gravity  当参数view为null时此值被忽略
     * @param xOffset  当参数view为null时此值被忽略
     * @param yOffset  当参数view为null时此值被忽略
     * @param duration 最多7秒
     */
    @SuppressLint("ShowToast")
    public static void show(Context context, View view, String msg, int gravity, int xOffset,
                            int yOffset, int duration) {
        handler.removeCallbacks(run);
        if (toast != null) {
            toast.cancel();
        }

        if (duration > 7000) {
            duration = 7000;
        } else if (duration <= 0) {
            duration = 4000;
        }
        if (view == null) {
            toast = Toast.makeText(context, msg,
                    duration > 4000 ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        } else {
            toast = new Toast(context.getApplicationContext());
            toast.setGravity(gravity, xOffset, yOffset);
            toast.setDuration(duration > 4000 ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
            toast.setView(view);
        }
        handler.postDelayed(run, duration);
        toast.show();
    }

}
