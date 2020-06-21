package com.anightswip.bundleplatform.commonlib.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.anightswip.bundleplatform.commonlib.network.BaseNetResponse;
import com.anightswip.bundleplatform.commonlib.page.BasePageStatus;
import com.anightswip.bundleplatform.network.BeanMobileDataList;
import com.jraska.livedata.TestObserver;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PageViewModel.class, MutableLiveData.class})
public class PageViewModelTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public InstantTaskExecutorRule testRule = new InstantTaskExecutorRule();

    @Test
    public void PageViewModelTestNormal() throws Exception {
        PageViewModel pageViewModel = new PageViewModel();

        BeanMobileDataList beanMobileDataList = new BeanMobileDataList();
        BaseNetResponse<BeanMobileDataList> bean = BaseNetResponse.success(beanMobileDataList);

        assertFalse(pageViewModel.pageStatusNotify(bean));

        LiveData<BasePageStatus> ldPage = pageViewModel.getPageStatus();
        TestObserver.test(ldPage)
                .awaitValue()
                .assertHasValue()
                .assertValue(new Function<BasePageStatus, Boolean>() {
                    @Override
                    public Boolean apply(BasePageStatus input) {
                        return input.status == BasePageStatus.PAGE_NORMAL;
                    }
                });
    }

    @Test
    public void PageViewModelTestException() throws Exception {
        PageViewModel pageViewModel = new PageViewModel();

        assertTrue(pageViewModel.pageStatusNotify(null));

        LiveData<BasePageStatus> ldPage = pageViewModel.getPageStatus();
        TestObserver.test(ldPage)
                .awaitValue()
                .assertHasValue()
                .assertValue(new Function<BasePageStatus, Boolean>() {
                    @Override
                    public Boolean apply(BasePageStatus input) {
                        return input.status == BasePageStatus.PAGE_ERROR_DATA;
                    }
                });
    }

    @Test
    public void PageViewModelTestNoData() throws Exception {
        PageViewModel pageViewModel = new PageViewModel();

        BaseNetResponse<BeanMobileDataList> bean = new BaseNetResponse<>();
        bean.info = null;
        bean.hasError = false;

        assertTrue(pageViewModel.pageStatusNotify(bean));

        LiveData<BasePageStatus> ldPage = pageViewModel.getPageStatus();
        TestObserver.test(ldPage)
                .awaitValue()
                .assertHasValue()
                .assertValue(new Function<BasePageStatus, Boolean>() {
                    @Override
                    public Boolean apply(BasePageStatus input) {
                        return input.status == BasePageStatus.PAGE_NO_DATA;
                    }
                });
    }

    @Test
    public void PageViewModelTestErrorData() throws Exception {
        PageViewModel pageViewModel = new PageViewModel();

        BaseNetResponse<BeanMobileDataList> bean = BaseNetResponse.errorData();

        assertTrue(pageViewModel.pageStatusNotify(bean));

        LiveData<BasePageStatus> ldPage = pageViewModel.getPageStatus();
        TestObserver.test(ldPage)
                .awaitValue()
                .assertHasValue()
                .assertValue(new Function<BasePageStatus, Boolean>() {
                    @Override
                    public Boolean apply(BasePageStatus input) {
                        return input.status == BasePageStatus.PAGE_ERROR_DATA;
                    }
                });
    }

    @Test
    public void PageViewModelTestNoNetWork() throws Exception {
        PageViewModel pageViewModel = new PageViewModel();

        BaseNetResponse<BeanMobileDataList> bean = BaseNetResponse.errorNetwork();

        assertTrue(pageViewModel.pageStatusNotify(bean));

        LiveData<BasePageStatus> ldPage = pageViewModel.getPageStatus();
        TestObserver.test(ldPage)
                .awaitValue()
                .assertHasValue()
                .assertValue(new Function<BasePageStatus, Boolean>() {
                    @Override
                    public Boolean apply(BasePageStatus input) {
                        return input.status == BasePageStatus.PAGE_NO_NETWORK;
                    }
                });
    }

}