package com.anightswip.bundlemain.business;

import androidx.lifecycle.LiveData;

import com.anightswip.bundlemain.bean.BeanMobileDataList;
import com.anightswip.bundleplatform.baselayer.contant.ApiPath;
import com.anightswip.bundleplatform.commonlib.network.ApiManager;
import com.anightswip.bundleplatform.commonlib.network.BaseNetResponse;

import java.util.HashMap;

/**
 * business的实现类
 */
public class MobileDataBusinessImpl implements IMobileDataBusiness {

    @Override
    public LiveData<BaseNetResponse<BeanMobileDataList>> fetchMobileDataNet() {
        HashMap<String, String> params = new HashMap<>();
        params.put("resource_id", "a807b7ab-6cad-4aa6-87d0-e283a7353a0f");
        params.put("limit", "59");
        return ApiManager.getInstance().getAsLivedata(BeanMobileDataList.class, params, ApiPath.DATASTORE_SEARCH);
    }
}
