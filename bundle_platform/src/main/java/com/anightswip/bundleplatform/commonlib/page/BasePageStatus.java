package com.anightswip.bundleplatform.commonlib.page;

import androidx.annotation.IntDef;
import androidx.lifecycle.MutableLiveData;

import com.anightswip.bundleplatform.commonlib.network.BaseNetResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class BasePageStatus {

    @StatusCode
    public int status;

    public String tipStr;

    @IntDef({PAGE_NO_NETWORK, PAGE_ERROR_DATA, PAGE_NO_DATA, PAGE_NORMAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface StatusCode {
    }

    public static final int PAGE_NO_NETWORK = 0;//网络出错
    public static final int PAGE_ERROR_DATA = 1;//数据出错
    public static final int PAGE_NO_DATA = 2;//暂无无数据
    public static final int PAGE_NORMAL = -1;//普通

    public static BasePageStatus noNetwork(String msg) {
        BasePageStatus bean = new BasePageStatus();
        bean.status = PAGE_NO_NETWORK;
        bean.tipStr = msg;
        return bean;
    }

    public static BasePageStatus noData(String msg) {
        BasePageStatus bean = new BasePageStatus();
        bean.status = PAGE_NO_DATA;
        bean.tipStr = msg;
        return bean;
    }

    public static BasePageStatus errorData(String msg) {
        BasePageStatus bean = new BasePageStatus();
        bean.status = PAGE_ERROR_DATA;
        bean.tipStr = msg;
        return bean;
    }

    public static BasePageStatus normal(String msg) {
        BasePageStatus bean = new BasePageStatus();
        bean.status = PAGE_NORMAL;
        bean.tipStr = msg;
        return bean;
    }
}
