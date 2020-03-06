package com.zhl.androiddemo.mvvm.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.zhl.androiddemo.mvvm.bean.Car;

import java.util.List;

/**
 * 描述：
 * Created by zhaohl on 2020/3/6.
 */
@Dao
public interface CarDao {

    @Query("SELECT * FROM car WHERE carId=:id")
    public Car getCarByid(int id);

    @Query("SELECT * FROM car WHERE user_id=:uid")
    public Car getCarByUid(int uid);

    @Query("SELECT * FROM car WHERE mode=:mode")
    public List<Car> getCarByMode(String mode);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Car... cars);

    //一次删除单条数据 或 多条
    @Delete
    public void delete(Car... cars);

    //一次更新单条数据 或 多条
    @Update
    public void update(Car... cars);
}
