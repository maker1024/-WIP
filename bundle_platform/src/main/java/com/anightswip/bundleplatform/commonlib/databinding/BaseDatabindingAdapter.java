package com.anightswip.bundleplatform.commonlib.databinding;

import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.databinding.BindingAdapter;

/**
 * 用在databinding中的简便方法
 */
public class BaseDatabindingAdapter {

    /**
     * 可在布局文件中直接设置
     * @param img ImageView
     * @param src 图片资源
     */
    @BindingAdapter(value = {"android:setSrcInt"}, requireAll = true)
    public static void setImageSrcInt(ImageView img, @DrawableRes int src) {
        img.setImageResource(src);
    }
}
