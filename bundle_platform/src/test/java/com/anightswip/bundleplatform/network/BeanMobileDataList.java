package com.anightswip.bundleplatform.network;

import com.anightswip.bundleplatform.baselayer.Bean.BeanBase;
import com.anightswip.bundleplatform.baselayer.Bean.BeanBaseNetReponse;

import java.util.ArrayList;

public class BeanMobileDataList extends BeanBaseNetReponse {

    private ArrayList<BeanMobileData> records;

    public ArrayList<BeanMobileData> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<BeanMobileData> records) {
        this.records = records;
    }

    public static class BeanMobileData extends BeanBase {
        public String volume_of_mobile_data;
        public String quarter;
        public int _id;
    }
}
