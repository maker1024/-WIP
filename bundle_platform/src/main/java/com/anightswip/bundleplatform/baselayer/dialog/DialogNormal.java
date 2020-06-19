package com.anightswip.bundleplatform.baselayer.dialog;

import android.content.Context;

import androidx.annotation.NonNull;

import com.anightswip.bundleplatform.commonlib.dialog.DialogNormalCustom;
import com.anightswip.bundleplatform.databinding.BundlePlatformNormalDialogBinding;

/**
 * 普通对话框的一个实现，仿IOS风格
 */
public class DialogNormal extends DialogNormalCustom {

    public DialogNormal(@NonNull Context context) {
        super(context);
        build();
    }

    public DialogNormal(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        build();
    }

    private void build() {
        BundlePlatformNormalDialogBinding binding =
                BundlePlatformNormalDialogBinding.inflate(getLayoutInflater());
        contentView(binding.getRoot());
        marginLR(50f);
        cancelable(false);
    }
}
