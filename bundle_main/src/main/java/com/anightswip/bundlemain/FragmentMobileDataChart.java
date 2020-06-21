package com.anightswip.bundlemain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.anightswip.bundleplatform.baselayer.BaseFragment;

/**
 * 图表展示
 */
public class FragmentMobileDataChart extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return new View(mContext);
    }

}
