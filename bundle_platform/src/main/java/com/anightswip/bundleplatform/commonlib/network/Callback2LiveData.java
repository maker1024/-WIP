package com.anightswip.bundleplatform.commonlib.network;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 把okhttp的Callback转为LiveData形式
 *
 * @param <T> Json需要转为的bean
 */
public abstract class Callback2LiveData<T> implements Callback {

    abstract void onNetworkReconnection();

    private IResponseParse2Bean mHttpParser;
    private final MutableLiveData<BaseNetResponse<T>> mResultLd = new MutableLiveData<>();
    private Class<T> mClassT;

    public Callback2LiveData(IResponseParse2Bean httpParser, Class<T> classT) {
        mHttpParser = httpParser;
        mClassT = classT;
    }

    public LiveData<BaseNetResponse<T>> getResultAsLiveData() {
        return mResultLd;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        onNetworkReconnection();//断网重连需要重新初始化httpclient
        mResultLd.postValue(BaseNetResponse.errorNetwork());
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        try {
            if (!response.isSuccessful()) {
                mResultLd.postValue(BaseNetResponse.errorService());
                return;
            }
            String responseStr = response.body().string();
            if (TextUtils.isEmpty(responseStr)) {
                mResultLd.postValue(BaseNetResponse.errorData());
                return;
            }
            T bean = mHttpParser.onParse(responseStr, mClassT);
            if (bean == null) {
                mResultLd.postValue(BaseNetResponse.errorData());
                return;
            }
            BaseNetResponse<T> responseClient = new BaseNetResponse<>();
            responseClient.info = bean;
            mResultLd.postValue(responseClient);
        } catch (Exception e) {
            mResultLd.postValue(BaseNetResponse.errorService());
        }
    }
}
