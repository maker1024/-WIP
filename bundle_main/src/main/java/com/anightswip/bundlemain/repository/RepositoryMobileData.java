package com.anightswip.bundlemain.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.anightswip.bundlemain.bean.BeanMobileDataList;
import com.anightswip.bundlemain.db.DaoMobileData;
import com.anightswip.bundlemain.db.MainDatabase;
import com.anightswip.bundleplatform.baselayer.contant.ApiPath;
import com.anightswip.bundleplatform.commonlib.executor.AppExecutors;
import com.anightswip.bundleplatform.commonlib.network.ApiManager;
import com.anightswip.bundleplatform.commonlib.network.BaseNetResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 获取Mobile Data的数据仓库
 */
public class RepositoryMobileData {

    private DaoMobileData mDaoMobileData;
    private MediatorLiveData<BaseNetResponse<BeanMobileDataList>> mResultLd;

    public RepositoryMobileData() {
        mDaoMobileData = MainDatabase.getInstance().mobileDataDao();
        mResultLd = new MediatorLiveData<>();
    }

    /**
     * 获取数据的简单逻辑:
     * |-数据库(为空)->网络(成功)->数据库(写入)->数据库(有数据)->界面(成功)
     * |-数据库(为空)->网络(错误)->界面(错误)
     * |-数据库(有数据)->界面(成功)
     *
     * @return livedata
     */
    public LiveData<BaseNetResponse<BeanMobileDataList>> getAllMobileData() {
        //数据库获取
        final LiveData<List<BeanMobileDataList.BeanMobileData>> dbLd = mDaoMobileData.getAll();
        mResultLd.addSource(dbLd, new Observer<List<BeanMobileDataList.BeanMobileData>>() {
            @Override
            public void onChanged(List<BeanMobileDataList.BeanMobileData> beanMobileData) {
                mResultLd.removeSource(dbLd);
                final BeanMobileDataList beanMobileDataListResult = new BeanMobileDataList();
                if (beanMobileData == null || beanMobileData.isEmpty()) {
                    //数据为空，就网络获取
                    final LiveData<BaseNetResponse<BeanMobileDataList>> netLd = fetchMobileDataNet();
                    mResultLd.addSource(netLd, new Observer<BaseNetResponse<BeanMobileDataList>>() {
                        @Override
                        public void onChanged(final BaseNetResponse<BeanMobileDataList> beanMobileDataListBaseNetResponse) {
                            mResultLd.removeSource(netLd);
                            if (!beanMobileDataListBaseNetResponse.hasError) {
                                //如果没错就存数据库,并从数据库返回数据给界面
                                AppExecutors.workThread().execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        mDaoMobileData.insertAll(
                                                beanMobileDataListBaseNetResponse.info.getRecords()
                                                        .toArray(new BeanMobileDataList.BeanMobileData[0]));
                                        AppExecutors.uiThread().execute(new Runnable() {
                                            @Override
                                            public void run() {
                                                final LiveData<List<BeanMobileDataList.BeanMobileData>> dbLd2 = mDaoMobileData.getAll();
                                                mResultLd.addSource(dbLd2, new Observer<List<BeanMobileDataList.BeanMobileData>>() {
                                                    @Override
                                                    public void onChanged(List<BeanMobileDataList.BeanMobileData> beanMobileData) {
                                                        mResultLd.removeSource(dbLd2);
                                                        beanMobileDataListResult.setRecords(new ArrayList<>(beanMobileData));
                                                        mResultLd.setValue(BaseNetResponse.success(beanMobileDataListResult));
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                                return;
                            }
                            //有错就传递错误信息给界面
                            mResultLd.setValue(beanMobileDataListBaseNetResponse);
                        }
                    });
                } else {
                    //数据传递给界面
                    beanMobileDataListResult.setRecords(new ArrayList<>(beanMobileData));
                    mResultLd.setValue(BaseNetResponse.success(beanMobileDataListResult));
                }
            }
        });
        return mResultLd;
    }

    //获取网络数据
    private LiveData<BaseNetResponse<BeanMobileDataList>> fetchMobileDataNet() {
        HashMap<String, String> params = new HashMap<>();
        params.put("resource_id", "a807b7ab-6cad-4aa6-87d0-e283a7353a0f");
        params.put("limit", "59");
        return ApiManager.getInstance().getAsLivedata(BeanMobileDataList.class, params, ApiPath.DATASTORE_SEARCH);
    }
}
