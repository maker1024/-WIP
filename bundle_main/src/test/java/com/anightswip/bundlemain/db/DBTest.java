package com.anightswip.bundlemain.db;

import android.content.Context;
import android.os.Build;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.arch.core.util.Function;
import androidx.room.Room;

import com.anightswip.bundlemain.bean.BeanMobileDataList;
import com.jraska.livedata.TestObserver;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.O_MR1)
public class DBTest {

    @Rule
    public InstantTaskExecutorRule testRule = new InstantTaskExecutorRule();

    @Test
    public void writeUserAndReadInList() throws Exception {
        Context context = RuntimeEnvironment.systemContext;
        MainDatabase.init(context);
//        db = Room.inMemoryDatabaseBuilder(context, MainDatabase.class).build();
        final DaoMobileData dao = MainDatabase.getInstance().mobileDataDao();//db.mobileDataDao();
        final BeanMobileDataList.BeanMobileData mobileData = new BeanMobileDataList.BeanMobileData();
        mobileData.quarter = "2020-Q3";
        mobileData.volume_of_mobile_data = "0.008768";
        mobileData._id = 1;
        new Thread(new Runnable() {
            @Override
            public void run() {
                dao.insertAll(mobileData);
                try {
                    TestObserver.test(dao.getAll())
                            .awaitValue()
                            .assertHasValue()
                            .assertValue(new Function<List<BeanMobileDataList.BeanMobileData>, Boolean>() {
                                @Override
                                public Boolean apply(List<BeanMobileDataList.BeanMobileData> input) {
                                    if(input==null){
                                        return false;
                                    }
                                    if(input.get(0)==null){
                                        return false;
                                    }
                                    if(!input.get(0).quarter.equals(mobileData.quarter)){
                                        return false;
                                    }
                                    if(!input.get(0).volume_of_mobile_data.equals(mobileData.volume_of_mobile_data)){
                                        return false;
                                    }
                                    if(input.get(0)._id != 1){
                                        return false;
                                    }
                                    return input.size() == 1;
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

        MainDatabase.getInstance().close();
    }
}
