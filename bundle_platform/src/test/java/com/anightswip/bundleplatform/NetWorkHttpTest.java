package com.anightswip.bundleplatform;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.arch.core.util.Function;

import com.anightswip.bundleplatform.baselayer.contant.ApiPath;
import com.anightswip.bundleplatform.commonlib.network.ApiManager;
import com.anightswip.bundleplatform.commonlib.network.BaseNetResponse;
import com.anightswip.bundleplatform.commonlib.network.Utils;
import com.jraska.livedata.TestObserver;

import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * 网络请求测试
 */
public class NetWorkHttpTest {

    @Rule
    public InstantTaskExecutorRule testRule = new InstantTaskExecutorRule();

    @Test
    public void parseRequestParmTest() {
        HashMap<String, String> params = new HashMap<>();
        params.put("resource_id", "a807b7ab-6cad-4aa6-87d0-e283a7353a0f");
        params.put("limit", "59");
        assertEquals("https://data.gov.sg/api/action/datastore_search?limit=59&resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f",
                Utils.parseRequestParam(params, ApiPath.DATASTORE_SEARCH));

        assertNull(Utils.parseRequestParam(null, ApiPath.DATASTORE_SEARCH));

        HashMap<String, String> params2 = new HashMap<>();
        assertNull(null, Utils.parseRequestParam(params2, ApiPath.DATASTORE_SEARCH));

        HashMap<String, String> params3 = new HashMap<>();
        params3.put("limit", "59");
        assertEquals("https://data.gov.sg/api/action/datastore_search?limit=59",
                Utils.parseRequestParam(params3, ApiPath.DATASTORE_SEARCH));
    }

    @Test
    public void httpGetTest() throws Exception {
        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setBody("444"));
        server.start();
        String urlStr = server.url("api/action/datastore_search").url().toString();
        HashMap<String, String> params = new HashMap<>();
        params.put("resource_id", "a807b7ab-6cad-4aa6-87d0-e283a7353a0f");
        params.put("limit", "59");
        try {
            TestObserver.test(
                    ApiManager.getInstance().getAsLivedata(BeanMobileDataList.class, params, urlStr))
                    .awaitValue()
                    .assertHasValue()
                    .assertValue(new Function<BaseNetResponse<BeanMobileDataList>, Boolean>() {
                        @Override
                        public Boolean apply(BaseNetResponse<BeanMobileDataList> input) {
                            boolean result = input.info == null;
                            return result;
                        }
                    });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        RecordedRequest request1 = server.takeRequest();
        assertEquals("/api/action/datastore_search?limit=59&resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f",
                request1.getPath());
        server.shutdown();
    }
}
