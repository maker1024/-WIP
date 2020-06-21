package com.anightswip.bundlemain.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.anightswip.bundlemain.bean.BeanMobileDataList;
import com.anightswip.bundlemain.repository.RepositoryMobileData;
import com.anightswip.bundleplatform.commonlib.network.BaseNetResponse;
import com.anightswip.bundleplatform.commonlib.viewmodel.PageViewModel;
import com.github.mikephil.charting.data.Entry;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * 图表fragment的viewmodel
 */
public class ViewModelMobileDataChart extends PageViewModel {

    private MediatorLiveData<BViewMobileDataChart> mUILiveData;
    private RepositoryMobileData mRepositoryMobileData;

    public ViewModelMobileDataChart() {
        super();
        mUILiveData = new MediatorLiveData<>();
        mRepositoryMobileData = new RepositoryMobileData();
    }

    public LiveData<BViewMobileDataChart> getViewData() {
        return mUILiveData;
    }

    public void fetchMobileData() {
        mUILiveData.addSource(mRepositoryMobileData.getAllMobileData(), new Observer<BaseNetResponse<BeanMobileDataList>>() {
            @Override
            public void onChanged(BaseNetResponse<BeanMobileDataList> response) {
                if (pageStatusNotify(response)) {
                    return;
                }
                BViewMobileDataChart bv;
                if (mUILiveData.getValue() != null) {
                    bv = mUILiveData.getValue();
                } else {
                    bv = new BViewMobileDataChart();
                }
                ArrayList<Entry> chartsInfo = new ArrayList<>();
                for (BeanMobileDataList.BeanMobileData tempBean : response.info.getRecords()) {
                    chartsInfo.add(new Entry(
                            response.info.getRecords().indexOf(tempBean),
                            new BigDecimal(tempBean.volume_of_mobile_data).floatValue(),
                            tempBean.quarter));
                }
                bv.setChartsInfo(chartsInfo);
                mUILiveData.setValue(bv);
            }
        });
    }
}
