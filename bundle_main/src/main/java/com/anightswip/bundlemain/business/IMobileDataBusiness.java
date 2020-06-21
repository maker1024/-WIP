package com.anightswip.bundlemain.business;

import androidx.lifecycle.LiveData;

import com.anightswip.bundlemain.bean.BeanMobileDataList;
import com.anightswip.bundleplatform.commonlib.network.BaseNetResponse;

public interface IMobileDataBusiness {

    /**
     * 从网络获取数据
     * @return livedata
     */
    LiveData<BaseNetResponse<BeanMobileDataList>> fetchMobileDataNet();
}
