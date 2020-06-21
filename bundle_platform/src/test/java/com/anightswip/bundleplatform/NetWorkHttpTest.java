package com.anightswip.bundleplatform;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.arch.core.util.Function;

import com.anightswip.bundleplatform.baselayer.contant.ApiPath;
import com.anightswip.bundleplatform.commonlib.network.ApiManager;
import com.anightswip.bundleplatform.commonlib.network.BaseNetResponse;
import com.anightswip.bundleplatform.commonlib.network.Utils;
import com.jraska.livedata.TestObserver;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;

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
    }

    @Test
    public void httpGetTest() throws Exception {
        mockServer = new MockWebServer();
//        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
//                TrustManagerFactory.getDefaultAlgorithm());
//        trustManagerFactory.init((KeyStore) null);
//        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
//        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
//            throw new IllegalStateException("Unexpected default trust managers:"
//                    + Arrays.toString(trustManagers));
//        }
//        X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
//        SSLContext sslContext = SSLContext.getInstance("TLS");
//        sslContext.init(null, new TrustManager[]{trustManager}, null);
//        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
//        mockServer.useHttps(sslSocketFactory, false);
        final Dispatcher dispatcher = new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                switch (request.getPath()) {
                    case "/api/action/datastore_search?limit=10&resource_id=110":
                        return new MockResponse().setResponseCode(200).setBody("{\n" +
                                "    \"help\": \"https://data.gov.sg/api/3/action/help_show?name=datastore_search\",\n" +
                                "    \"success\": true,\n" +
                                "    \"result\": {\n" +
                                "        \"resource_id\": \"a807b7ab-6cad-4aa6-87d0-e283a7353a0f\",\n" +
                                "        \"fields\": [\n" +
                                "            {\n" +
                                "                \"type\": \"int4\",\n" +
                                "                \"id\": \"_id\"\n" +
                                "            },\n" +
                                "            {\n" +
                                "                \"type\": \"text\",\n" +
                                "                \"id\": \"quarter\"\n" +
                                "            },\n" +
                                "            {\n" +
                                "                \"type\": \"numeric\",\n" +
                                "                \"id\": \"volume_of_mobile_data\"\n" +
                                "            }\n" +
                                "        ],\n" +
                                "        \"records\": [\n" +
                                "            {\n" +
                                "                \"volume_of_mobile_data\": \"0.000384\",\n" +
                                "                \"quarter\": \"2004-Q3\",\n" +
                                "                \"_id\": 1\n" +
                                "            },\n" +
                                "            {\n" +
                                "                \"volume_of_mobile_data\": \"0.000543\",\n" +
                                "                \"quarter\": \"2004-Q4\",\n" +
                                "                \"_id\": 2\n" +
                                "            }\n" +
                                "        ],\n" +
                                "        \"limit\": 59,\n" +
                                "        \"total\": 59\n" +
                                "    }\n" +
                                "}");
                }
                return new MockResponse().setResponseCode(404);
            }
        };
        mockServer.setDispatcher(dispatcher);
        mockServer.start();
        String urlStr = mockServer.url("api/action/datastore_search").url().toString();
        HashMap<String, String> params = new HashMap<>();
        params.put("resource_id", "110");
        params.put("limit", "10");
        try {
            TestObserver.test(
                    ApiManager.getInstance().getAsLivedata(BeanMobileDataList.class, params, urlStr))
                    .awaitValue()
                    .assertHasValue()
                    .assertValue(new Function<BaseNetResponse<BeanMobileDataList>, Boolean>() {
                        @Override
                        public Boolean apply(BaseNetResponse<BeanMobileDataList> input) {
                            boolean result = input.errCode == BaseNetResponse.ERROR_DATA;
                            return result;
                        }
                    });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        RecordedRequest request1 = mockServer.takeRequest();
        assertEquals("/api/action/datastore_search?limit=10&resource_id=110",
                request1.getPath());
        mockServer.shutdown();
    }
}
