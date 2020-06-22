package com.anightswip.bundlemain.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.anightswip.bundlemain.bean.BeanMobileDataList;
import com.anightswip.bundlemain.business.IMobileDataBusiness;
import com.anightswip.bundleplatform.commonlib.network.BaseNetResponse;

public class MobileDataBusinessImplTestCaseNetError implements IMobileDataBusiness {
    @Override
    public LiveData<BaseNetResponse<BeanMobileDataList>> fetchMobileDataNet() {
        MediatorLiveData<BaseNetResponse<BeanMobileDataList>> netLd = new MediatorLiveData<>();
        BaseNetResponse<BeanMobileDataList> bean = BaseNetResponse.errorService();
        netLd.setValue(bean);
        return netLd;
    }
}
