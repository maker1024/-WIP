package com.anightswip.bundlemain;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.anightswip.bundlemain.bean.BeanMobileDataList;
import com.anightswip.bundlemain.repository.RepositoryMobileData;
import com.anightswip.bundlemain.viewmodel.BViewMobileDataList;
import com.anightswip.bundlemain.viewmodel.ViewModelMobileDataList;
import com.anightswip.bundleplatform.commonlib.network.BaseNetResponse;
import com.jraska.livedata.TestObserver;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ViewModelMobileDataList.class, RepositoryMobileData.class})
public class ViewModelMobileDataListTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public InstantTaskExecutorRule testRule = new InstantTaskExecutorRule();

    @Mock
    private RepositoryMobileData repositoryMobileData;

    @Test
    public void vewModelMobileDataListTestNormal() throws Exception {
        MediatorLiveData<BaseNetResponse<BeanMobileDataList>> ldRepo = new MediatorLiveData<>();
//
//        PowerMockito.spy(TextUtils.class);
//        PowerMockito.when(TextUtils.equals(Mockito.anyString(), Mockito.anyString())).thenCallRealMethod();
//        PowerMockito.when(TextUtils.isEmpty(Mockito.anyString())).thenCallRealMethod();

        BeanMobileDataList beanMobileDataList = new BeanMobileDataList();
        ArrayList<BeanMobileDataList.BeanMobileData> beanMobileDataArrayList = new ArrayList<>();
        BeanMobileDataList.BeanMobileData beanMobileData = new BeanMobileDataList.BeanMobileData();
        beanMobileData._id = 1;
        beanMobileData.volume_of_mobile_data = "0.8876";
        beanMobileData.quarter = "2010-Q3";
        beanMobileDataArrayList.add(beanMobileData);
        BeanMobileDataList.BeanMobileData beanMobileData2 = new BeanMobileDataList.BeanMobileData();
        beanMobileData2._id = 2;
        beanMobileData2.volume_of_mobile_data = "0.1076";
        beanMobileData2.quarter = "2010-Q4";
        beanMobileDataArrayList.add(beanMobileData2);
        BeanMobileDataList.BeanMobileData beanMobileData3 = new BeanMobileDataList.BeanMobileData();
        beanMobileData3._id = 3;
        beanMobileData3.volume_of_mobile_data = "0.0999";
        beanMobileData3.quarter = "2011-Q1";
        beanMobileDataArrayList.add(beanMobileData3);
        beanMobileDataList.setRecords(beanMobileDataArrayList);
        BaseNetResponse<BeanMobileDataList> bean = BaseNetResponse.success(beanMobileDataList);
        ldRepo.setValue(bean);

        when(repositoryMobileData.getAllMobileData()).thenReturn(ldRepo);

//
//        MemberModifier
//                .field(ViewModelMobileDataChart.class, "mRepositoryMobileData")
//                .set(viewModel, repositoryMobileData);

//        PowerMockito.when(PowerMockito.spy(viewModel), "mRepositoryMobileData")
//                .thenReturn(repositoryMobileData);

        PowerMockito.mockStatic(RepositoryMobileData.class);
        PowerMockito.whenNew(RepositoryMobileData.class).withNoArguments().thenReturn(repositoryMobileData);

        ViewModelMobileDataList viewModel = new ViewModelMobileDataList();
        viewModel.fetchMobileData();

        LiveData<BViewMobileDataList> ld = viewModel.getViewData();
        TestObserver.test(ld)
                .awaitValue()
                .assertHasValue()
                .assertValue(new Function<BViewMobileDataList, Boolean>() {
                    @Override
                    public Boolean apply(BViewMobileDataList input) {
                        boolean result = (input.getInfos().size() == 2);
                        if (!result) {
                            return false;
                        }
                        result = input.getInfos().get(0).isNeedShowClickImg();
                        if (!result) {
                            return false;
                        }
                        result = input.getInfos().get(1).isNeedShowClickImg();
                        if (!result) {
                            return false;
                        }
                        return true;
                    }
                });
    }

    @Test
    public void vewModelMobileDataListTesNull() throws Exception {
        MediatorLiveData<BaseNetResponse<BeanMobileDataList>> ldRepo = new MediatorLiveData<>();
        ldRepo.setValue(null);

        when(repositoryMobileData.getAllMobileData()).thenReturn(ldRepo);

        PowerMockito.mockStatic(RepositoryMobileData.class);
        PowerMockito.whenNew(RepositoryMobileData.class).withNoArguments().thenReturn(repositoryMobileData);

        ViewModelMobileDataList viewModel = new ViewModelMobileDataList();
        viewModel.fetchMobileData();

        LiveData<BViewMobileDataList> ld = viewModel.getViewData();
        TestObserver.test(ld).assertNoValue();
    }
}