package com.anightswip.bundleplatform.commonlib.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anightswip.bundleplatform.commonlib.network.BaseNetResponse;
import com.anightswip.bundleplatform.commonlib.page.BasePageStatus;

/**
 * 通用页面viewmodel，可以获取页面状态通知
 */
public class PageViewModel extends ViewModel {

    final protected MutableLiveData<BasePageStatus> mPageStatus;

    public PageViewModel() {
        mPageStatus = new MediatorLiveData<>();
    }

    public LiveData<BasePageStatus> getPageStatus() {
        return mPageStatus;
    }

    /**
     * 发送页面状态信息
     *
     * @param response 应答信息
     * @return true 应答success, false 应答error
     */
    public boolean pageStatusNotify(BaseNetResponse response) {
        if (response == null) {
            mPageStatus.setValue(BasePageStatus.errorData("数据错误"));
            return true;
        }
        if (response.hasError) {
            switch (response.errCode) {
                case BaseNetResponse.ERROR_DATA:
                case BaseNetResponse.SERVICE_ERROR:
                    mPageStatus.setValue(BasePageStatus.errorData(response.errMsg));
                    break;
                case BaseNetResponse.NO_NETWORK:
                    mPageStatus.setValue(BasePageStatus.noNetwork(response.errMsg));
                    break;
            }
            return true;
        }
        if (response.info == null) {
            mPageStatus.setValue(BasePageStatus.noData(response.errMsg));
            return true;
        }
        mPageStatus.setValue(BasePageStatus.normal(response.errMsg));
        return false;
    }
}
