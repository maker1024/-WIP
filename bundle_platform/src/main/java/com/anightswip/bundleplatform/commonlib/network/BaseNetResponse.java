package com.anightswip.bundleplatform.commonlib.network;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 网络应答状态封装类
 * @param <T> service应答model类型
 */
public class BaseNetResponse<T> {

    public T info;
    public boolean hasError = false;
    public String errMsg = "";
    @ErrorCode
    public int errCode;

    @IntDef({SERVICE_ERROR, NO_NETWORK, ERROR_DATA})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ErrorCode {
    }

    public static final int SERVICE_ERROR = -1;
    public static final int NO_NETWORK = 0;
    public static final int ERROR_DATA = 1;

    public static  BaseNetResponse errorData() {
        BaseNetResponse baseNetResponse = new BaseNetResponse<>();
        baseNetResponse.hasError = true;
        baseNetResponse.info = null;
        baseNetResponse.errCode = BaseNetResponse.ERROR_DATA;
        baseNetResponse.errMsg = Constant.DATA_ERROR;
        return baseNetResponse;
    }

    public static BaseNetResponse errorService() {
        BaseNetResponse baseNetResponse = new BaseNetResponse();
        baseNetResponse.hasError = true;
        baseNetResponse.info = null;
        baseNetResponse.errCode = BaseNetResponse.SERVICE_ERROR;
        baseNetResponse.errMsg = Constant.WEBSERVICE_ERROR;
        return baseNetResponse;
    }

    public static BaseNetResponse errorNetwork() {
        BaseNetResponse baseNetResponse = new BaseNetResponse();
        baseNetResponse.hasError = true;
        baseNetResponse.info = null;
        baseNetResponse.errCode = BaseNetResponse.NO_NETWORK;
        baseNetResponse.errMsg = Constant.NET_ERROR;
        return baseNetResponse;
    }

    public static <T> BaseNetResponse<T> success(T info) {
        BaseNetResponse<T> baseNetResponse = new BaseNetResponse<>();
        baseNetResponse.info = info;
        return baseNetResponse;
    }
}
