package com.anightswip.bundleplatform.commonlib.network;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.anightswip.bundleplatform.commonlib.utils.GsonUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
                mOkHttpClient = buildOkhttpClient();//断网重连需要重新初始化httpclient
                lv.postValue(BaseNetResponse.errorNetwork());
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    if (!response.isSuccessful()) {
                        lv.postValue(BaseNetResponse.errorService());
                        return;
                    }
                    JsonObject jsonResponse =
                            JsonParser.parseString(response.body().string()).getAsJsonObject();
                    if (!jsonResponse.get("success").getAsBoolean()) {
                        lv.postValue(BaseNetResponse.errorService());
                        return;
                    }
                    T bean = GsonUtil.getReponseBean(
                            jsonResponse.get("result").getAsJsonObject().toString(), classT);
                    if (bean == null) {
                        lv.postValue(BaseNetResponse.errorData());
                        return;
                    }
                    BaseNetResponse<T> responseClient = new BaseNetResponse<>();
                    responseClient.info = bean;
                    lv.postValue(responseClient);
                } catch (Exception e) {
                    lv.postValue(BaseNetResponse.errorService());
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
