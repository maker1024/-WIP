package com.anightswip.bundleplatform.commonlib.network;

import androidx.lifecycle.LiveData;

import java.util.HashMap;

/**
 * http请求的接口
 */
public interface IHttpRequest {

    /**
     * http的Get请求
     *
     * @param classT               应答的model
     * @param requestParametersMap 请求参数
     * @param url                  请求地址(带上host)
     * @param <T>                  应答的model
     * @return livedata
     */
    <T> LiveData<BaseNetResponse<T>> getAsLivedata(
            final Class<T> classT,
            HashMap<String, String> requestParametersMap,
            String url);

}
