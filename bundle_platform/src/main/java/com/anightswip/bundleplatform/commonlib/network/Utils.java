package com.anightswip.bundleplatform.commonlib.network;

import java.util.HashMap;
import java.util.Iterator;

public class Utils {

    /**
     * 解析http请求参数
     *
     * @param requestParametersMap
     * @param url
     * @return 返回带参数的请求地址
     */
    public static String parseRequestParam(HashMap<String, String> requestParametersMap, String url) {
        if (null != requestParametersMap && !requestParametersMap.isEmpty()) {
            int mapSize = requestParametersMap.size();
            int loopTimes = 0;
            StringBuffer pathBuffer = (new StringBuffer(url)).append("?");
            for (Iterator iterator = requestParametersMap.keySet().iterator(); iterator.hasNext(); ++loopTimes) {
                String key = (String) iterator.next();
                if (loopTimes == mapSize - 1) {
                    pathBuffer.append(key).append("=").append(requestParametersMap.get(key));
                } else {
                    pathBuffer.append(key).append("=").append(requestParametersMap.get(key)).append("&");
                }
            }
            return pathBuffer.toString();
        }
        return null;
    }

}
