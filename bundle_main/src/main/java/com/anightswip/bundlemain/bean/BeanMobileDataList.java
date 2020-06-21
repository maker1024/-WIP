package com.anightswip.bundlemain.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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

    @Entity(tableName = "mobile_data")
    public static class BeanMobileData extends BeanBase {
        @ColumnInfo
        public String volume_of_mobile_data;
        @ColumnInfo
        public String quarter;
        @PrimaryKey
        public int _id;
    }
}
