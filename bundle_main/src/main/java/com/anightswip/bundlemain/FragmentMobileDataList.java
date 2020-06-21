package com.anightswip.bundlemain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.anightswip.bundlemain.adapter.AdapterMobileDataList;
import com.anightswip.bundlemain.databinding.BundleMainFragmentMobileDataListBinding;
import com.anightswip.bundlemain.viewmodel.BViewMobileDataList;
import com.anightswip.bundlemain.viewmodel.ViewModelMobileDataList;
import com.anightswip.bundleplatform.baselayer.BaseFragment;
import com.anightswip.bundleplatform.commonlib.page.BasePageStatus;

/**
 * 列表展示
 */
public class FragmentMobileDataList extends BaseFragment {

    private BundleMainFragmentMobileDataListBinding mBinding;
    private ViewModelMobileDataList mViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ViewModelMobileDataList.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = BundleMainFragmentMobileDataListBinding.inflate(getLayoutInflater());
        return page().warpTipsView(mBinding.getRoot());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mViewModel.getViewData().observe(this, new Observer<BViewMobileDataList>() {
            @Override
            public void onChanged(BViewMobileDataList bViewAbout) {
                if (mBinding.recycleView.getAdapter() == null) return;
                ((AdapterMobileDataList) mBinding.recycleView.getAdapter()).setData(bViewAbout.getInfos());
            }
        });
        mViewModel.getPageStatus().observe(this, new Observer<BasePageStatus>() {
            @Override
            public void onChanged(BasePageStatus basePageStatus) {
                if (basePageStatus == null) {
                    return;
                }
                switch (basePageStatus.status) {
                    case BasePageStatus.PAGE_ERROR_DATA:
                    case BasePageStatus.PAGE_NO_DATA:
                    case BasePageStatus.PAGE_NO_NETWORK:
                        page().showNoNetworkView(true, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                init();
                            }
                        });
                        break;
                    case BasePageStatus.PAGE_NORMAL:
                        page().stopLoad();
                        break;
                }
            }
        });
        initRecycleView();
        init();
    }

    private void init() {
        page().loading();
        mViewModel.fetchMobileData();
    }

    private void initRecycleView() {
        mBinding.recycleView.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.recycleView.setAdapter(new AdapterMobileDataList());
    }
}
