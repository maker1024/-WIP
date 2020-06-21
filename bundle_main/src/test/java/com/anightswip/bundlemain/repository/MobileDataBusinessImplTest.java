package com.anightswip.bundlemain.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.anightswip.bundlemain.bean.BeanMobileDataList;
import com.anightswip.bundlemain.business.IMobileDataBusiness;
import com.anightswip.bundleplatform.commonlib.network.BaseNetResponse;

import java.util.ArrayList;

public class MobileDataBusinessImplTest implements IMobileDataBusiness {
    @Override
    public LiveData<BaseNetResponse<BeanMobileDataList>> fetchMobileDataNet() {
        MediatorLiveData<BaseNetResponse<BeanMobileDataList>> netLd = new MediatorLiveData<>();
        BeanMobileDataList beanMobileDataList = new BeanMobileDataList();
        ArrayList<BeanMobileDataList.BeanMobileData> beanMobileDataArrayList = new ArrayList<>();
        BeanMobileDataList.BeanMobileData beanMobileData = new BeanMobileDataList.BeanMobileData();
        beanMobileData._id = 2;
        beanMobileData.volume_of_mobile_data = "0.8876";
        beanMobileData.quarter = "2010-Q3";
        beanMobileDataArrayList.add(beanMobileData);
        beanMobileDataList.setRecords(beanMobileDataArrayList);
        BaseNetResponse<BeanMobileDataList> bean = BaseNetResponse.success(beanMobileDataList);
        netLd.setValue(bean);
        return netLd;
    }
}
