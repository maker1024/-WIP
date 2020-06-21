package com.anightswip.bundlemain.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.anightswip.bundlemain.bean.BeanMobileDataList;
import com.anightswip.bundlemain.db.DaoMobileData;

import java.util.ArrayList;
import java.util.List;

public class DaoMobileDataImpl2 implements DaoMobileData {

    private boolean hasInsert = false;

    public DaoMobileDataImpl2() {
    }

    @Override
    public LiveData<List<BeanMobileDataList.BeanMobileData>> getAll() {
        MediatorLiveData<List<BeanMobileDataList.BeanMobileData>> ldDb = new MediatorLiveData<>();
        if (hasInsert) {
            List<BeanMobileDataList.BeanMobileData> dbList = new ArrayList<>();
            BeanMobileDataList.BeanMobileData beanMobileData0 = new BeanMobileDataList.BeanMobileData();
            beanMobileData0._id = 1;
            beanMobileData0.volume_of_mobile_data = "0.1876";
            beanMobileData0.quarter = "2010-Q4";
            dbList.add(beanMobileData0);
            ldDb.setValue(dbList);
            return ldDb;
        }
        ldDb.setValue(new ArrayList<BeanMobileDataList.BeanMobileData>());
        return ldDb;
    }

    @Override
    public void insertAll(BeanMobileDataList.BeanMobileData... mobileData) {
        hasInsert = true;
    }

    @Override
    public void update(BeanMobileDataList.BeanMobileData... mobileData) {

    }

    @Override
    public void delete(BeanMobileDataList.BeanMobileData mobileData) {

    }

    @Override
    public void deleteAll() {

    }
}
