package com.anightswip.bundleplatform.commonlib.network;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.anightswip.bundleplatform.commonlib.utils.GsonUtil;

import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 网络请求
 */
public class ApiManager implements IHttpRequest {

    private static volatile ApiManager mInstance;
    private OkHttpClient mOkHttpClient;

    private ApiManager() {
        mOkHttpClient = buildOkhttpClient();
    }

    public static ApiManager getInstance() {
        if (mInstance == null) {
            synchronized (ApiManager.class) {
                if (mInstance == null) {
                    mInstance = new ApiManager();
                }
            }
        }
        return mInstance;
    }

    @Override
    public <T> LiveData<BaseNetResponse<T>> getAsLivedata(
            final Class<T> classT,
            HashMap<String, String> requestParametersMap,
            String url) {
        final MutableLiveData<BaseNetResponse<T>> lv = new MutableLiveData<>();
        String wholeUrl = Utils.parseRequestParam(requestParametersMap, url);
        Request request2 = new Request.Builder()
                .get()
                .url(wholeUrl == null ? "" : wholeUrl)
                .build();
        mOkHttpClient.newCall(request2).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mOkHttpClient = buildOkhttpClient();//断网重连
                BaseNetResponse<T> response = new BaseNetResponse<>();
                response.hasError = true;
                response.info = null;
                response.errCode = BaseNetResponse.NO_NETWORK;
                response.errMsg = Constant.NET_ERROR;
                lv.postValue(response);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    if (!response.isSuccessful()) {
                        BaseNetResponse<T> responseClient = new BaseNetResponse<>();
                        responseClient.hasError = true;
                        responseClient.info = null;
                        responseClient.errCode = BaseNetResponse.SERVICE_ERROR;
                        responseClient.errMsg = Constant.WEBSERVICE_ERROR;
                        lv.postValue(responseClient);
                        return;
                    }
                    JSONObject jsonResponse = new JSONObject(response.body().string());
                    if (!jsonResponse.optBoolean("success")) {
                        BaseNetResponse<T> responseClient = new BaseNetResponse<>();
                        responseClient.hasError = true;
                        responseClient.info = null;
                        responseClient.errCode = BaseNetResponse.SERVICE_ERROR;
                        responseClient.errMsg = Constant.WEBSERVICE_ERROR;
                        lv.postValue(responseClient);
                        return;
                    }
                    T bean = GsonUtil.getReponseBean(jsonResponse.optJSONObject("result"), classT);
                    if (bean == null) {
                        BaseNetResponse<T> responseClient = new BaseNetResponse<>();
                        responseClient.hasError = true;
                        responseClient.info = null;
                        responseClient.errCode = BaseNetResponse.ERROR_DATA;
                        responseClient.errMsg = Constant.DATA_ERROR;
                        lv.postValue(responseClient);
                        return;
                    }
                    BaseNetResponse<T> responseClient = new BaseNetResponse<>();
                    responseClient.info = bean;
                    lv.postValue(responseClient);
                } catch (Exception e) {
                    BaseNetResponse<T> responseClient = new BaseNetResponse<>();
                    responseClient.hasError = true;
                    responseClient.info = null;
                    responseClient.errCode = BaseNetResponse.SERVICE_ERROR;
                    responseClient.errMsg = Constant.WEBSERVICE_ERROR;
                    lv.postValue(responseClient);
                }
            }
        });
        return lv;
    }

    private OkHttpClient buildOkhttpClient() {
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:"
                        + Arrays.toString(trustManagers));
            }
            X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(8 * 1000, TimeUnit.MILLISECONDS)
                    .readTimeout(8 * 1000, TimeUnit.MILLISECONDS)
                    .retryOnConnectionFailure(true)
                    .sslSocketFactory(sslSocketFactory, trustManager);

            return builder.build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new OkHttpClient.Builder()
                .connectTimeout(8 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(8 * 1000, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .build();
    }
}
