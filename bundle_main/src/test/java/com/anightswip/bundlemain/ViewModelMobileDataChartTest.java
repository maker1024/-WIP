package com.anightswip.bundlemain;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.anightswip.bundlemain.bean.BeanMobileDataList;
import com.anightswip.bundlemain.repository.RepositoryMobileData;
import com.anightswip.bundlemain.viewmodel.BViewMobileDataChart;
import com.anightswip.bundlemain.viewmodel.ViewModelMobileDataChart;
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
@PrepareForTest({ViewModelMobileDataChart.class, RepositoryMobileData.class})
public class ViewModelMobileDataChartTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public InstantTaskExecutorRule testRule = new InstantTaskExecutorRule();

    @Mock
    private RepositoryMobileData repositoryMobileData;

//    @InjectMocks
//    private ViewModelMobileDataChart viewModel;

    @Test
    public void vewModelMobileDataChartTestNormal() throws Exception {
        MediatorLiveData<BaseNetResponse<BeanMobileDataList>> ldRepo = new MediatorLiveData<>();

        BeanMobileDataList beanMobileDataList = new BeanMobileDataList();
        ArrayList<BeanMobileDataList.BeanMobileData> beanMobileDataArrayList = new ArrayList<>();
        BeanMobileDataList.BeanMobileData beanMobileData = new BeanMobileDataList.BeanMobileData();
        beanMobileData._id = 1;
        beanMobileData.volume_of_mobile_data = "0.8876";
        beanMobileData.quarter = "2010-Q3";
        beanMobileDataArrayList.add(beanMobileData);
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

        ViewModelMobileDataChart viewModel = new ViewModelMobileDataChart();
        viewModel.fetchMobileData();

        LiveData<BViewMobileDataChart> ld = viewModel.getViewData();
        TestObserver.test(ld)
                .awaitValue()
                .assertHasValue()
                .assertValue(new Function<BViewMobileDataChart, Boolean>() {
                    @Override
                    public Boolean apply(BViewMobileDataChart input) {
                        boolean result = (input.getChartsInfo().size() == 1);
                        return result;
                    }
                });
    }

    @Test
    public void vewModelMobileDataChartTesNull() throws Exception {
        MediatorLiveData<BaseNetResponse<BeanMobileDataList>> ldRepo = new MediatorLiveData<>();
        ldRepo.setValue(null);

        when(repositoryMobileData.getAllMobileData()).thenReturn(ldRepo);

        PowerMockito.mockStatic(RepositoryMobileData.class);
        PowerMockito.whenNew(RepositoryMobileData.class).withNoArguments().thenReturn(repositoryMobileData);

        ViewModelMobileDataChart viewModel = new ViewModelMobileDataChart();
        viewModel.fetchMobileData();

        LiveData<BViewMobileDataChart> ld = viewModel.getViewData();
        TestObserver.test(ld).assertNoValue();
    }
}