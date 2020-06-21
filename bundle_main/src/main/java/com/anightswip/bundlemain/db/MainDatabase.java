package com.anightswip.bundlemain.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.anightswip.bundlemain.bean.BeanMobileDataList;

@Database(entities = {BeanMobileDataList.BeanMobileData.class}, version = 1)
public abstract class MainDatabase extends RoomDatabase {

    private static volatile MainDatabase INSTANCE;
    private static Context mAppContext;

    public static void init(Context appContext) {
        mAppContext = appContext;
    }

    public static MainDatabase getInstance(){
        if (INSTANCE == null) {
            synchronized (MainDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room
                            .databaseBuilder(mAppContext, MainDatabase.class, "WIP_DB")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract DaoMobileData mobileDataDao();
}
