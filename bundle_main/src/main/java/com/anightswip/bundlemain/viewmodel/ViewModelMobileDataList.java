package com.anightswip.bundlemain.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.anightswip.bundlemain.bean.BeanMobileDataList;
import com.anightswip.bundlemain.repository.RepositoryMobileData;
import com.anightswip.bundleplatform.commonlib.network.BaseNetResponse;
import com.anightswip.bundleplatform.commonlib.viewmodel.PageViewModel;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * 列表fragment的viewmodel
 */
public class ViewModelMobileDataList extends PageViewModel {

    private MediatorLiveData<BViewMobileDataList> mUILiveData;

    public ViewModelMobileDataList() {
        mUILiveData = new MediatorLiveData<>();
    }

    public LiveData<BViewMobileDataList> getViewData() {
        return mUILiveData;
    }

    public void fetchMobileData() {
        RepositoryMobileData rp = new RepositoryMobileData();
        mUILiveData.addSource(rp.getAllMobileData(), new Observer<BaseNetResponse<BeanMobileDataList>>() {
            @Override
            public void onChanged(BaseNetResponse<BeanMobileDataList> response) {
                if (pageStatusNotify(response)) {
                    return;
                }
                BViewMobileDataList bv;
                if (mUILiveData.getValue() != null) {
                    bv = mUILiveData.getValue();
                } else {
                    bv = new BViewMobileDataList();
                }
                bv.setInfos(parseToViewBean(response.info.getRecords()));
                mUILiveData.setValue(bv);
            }
        });
    }

    //网络数据转界面数据
    private ArrayList<BViewMobileDataList.BViewMobileData> parseToViewBean(
            ArrayList<BeanMobileDataList.BeanMobileData> records) {
        ArrayList<BViewMobileDataList.BViewMobileData> bViewMobileDataArrayList = new ArrayList<>();
        BigDecimal preQuarterData = null;
        BigDecimal totalQuarterDataYear = null;
        String currentYear = null;
        BViewMobileDataList.BViewMobileData tempBv = null;
        for (BeanMobileDataList.BeanMobileData tempBean : records) {
            String[] yearAndQuarter = tempBean.quarter.trim().split("-");
            BigDecimal currentQuarterData = new BigDecimal(tempBean.volume_of_mobile_data);
            if (TextUtils.equals(currentYear, yearAndQuarter[0])) {
                //如果是当前年度，比较季度数据，增加总数据
                if (currentQuarterData.compareTo(preQuarterData) < 0) {
                    if (tempBv == null) tempBv = new BViewMobileDataList.BViewMobileData();
                    tempBv.setNeedShowClickImg(true);
                }
                preQuarterData = currentQuarterData;
                totalQuarterDataYear = totalQuarterDataYear.add(currentQuarterData);
            } else {
                //如果不是当前年度，换为当前年度，比较季度数据，总数据从新累加
                if (preQuarterData != null) {
                    //比较季度数据，跨年比较
                    if (tempBv == null) tempBv = new BViewMobileDataList.BViewMobileData();
                    tempBv.setItemTitle(currentYear + "Year: " + totalQuarterDataYear.toString() + "PB");
                    bViewMobileDataArrayList.add(tempBv);
                    tempBv = new BViewMobileDataList.BViewMobileData();
                    if (currentQuarterData.compareTo(preQuarterData) < 0)
                        tempBv.setNeedShowClickImg(true);
                }
                preQuarterData = currentQuarterData;
                currentYear = yearAndQuarter[0];
                totalQuarterDataYear = currentQuarterData;
            }
            if (records.indexOf(tempBean) == records.size() - 1) {
                //如果是最后一条数据
                if (tempBv == null) tempBv = new BViewMobileDataList.BViewMobileData();
                tempBv.setItemTitle(currentYear + "Year: " + totalQuarterDataYear.toString() + "PB");
                bViewMobileDataArrayList.add(tempBv);
            }
        }
        return bViewMobileDataArrayList;
    }
}
