package com.anightswip.bundlemain.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.anightswip.bundlemain.bean.BeanMobileDataList;

import java.util.List;

@Dao
public interface DaoMobileData {
    @Query("SELECT * FROM mobile_data")
    LiveData<List<BeanMobileDataList.BeanMobileData>> getAll();

    @Insert
    void insertAll(BeanMobileDataList.BeanMobileData... mobileData);

    @Update
    public void update(BeanMobileDataList.BeanMobileData... mobileData);

    @Delete
    void delete(BeanMobileDataList.BeanMobileData mobileData);

    @Query("delete from mobile_data")
    void deleteAll();
}
