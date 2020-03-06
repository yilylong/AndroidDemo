package com.zhl.androiddemo.mvvm.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * 描述：
 * Created by zhaohl on 2020/3/6.
 */
@Entity(tableName = "car",foreignKeys = @ForeignKey(entity = Person.class,parentColumns = "id",childColumns = "user_id"))
public class Car {
    @PrimaryKey(autoGenerate = true)
    public int carId;

    public String mode;

    public String brand;

    @ColumnInfo(name = "user_id")
    public int userId;

    @ColumnInfo(name = "car_number")
    public String carNum;
}
