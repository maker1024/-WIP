package com.anightswip.bundleplatform.baselayer;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.anightswip.bundleplatform.baselayer.page.GeneralPageManager;
import com.anightswip.bundleplatform.commonlib.page.IPageAble;

public class BaseFragment extends Fragment implements IPageAble {

    protected Context mContext;
    private GeneralPageManager mPageManager;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public GeneralPageManager page() {
        if (mPageManager == null) {
            mPageManager = new GeneralPageManager(mContext);
        }
        return mPageManager;
    }
}
