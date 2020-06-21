package com.anightswip.bundleplatform.commonlib.network;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.security.KeyStore;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 网络请求管理
 */
public class ApiManager implements IHttpRequest {

    private static volatile ApiManager mInstance;
    private OkHttpClient mOkHttpClient;
    private IResponseParse2Bean mResponseParser;

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

    /**
     * 设置Json接口定义相关字段的解析器
     * @param httpParser 实现IResponseParse2Bean的解析器
     */
    public void init(@NonNull IResponseParse2Bean httpParser) {
        if (httpParser == null) {
            throw new IllegalStateException("method param 'httpParser' should not be NULL!");
        }
        mResponseParser = httpParser;
    }

    /**
     * 接口请求，http的get方法
     * @param requestParametersMap 请求参数
     * @param url 带有host的url接口地址
     * @param callback 注意callback的方法是运行在子线程而不是UI线程
     */
    private void getAsCallback(
            HashMap<String, String> requestParametersMap,
            String url,
            Callback callback) {
        String wholeUrl = Utils.parseRequestParam(requestParametersMap, url);
        Request request = new Request.Builder()
                .get()
                .url(wholeUrl == null ? "" : wholeUrl)
                .build();
        mOkHttpClient.newCall(request).enqueue(callback);
    }

    @Override
    public <T> LiveData<BaseNetResponse<T>> getAsLivedata(
            final Class<T> classT,
            HashMap<String, String> requestParametersMap,
            String url) {
        if (mResponseParser == null) {
            throw new IllegalStateException("call 'ApiManager.getInstance().init()' first!");
        }
        Callback2LiveData<T> callback2LiveData = new Callback2LiveData<T>(mResponseParser, classT) {
            @Override
            void onNetworkReconnection() {
                mOkHttpClient = buildOkhttpClient();
            }
        };
        getAsCallback(requestParametersMap, url, callback2LiveData);
        return callback2LiveData.getResultAsLiveData();
    }

    //生成默认的okhttpclient
    private OkHttpClient buildOkhttpClient() {
        //获取https请求的client
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
        //获取https的失败，只能获取http的
        return new OkHttpClient.Builder()
                .connectTimeout(8 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(8 * 1000, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .build();
    }
}
