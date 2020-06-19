package com.anightswip.bundleplatform.baselayer.page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.DrawableRes;

import com.anightswip.bundleplatform.R;
import com.anightswip.bundleplatform.databinding.BundlePlatformViewCommonTipsBinding;

/**
 * author : sangyi475
 * e-mail : SANGYI475@pingan.com.cn
 * desc   : 公用的提示页面，比如暂无数据，加载失败，暂无网络，也支持自定义
 */

public class PageCommonTipsView {

    private BundlePlatformViewCommonTipsBinding mBigBinding;

    public PageCommonTipsView(Context context) {
        mBigBinding = BundlePlatformViewCommonTipsBinding.inflate(LayoutInflater.from(context));

    }

    public PageCommonTipsView(Context context, @DrawableRes int imageRes, String centerText, String secondText) {
        mBigBinding = BundlePlatformViewCommonTipsBinding.inflate(LayoutInflater.from(context));
        mBigBinding.setImage(imageRes);
        mBigBinding.setCenterText(centerText);
        mBigBinding.setSecondText(secondText);
    }

    public PageCommonTipsView needTopButtom() {
        if (mBigBinding != null) {
            mBigBinding.setNeedTopButtom(true);
        }
        return this;
    }

    public View getNoDataView() {
        if (mBigBinding != null) {
            mBigBinding.setImage(R.drawable.bundle_platform_no_data);
            mBigBinding.setCenterText(mBigBinding.getRoot().getContext().getString(R.string.platform_no_data));
            mBigBinding.setSecondText(mBigBinding.getRoot().getContext().getString(R.string.platform_click_refresh));
            return mBigBinding.getRoot();
        }
        return null;
    }

    public View getNoDataView(String secondTips) {
        if (mBigBinding != null) {
            mBigBinding.setImage(R.drawable.bundle_platform_no_data);
            mBigBinding.setCenterText(mBigBinding.getRoot().getContext().getString(R.string.platform_no_data));
            mBigBinding.setSecondText(secondTips);
            return mBigBinding.getRoot();
        }
        return null;
    }

    public View getLoadErrorView() {
        if (mBigBinding != null) {
            mBigBinding.setImage(R.drawable.bundle_platform_load_error);
            mBigBinding.setCenterText(mBigBinding.getRoot().getContext().getString(R.string.platform_load_fail));
            mBigBinding.setSecondText(mBigBinding.getRoot().getContext().getString(R.string.platform_click_refresh));
            return mBigBinding.getRoot();
        }
        return null;
    }

    public View getLoadErrorView(String secondTips) {
        if (mBigBinding != null) {
            mBigBinding.setImage(R.drawable.bundle_platform_load_error);
            mBigBinding.setCenterText(mBigBinding.getRoot().getContext().getString(R.string.platform_load_fail));
            mBigBinding.setSecondText(secondTips);
            return mBigBinding.getRoot();
        }
        return null;
    }

    public View getNoNetworkView() {
        if (mBigBinding != null) {
            mBigBinding.setImage(R.drawable.bundle_platform_no_network);
            mBigBinding.setCenterText(mBigBinding.getRoot().getContext().getString(R.string.platform_no_network));
            mBigBinding.setSecondText(mBigBinding.getRoot().getContext().getString(R.string.platform_click_refresh));
            return mBigBinding.getRoot();
        }
        return null;
    }

    public View getNoNetworkView(String secondTips) {
        if (mBigBinding != null) {
            mBigBinding.setImage(R.drawable.bundle_platform_no_network);
            mBigBinding.setCenterText(mBigBinding.getRoot().getContext().getString(R.string.platform_no_network));
            mBigBinding.setSecondText(secondTips);
            return mBigBinding.getRoot();
        }
        return null;
    }

    public View getCustomView() {
        if (mBigBinding != null) {
            return mBigBinding.getRoot();
        }
        return null;
    }

    public PageCommonTipsView setOnClickListener(View.OnClickListener listener) {
        if (mBigBinding != null) {
            mBigBinding.setClick(listener);
        }
        return this;
    }
}
