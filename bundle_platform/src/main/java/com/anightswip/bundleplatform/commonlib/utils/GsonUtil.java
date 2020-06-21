package com.anightswip.bundleplatform.commonlib.utils;

import android.text.TextUtils;

import com.google.gson.Gson;

/**
 * google Gson的工具类
 */
public class GsonUtil {

    /**
     * 获取返回单个对象
     *
     * @param jsonStr
     * @return
     */
    public static <T> T getReponseBean(String jsonStr, Class<T> classOfT) {
        if (TextUtils.isEmpty(jsonStr)) {
            return null;
        }
        try {
            T obj = new Gson().fromJson(jsonStr.trim(), classOfT);
            if (obj != null) {
                return obj;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
