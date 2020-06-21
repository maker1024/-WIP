package com.anightswip.bundleplatform.commonlib.network;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

/**
 * http应答处理，与web接口定义相关
 */
public interface IResponseParse2Bean {

    /**
     * 实现接口用于解析接口定义相关字段，不同app会有不同定义
     *
     * @param httpResponse 网络返回的body转为的String，默认为Json格式
     * @param classT 要转换成的bean
     * @param <T> 要转换成的bean
     * @return 成功转换的bean
     */
    @WorkerThread
    <T> T onParse(@NonNull String httpResponse, Class<T> classT) throws Exception;

}
