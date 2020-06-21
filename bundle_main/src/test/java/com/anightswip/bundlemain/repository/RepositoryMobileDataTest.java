package com.anightswip.bundlemain.repository;

import android.os.Build;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;

import com.anightswip.bundlemain.bean.BeanMobileDataList;
import com.anightswip.bundleplatform.commonlib.executor.AppExecutors;
import com.anightswip.bundleplatform.commonlib.executor.UIThreadExecutor;
import com.anightswip.bundleplatform.commonlib.network.BaseNetResponse;
import com.jraska.livedata.TestObserver;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.concurrent.Executors;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.O_MR1)
public class RepositoryMobileDataTest {

    @Rule
    public InstantTaskExecutorRule testRule = new InstantTaskExecutorRule();

    @Before
    public void setUp(){
//        AppExecutors.init();
    }

    @Test
    public void RepositoryMobileDatagetAllMobileDataTest() throws Exception {
        RepositoryMobileData repositoryMobileData =
                new RepositoryMobileData(new DaoMobileDataImpl(), new MobileDataBusinessImplTest());

        LiveData<BaseNetResponse<BeanMobileDataList>> ld = repositoryMobileData.getAllMobileData();
        TestObserver.test(ld)
                .awaitValue()
                .assertHasValue()
                .assertValue(new Function<BaseNetResponse<BeanMobileDataList>, Boolean>() {
                    @Override
                    public Boolean apply(BaseNetResponse<BeanMobileDataList> input) {
                        boolean result = input.hasError == false;
                        return result;
                    }
                });
    }

//    @Test
//    public void RepositoryMobileDatagetAllMobileDataTestDbEmpty() throws Exception {
//        RepositoryMobileData repositoryMobileData =
//                new RepositoryMobileData(new DaoMobileDataImpl2(), new MobileDataBusinessImplTest());
//
//        LiveData<BaseNetResponse<BeanMobileDataList>> ld = repositoryMobileData.getAllMobileData();
//        TestObserver.test(ld)
//                .awaitValue()
//                .assertHasValue()
//                .assertValue(new Function<BaseNetResponse<BeanMobileDataList>, Boolean>() {
//                    @Override
//                    public Boolean apply(BaseNetResponse<BeanMobileDataList> input) {
//                        boolean result = input.hasError == false;
//                        return result;
//                    }
//                });
//    }

}