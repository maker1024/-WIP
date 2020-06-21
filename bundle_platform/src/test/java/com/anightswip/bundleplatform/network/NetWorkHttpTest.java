package com.anightswip.bundleplatform.network;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.arch.core.util.Function;

import com.anightswip.bundleplatform.baselayer.contant.ApiPath;
import com.anightswip.bundleplatform.baselayer.network.HttpResponseParser;
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
        mockServer = new MockWebServer();
        final Dispatcher dispatcher = new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                switch (request.getPath()) {
                    case "/api/action/datastore_search?limit=4&resource_id=120":
                        return new MockResponse().setResponseCode(404);
                    case "/api/action/datastore_search?limit=5&resource_id=121":
                        return new MockResponse().setResponseCode(200).setBody("");
                    case "/api/action/datastore_search?limit=6&resource_id=122":
                        return new MockResponse().setResponseCode(200).setBody("{\"success\": false}");
                    case "/api/action/datastore_search?limit=7&resource_id=123":
                        return new MockResponse().setResponseCode(200).setBody("luma");
                    case "/api/action/datastore_search?limit=3&resource_id=119":
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
                                "            },\n" +
                                "            {\n" +
                                "                \"volume_of_mobile_data\": \"0.00062\",\n" +
                                "                \"quarter\": \"2005-Q1\",\n" +
                                "                \"_id\": 3\n" +
                                "            }\n" +
                                "        ],\n" +
                                "        \"_links\": {\n" +
                                "            \"start\": \"/api/action/datastore_search?limit=59&resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f\",\n" +
                                "            \"next\": \"/api/action/datastore_search?offset=59&limit=59&resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f\"\n" +
                                "        },\n" +
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
    }

    @Test
    public void httpGetTest() throws Exception {
        String urlStr = mockServer.url("api/action/datastore_search").url().toString();
//        HashMap<String, String> params = new HashMap<>();
//        params.put("resource_id", "110");
//        params.put("limit", "10");
//
//        ApiManager.getInstance().getAsCallback(params, urlStr, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                assertNotNull(e);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                assertEquals("this is a json", response.body().string());
//            }
//        });
//
//        RecordedRequest request1 = mockServer.takeRequest();
//        assertEquals("/api/action/datastore_search?limit=10&resource_id=110",
//                request1.getPath());

        ApiManager.getInstance().init(new HttpResponseParser());
        HashMap<String, String> params2 = new HashMap<>();
        params2.put("resource_id", "119");
        params2.put("limit", "3");

        TestObserver.test(
                ApiManager.getInstance().getAsLivedata(BeanMobileDataList.class, params2, urlStr))
                .awaitValue()
                .assertHasValue()
                .assertValue(new Function<BaseNetResponse<BeanMobileDataList>, Boolean>() {
                    @Override
                    public Boolean apply(BaseNetResponse<BeanMobileDataList> input) {
                        boolean result = (input.info != null);
                        return result;
                    }
                });

        HashMap<String, String> params3 = new HashMap<>();
        params3.put("resource_id", "120");
        params3.put("limit", "4");

        TestObserver.test(
                ApiManager.getInstance().getAsLivedata(BeanMobileDataList.class, params3, urlStr))
                .awaitValue()
                .assertHasValue()
                .assertValue(new Function<BaseNetResponse<BeanMobileDataList>, Boolean>() {
                    @Override
                    public Boolean apply(BaseNetResponse<BeanMobileDataList> input) {
                        boolean result = (input.errCode == BaseNetResponse.SERVICE_ERROR);
                        return result;
                    }
                });

        HashMap<String, String> params4 = new HashMap<>();
        params4.put("resource_id", "121");
        params4.put("limit", "5");

        TestObserver.test(
                ApiManager.getInstance().getAsLivedata(BeanMobileDataList.class, params4, urlStr))
                .awaitValue()
                .assertHasValue()
                .assertValue(new Function<BaseNetResponse<BeanMobileDataList>, Boolean>() {
                    @Override
                    public Boolean apply(BaseNetResponse<BeanMobileDataList> input) {
                        boolean result = (input.errCode == BaseNetResponse.ERROR_DATA);
                        return result;
                    }
                });

        HashMap<String, String> params5 = new HashMap<>();
        params5.put("resource_id", "122");
        params5.put("limit", "6");

        TestObserver.test(
                ApiManager.getInstance().getAsLivedata(BeanMobileDataList.class, params5, urlStr))
                .awaitValue()
                .assertHasValue()
                .assertValue(new Function<BaseNetResponse<BeanMobileDataList>, Boolean>() {
                    @Override
                    public Boolean apply(BaseNetResponse<BeanMobileDataList> input) {
                        boolean result = (input.errCode == BaseNetResponse.ERROR_DATA);
                        return result;
                    }
                });

        HashMap<String, String> params6 = new HashMap<>();
        params6.put("resource_id", "123");
        params6.put("limit", "7");

        TestObserver.test(
                ApiManager.getInstance().getAsLivedata(BeanMobileDataList.class, params6, urlStr))
                .awaitValue()
                .assertHasValue()
                .assertValue(new Function<BaseNetResponse<BeanMobileDataList>, Boolean>() {
                    @Override
                    public Boolean apply(BaseNetResponse<BeanMobileDataList> input) {
                        boolean result = (input.errCode == BaseNetResponse.SERVICE_ERROR);
                        return result;
                    }
                });

        mockServer.shutdown();

        TestObserver.test(
                ApiManager.getInstance().getAsLivedata(BeanMobileDataList.class, params6, urlStr))
                .awaitValue()
                .assertHasValue()
                .assertValue(new Function<BaseNetResponse<BeanMobileDataList>, Boolean>() {
                    @Override
                    public Boolean apply(BaseNetResponse<BeanMobileDataList> input) {
                        boolean result = (input.errCode == BaseNetResponse.NO_NETWORK);
                        return result;
                    }
                });

//        mockServer.shutdown();
    }

//    public <T> T getOrAwaitValue(final LiveData<T> liveData) throws InterruptedException {
//        final Object[] data = new Object[1];
//        final CountDownLatch latch = new CountDownLatch(1);
//        Observer<T> observer = new Observer<T>() {
//            @Override
//            public void onChanged(@Nullable T o) {
//                data[0] = o;
//                latch.countDown();
//                liveData.removeObserver(this);
//            }
//        };
//        liveData.observeForever(observer);
//        latch.await(2, TimeUnit.SECONDS);
//        //noinspection unchecked
//        return (T) data[0];
//    }
}
