package com.anightswip.bundleplatform.commonlib.databinding;

import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.databinding.BindingAdapter;

/**
 * Created by sangY on 17/5/24.
 */

public class BaseDatabindingAdapter {

    @BindingAdapter(value = {"android:setSrcInt"}, requireAll = true)
    public static void setImageSrcInt(ImageView img, @DrawableRes int src) {
        img.setImageResource(src);
    }
}
