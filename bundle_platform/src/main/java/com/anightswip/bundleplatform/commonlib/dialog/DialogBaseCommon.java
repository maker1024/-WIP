package com.anightswip.bundleplatform.commonlib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.anightswip.bundleplatform.R;

/**
 * 对话框的基类，所有对话框继承实现
 */
public abstract class DialogBaseCommon extends Dialog {

    private int mWidth;
    private int mHeight;

    public DialogBaseCommon(@NonNull Context context) {
        super(context, R.style.base_common_dialog);
        setCancelable(true);
    }

    public DialogBaseCommon(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        setCancelable(true);
    }

    public DialogBaseCommon(@NonNull Context context, boolean cancelable) {
        super(context, R.style.base_common_dialog);
        setCancelable(cancelable);
    }

    public DialogBaseCommon(@NonNull Context context, boolean cancelable, @StyleRes int themeResId) {
        super(context, themeResId);
        setCancelable(cancelable);
    }

    /**
     * setContentView()
     * 视图的初始化
     */
    protected abstract View buildView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**
         * 设置对话框属性
         */
        Window dialogWindow = this.getWindow();
        int width = dialogWindow.getWindowManager().getDefaultDisplay().getWidth();
        int height = dialogWindow.getWindowManager().getDefaultDisplay().getHeight();
        width = resetDialogWidth(width);
        mWidth = width;
        height = resetDialogHeight(height, width);
        mHeight = height;
        dialogWindow.setGravity(resetDialogGravity());
        dialogWindow.setDimAmount(resetDim());
        dialogWindow.setWindowAnimations(resetDialogAnim());
        setContentView(buildView());
        dialogWindow.setLayout(width, height);
    }

    /**
     * @param displayWidth
     * @return 可以返回ViewGroup.LayoutParams.WRAP_CONTENT或者ViewGroup.LayoutParams.MATCH_PARENT, 或者指定的值
     */
    protected int resetDialogWidth(int displayWidth) {
        return displayWidth;//- 2 * DensityUtil.dp2px(getContext(), 30);
    }

    /**
     * @param dialogHeight
     * @param displayWidth
     * @return 可以返回ViewGroup.LayoutParams.WRAP_CONTENT或者ViewGroup.LayoutParams.MATCH_PARENT, 或者指定的值
     */
    protected int resetDialogHeight(int dialogHeight, int displayWidth) {
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    /**
     * 重新设置dim值，默认0.5
     *
     * @return
     */
    protected float resetDim() {
        return 0.5f;
    }

    /**
     * @return 返回content的Gravity
     */
    protected int resetDialogGravity() {
        return Gravity.CENTER;
    }

    /**
     * @return 返回dialog的出场动画, 0表示采用系统默认的
     */
    @StyleRes
    protected int resetDialogAnim() {
        return 0;
    }

    public int getDialogWidth() {
        return mWidth;
    }

    public int getDialogHeight() {
        return mHeight;
    }
}
