package com.zhl.androiddemo.mvvm.db;

import androidx.room.Room;

import com.zhl.androiddemo.MyApplication;

/**
 * 描述：
 * Created by zhaohl on 2020/3/5.
 */
public class DBInstance {
    private static final String DB_NAME = "room_db";
    public static AppDataBase appDataBase;

    public static AppDataBase getInstance(){
        if(appDataBase==null){
            synchronized (DBInstance.class){
                if(appDataBase==null){
                    appDataBase = Room.databaseBuilder(MyApplication.getInstance(),AppDataBase.class, DB_NAME)
                            .allowMainThreadQueries()
//                            .addMigrations(AppDataBase.migration1_2)
//                            .addMigrations(AppDataBase.migration2_3)
                            .build();
                }
            }
        }
        return appDataBase;
    }

}
