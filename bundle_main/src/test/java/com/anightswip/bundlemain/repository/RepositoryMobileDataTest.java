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

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.O_MR1)
public class RepositoryMobileDataTest {

    @Rule
    public InstantTaskExecutorRule testRule = new InstantTaskExecutorRule();

    @Test
    public void RepositoryMobileDatagetAllMobileDataTest() throws Exception {
        if (!AppExecutors.hasInit()) {
            AppExecutors.init(new UIThreadExecutor(RuntimeEnvironment.systemContext.getMainLooper())
                    , new UIThreadExecutor(RuntimeEnvironment.systemContext.getMainLooper()));
        }
        RepositoryMobileData repositoryMobileData =
                new RepositoryMobileData(new DaoMobileDataImpl(), new MobileDataBusinessImplTestCaseNetSuccess());

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

    @Test
    public void RepositoryMobileDatagetAllMobileDataTestDbEmpty() throws Exception {
        if (!AppExecutors.hasInit()) {
            AppExecutors.init(new UIThreadExecutor(RuntimeEnvironment.systemContext.getMainLooper())
                    , new UIThreadExecutor(RuntimeEnvironment.systemContext.getMainLooper()));
        }
        RepositoryMobileData repositoryMobileData =
                new RepositoryMobileData(new DaoMobileDataImpl2(), new MobileDataBusinessImplTestCaseNetSuccess());

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

    @Test
    public void RepositoryMobileDatagetAllMobileDataTestNetError() throws Exception {
        if (!AppExecutors.hasInit()) {
            AppExecutors.init(new UIThreadExecutor(RuntimeEnvironment.systemContext.getMainLooper())
                    , new UIThreadExecutor(RuntimeEnvironment.systemContext.getMainLooper()));
        }
        RepositoryMobileData repositoryMobileData =
                new RepositoryMobileData(new DaoMobileDataImpl2(), new MobileDataBusinessImplTestCaseNetError());

        LiveData<BaseNetResponse<BeanMobileDataList>> ld = repositoryMobileData.getAllMobileData();
        TestObserver.test(ld)
                .awaitValue()
                .assertHasValue()
                .assertValue(new Function<BaseNetResponse<BeanMobileDataList>, Boolean>() {
                    @Override
                    public Boolean apply(BaseNetResponse<BeanMobileDataList> input) {
                        if (!input.hasError) return false;
                        return true;
                    }
                });
    }

}