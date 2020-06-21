package com.anights.wip.frame;


import android.app.Dialog;
import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;

import com.anights.wip.databinding.ActivityFrameBinding;
import com.anightswip.bundleplatform.baselayer.BaseActivity;
import com.anightswip.bundleplatform.baselayer.dialog.DialogNormal;
import com.anightswip.bundleplatform.commonlib.activitymanager.ManagerActivity;
import com.anightswip.bundleplatform.commonlib.dialog.IDialogClickedListener;
import com.anightswip.bundleplatform.commonlib.utils.FragmentChangeUtil;

/**
 * 框架页面
 */
public class ActivityFrame extends BaseActivity {

    private ActivityFrameBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityFrameBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        init();
    }

    private void init() {
        mBinding.buttomLayout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentChangeUtil.changePage(
                        getSupportFragmentManager(),
                        mBinding.content.getId(),
                        checkedId == mBinding.cbChart.getId()
                                ? "com.anightswip.bundlemain.FragmentMobileDataChart"
                                : "com.anightswip.bundlemain.FragmentMobileDataList",
                        null);
            }
        });
        mBinding.cbChart.setChecked(true);
    }

    @Override
    public void onBackPressed() {
        new DialogNormal(mContext)
                .msg("确定退出App吗？")
                .rightBtn("取消", new IDialogClickedListener() {
                    @Override
                    public void onClicked(Dialog dialog) {
                        dialog.dismiss();
                    }
                })
                .leftBtn("确定", new IDialogClickedListener() {
                    @Override
                    public void onClicked(Dialog dialog) {
                        dialog.dismiss();
                        ManagerActivity.getInstance().exit();
                    }
                })
                .show();
    }
}
