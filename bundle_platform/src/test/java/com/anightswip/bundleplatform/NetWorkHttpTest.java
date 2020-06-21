package com.anightswip.bundleplatform;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.anightswip.bundleplatform.baselayer.contant.ApiPath;
import com.anightswip.bundleplatform.commonlib.network.ApiManager;
import com.anightswip.bundleplatform.commonlib.network.Utils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.mockwebserver.Dispatcher;
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

    private MockWebServer mockServer;

    @Test
    public void parseRequestParmTest() {
        HashMap<String, String> params = new HashMap<>();
        params.put("resource_id", "15033");
        params.put("limit", "59");
        assertEquals("https://data.gov.sg/api/action/datastore_search?limit=59&resource_id=15033",
                Utils.parseRequestParam(params, ApiPath.DATASTORE_SEARCH));

        assertNull(Utils.parseRequestParam(null, ApiPath.DATASTORE_SEARCH));

        HashMap<String, String> params2 = new HashMap<>();
        assertNull(null, Utils.parseRequestParam(params2, ApiPath.DATASTORE_SEARCH));

        HashMap<String, String> params3 = new HashMap<>();
        params3.put("limit", "10");
        assertEquals("https://data.gov.sg/api/action/datastore_search?limit=10",
                Utils.parseRequestParam(params3, ApiPath.DATASTORE_SEARCH));
    }

    @Before
    public void setUpServer() throws Exception {
        mockServer = new MockWebServer();
        final Dispatcher dispatcher = new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                switch (request.getPath()) {
                    case "/api/action/datastore_search?limit=10&resource_id=110":
                        return new MockResponse().setResponseCode(200).setBody("this is a json");
                }
                return new MockResponse().setResponseCode(404);
            }
        };
        mockServer.setDispatcher(dispatcher);
        mockServer.start();
    }

    @Test
    public void httpGetTest() throws Exception {
        String urlStr = mockServer.url("api/action/datastore_search").url().toString();
        HashMap<String, String> params = new HashMap<>();
        params.put("resource_id", "110");
        params.put("limit", "10");
//        try {
//            TestObserver.test(
//                    ApiManager.getInstance().getAsLivedata(BeanMobileDataList.class, params, urlStr))
//                    .awaitValue()
//                    .assertHasValue()
//                    .assertValue(new Function<BaseNetResponse<BeanMobileDataList>, Boolean>() {
//                        @Override
//                        public Boolean apply(BaseNetResponse<BeanMobileDataList> input) {
//                            boolean result = input.errCode == BaseNetResponse.ERROR_DATA;
//                            return result;
//                        }
//                    });
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        ApiManager.getInstance().getAsCallback(params, urlStr, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                assertNull(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                assertEquals("this is a json", response.body().string());
            }
        });
        RecordedRequest request1 = mockServer.takeRequest();
        assertEquals("/api/action/datastore_search?limit=10&resource_id=110",
                request1.getPath());
        mockServer.shutdown();
    }
}
