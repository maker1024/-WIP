package com.anightswip.bundleplatform.utils;

import com.anightswip.bundleplatform.commonlib.utils.GsonUtil;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class GsonTest {

    private String mJsonStr;

    @Before
    public void setUp() {
        mJsonStr = "{\n" +
                "    \"success\": true,\n" +
                "    \"num\": 321,\n" +
                "    \"msg\": \"this is message\",\n" +
                "    \"array\": [\n" +
                "        {\n" +
                "            \"successNest\": false,\n" +
                "            \"numNest\": 1,\n" +
                "            \"msgNest\": \"this is nest message 1\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"successNest\": true,\n" +
                "            \"numNest\": 2,\n" +
                "            \"msgNest\": \"this is nest message 2\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
    }

    @Test
    public void goTestNormal() {

        GsonTestBean gsonBean = GsonUtil.getReponseBean(mJsonStr, GsonTestBean.class);
        assertNotNull(gsonBean);

        assertTrue(gsonBean.isSuccess());
        assertEquals(321, gsonBean.getNum());
        assertEquals("this is message", gsonBean.getMsg());
        assertNotNull(gsonBean.getArray());
        assertEquals(2, gsonBean.getArray().size());

        assertNotNull(gsonBean.getArray().get(0));
        assertFalse(gsonBean.getArray().get(0).isSuccessNest());
        assertEquals(1, gsonBean.getArray().get(0).getNumNest());
        assertEquals("this is nest message 1", gsonBean.getArray().get(0).getMsgNest());

        assertNotNull(gsonBean.getArray().get(1));
        assertTrue(gsonBean.getArray().get(1).isSuccessNest());
        assertEquals(2, gsonBean.getArray().get(1).getNumNest());
        assertEquals("this is nest message 2", gsonBean.getArray().get(1).getMsgNest());
    }

    @Test
    public void goTestException() {
        GsonTestBean gsonBean = GsonUtil.getReponseBean("", GsonTestBean.class);
        assertNull(gsonBean);

        gsonBean = GsonUtil.getReponseBean("", null);
        assertNull(gsonBean);

        gsonBean = GsonUtil.getReponseBean("123", GsonTestBean.class);
        assertNull(gsonBean);

        gsonBean = GsonUtil.getReponseBean(null, GsonTestBean.class);
        assertNull(gsonBean);
    }

}
