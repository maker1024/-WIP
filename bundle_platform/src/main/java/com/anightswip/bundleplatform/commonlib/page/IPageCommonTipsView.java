package com.anightswip.bundleplatform.commonlib.page;

import android.view.View;

import androidx.annotation.IdRes;

/**
 * 通用页面行为接口
 */

public interface IPageCommonTipsView {
    /**
     * 空页面
     *
     * @param isClickedRefresh 用下来手势刷新还是点击刷新
     * @param listener
     */
    void showNoDataView(boolean isClickedRefresh, final View.OnClickListener listener);

    /**
     * 加载错误页面
     *
     * @param isClickedRefresh 用下来手势刷新还是点击刷新
     * @param listener
     */
    void showLoadErrorView(boolean isClickedRefresh, final View.OnClickListener listener);

    /**
     * 无网络页面
     *
     * @param isClickedRefresh 用下来手势刷新还是点击刷新
     * @param listener
     */
    void showNoNetworkView(boolean isClickedRefresh, final View.OnClickListener listener);

    /**
     * 关闭TipsView
     */
    void dissmissTipsView();

    /**
     * 是否正在显示TipsView
     */
    boolean isShowTipsView();

    /**
     * 是否TipsView正在处于加载中
     */
    boolean isTipsLoading();

    /**
     * 停止TipsView的加载状态
     */
    void stopTipsLoading();

    /**
     * 加载中
     */
    void loading();

    /**
     * 停止加载
     */
    void stopLoad();

    /**
     * 加载中
     */
    boolean isLoading();

    /**
     * TipsView盖在某个view上
     *
     * @param contentView TipsView包裹的某个view,一般是RootView
     * @return
     */
    View warpTipsView(View contentView);

    /**
     * TipsView盖在某个view上
     *
     * @param contentView TipsView包裹的某个view,一般是RootView
     * @param whichView   contentView下whichView标识的view
     * @return
     */
    View warpTipsView(View contentView, @IdRes int whichView);
}
