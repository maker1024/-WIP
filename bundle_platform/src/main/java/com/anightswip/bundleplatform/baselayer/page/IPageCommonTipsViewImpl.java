package com.anightswip.bundleplatform.baselayer.page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anightswip.bundleplatform.R;
import com.anightswip.bundleplatform.baselayer.page.PageCommonTipsView;
import com.anightswip.bundleplatform.commonlib.page.IPageCommonTipsView;
import com.anightswip.bundleplatform.databinding.BundlePlatformBasePageBinding;
import com.anightswip.bundleplatform.databinding.BundlePlatformPageCommonLoadingBinding;

/**
 * 页面通用功能的一个实现
 */
public class IPageCommonTipsViewImpl implements IPageCommonTipsView {

    protected Context mContext;
    private PageCommonTipsView mTipsView;
    private BundlePlatformBasePageBinding mBinding;
    private BundlePlatformPageCommonLoadingBinding mLoadingBind;

    public IPageCommonTipsViewImpl(Context context) {
        mContext = context;
    }

    @Override
    public void showNoDataView(boolean isClickedRefresh, final View.OnClickListener listener) {
        if (mBinding == null) {
            return;
        }
        if (mTipsView == null) {
            mTipsView = new PageCommonTipsView(mContext);
        }
        mBinding.tipsLayout.setRefreshing(false);
        mBinding.tipsLayout.setEnabled(false);
//        mBinding.tipsLayout.setOnRefreshListener(isClickedRefresh ? null : new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                if (listener != null) {
//                    listener.onClick(mBinding.tipsLayout);
//                }
//            }
//        });
        View.OnClickListener onClickListener = null;
        if (true) {
            onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    mBinding.tipsLayout.setRefreshing(true);
                    if (listener != null) {
                        listener.onClick(mBinding.tipsLayout);
                    }
                }
            };
        }
        mBinding.tipsContainer.removeAllViews();
        mBinding.tipsContainer.addView(mTipsView.setOnClickListener(onClickListener).getNoDataView(),
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mBinding.tipsLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoadErrorView(boolean isClickedRefresh, final View.OnClickListener listener) {
        if (mBinding == null) {
            return;
        }
        if (mTipsView == null) {
            mTipsView = new PageCommonTipsView(mContext);
        }
        mBinding.tipsLayout.setRefreshing(false);
        mBinding.tipsLayout.setEnabled(false);
//        mBinding.tipsLayout.setOnRefreshListener(isClickedRefresh ? null : new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                if (listener != null) {
//                    listener.onClick(mBinding.tipsLayout);
//                }
//            }
//        });
        View.OnClickListener onClickListener = null;
        if (true) {
            onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    mBinding.tipsLayout.setRefreshing(true);
                    if (listener != null) {
                        listener.onClick(mBinding.tipsLayout);
                    }
                }
            };
        }
        mBinding.tipsContainer.removeAllViews();
        mBinding.tipsContainer.addView(mTipsView.setOnClickListener(onClickListener).getLoadErrorView(),
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mBinding.tipsLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNoNetworkView(boolean isClickedRefresh, final View.OnClickListener listener) {
        if (mBinding == null) {
            return;
        }
        if (mTipsView == null) {
            mTipsView = new PageCommonTipsView(mContext);
        }
        mBinding.tipsLayout.setRefreshing(false);
        mBinding.tipsLayout.setEnabled(false);
//        mBinding.tipsLayout.setOnRefreshListener(isClickedRefresh ? null : new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                if (listener != null) {
//                    listener.onClick(mBinding.tipsLayout);
//                }
//            }
//        });
        View.OnClickListener onClickListener = null;
        if (true) {
            onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    mBinding.tipsLayout.setRefreshing(true);
                    if (listener != null) {
                        listener.onClick(mBinding.tipsLayout);
                    }
                }
            };
        }
        mBinding.tipsContainer.removeAllViews();
        mBinding.tipsContainer.addView(mTipsView.setOnClickListener(onClickListener).getNoNetworkView(),
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mBinding.tipsLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void dissmissTipsView() {
        if (mBinding != null) {
            mBinding.tipsLayout.setRefreshing(false);
            mBinding.tipsLayout.setVisibility(View.GONE);
            mBinding.tipsContainer.removeAllViews();
        }
        mTipsView = null;
        mLoadingBind = null;
    }

    @Override
    public boolean isShowTipsView() {
        return mTipsView != null;
    }

    @Override
    public boolean isTipsLoading() {
        if (mBinding == null) {
            return false;
        }
        return mBinding.tipsLayout.isRefreshing();
    }

    @Override
    public void stopTipsLoading() {
        if (mBinding != null) {
            mBinding.tipsLayout.setRefreshing(false);
        }
    }

    @Override
    public void loading() {
        if (mBinding == null) {
            return;
        }
        mBinding.tipsLayout.setRefreshing(false);
        mBinding.tipsLayout.setEnabled(false);
        mBinding.tipsContainer.removeAllViews();
        mLoadingBind = BundlePlatformPageCommonLoadingBinding.inflate(LayoutInflater.from(mContext));
        mBinding.tipsContainer.addView(mLoadingBind.getRoot(),
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mBinding.tipsLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopLoad() {
        if (mBinding != null) {
            mBinding.tipsLayout.setRefreshing(false);
            mBinding.tipsLayout.setVisibility(View.GONE);
            mBinding.tipsContainer.removeAllViews();
        }
        mTipsView = null;
        mLoadingBind = null;
    }

    @Override
    public boolean isLoading() {
        if (mLoadingBind != null) {
            return true;
        }
        return false;
    }

    @Override
    public View warpTipsView(View contentView) {
        if (mBinding == null) {
            mBinding = BundlePlatformBasePageBinding.inflate(LayoutInflater.from(mContext));
        }
        mBinding.contentContainer.addView(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mBinding.tipsLayout.setColorSchemeResources(R.color.main);
        return mBinding.getRoot();
    }

    @Override
    public View warpTipsView(View contentView, int whichView) {
        if (contentView == null) {
            return null;
        }
        View refreshView = contentView.findViewById(whichView);
        if (refreshView == null) {
            return warpTipsView(contentView);
        } else {
            if (mBinding == null) {
                mBinding = BundlePlatformBasePageBinding.inflate(LayoutInflater.from(mContext));
                mBinding.tipsLayout.setColorSchemeResources(R.color.main);
            }

            ViewGroup.LayoutParams vglp = refreshView.getLayoutParams();
            ViewGroup refreshViewParent = (ViewGroup) refreshView.getParent();
            int refreshViewIndex = refreshViewParent.indexOfChild(refreshView);
            refreshViewParent.removeView(refreshView);
            refreshViewParent.addView(mBinding.getRoot(), refreshViewIndex, vglp);
            mBinding.contentContainer.addView(refreshView,
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return contentView;
        }
    }
}
