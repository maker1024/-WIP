package com.anightswip.bundleplatform.baselayer.network;

import androidx.annotation.NonNull;

import com.anightswip.bundleplatform.commonlib.network.IResponseParse2Bean;
import com.anightswip.bundleplatform.commonlib.utils.GsonUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * 不同app可能有不同的Json解析字段
 */
public class HttpResponseParser implements IResponseParse2Bean {

    @Override
    public <T> T onParse(@NonNull String httpResponse, Class<T> classT) {
        if (httpResponse == null || httpResponse.isEmpty()) return null;
        JsonObject jsonResponse =
                JsonParser.parseString(httpResponse).getAsJsonObject();
        if (!jsonResponse.get("success").getAsBoolean()) {
            return null;
        }
        return GsonUtil.getReponseBean(
                jsonResponse.get("result").getAsJsonObject().toString(), classT);
    }
}
