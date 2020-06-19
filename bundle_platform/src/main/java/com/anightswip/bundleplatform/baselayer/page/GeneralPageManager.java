package com.anightswip.bundleplatform.baselayer.page;

import android.content.Context;
import android.view.View;

import com.anightswip.bundleplatform.commonlib.page.IPageCommonTipsView;

/**
 * author : sangyi475
 * e-mail : SANGYI475@pingan.com.cn
 * desc   : 通用页面管理
 */
public class GeneralPageManager implements IPageCommonTipsView {

    private IPageCommonTipsViewImpl mICommonTipsView;

    public GeneralPageManager(Context context) {
        mICommonTipsView = new IPageCommonTipsViewImpl(context);
    }

    @Override
    public void showNoDataView(boolean isClickedRefresh, final View.OnClickListener listener) {
        mICommonTipsView.showNoDataView(isClickedRefresh, listener);
    }

    @Override
    public void showLoadErrorView(boolean isClickedRefresh, final View.OnClickListener listener) {
        mICommonTipsView.showLoadErrorView(isClickedRefresh, listener);
    }

    @Override
    public void showNoNetworkView(boolean isClickedRefresh, final View.OnClickListener listener) {
        mICommonTipsView.showNoNetworkView(isClickedRefresh, listener);
    }

    @Override
    public void dissmissTipsView() {
        mICommonTipsView.dissmissTipsView();
    }

    @Override
    public boolean isShowTipsView() {
        return mICommonTipsView.isShowTipsView();
    }

    @Override
    public boolean isTipsLoading() {
        return mICommonTipsView.isTipsLoading();
    }

    @Override
    public void stopTipsLoading() {
        mICommonTipsView.stopTipsLoading();
    }

    @Override
    public void loading() {
        mICommonTipsView.loading();
    }

    @Override
    public void stopLoad() {
        mICommonTipsView.stopLoad();
    }

    @Override
    public boolean isLoading() {
        return mICommonTipsView.isLoading();
    }

    @Override
    public View warpTipsView(View contentView) {
        return mICommonTipsView.warpTipsView(contentView);
    }

    @Override
    public View warpTipsView(View contentView, int whichView) {
        return mICommonTipsView.warpTipsView(contentView, whichView);
    }
}
