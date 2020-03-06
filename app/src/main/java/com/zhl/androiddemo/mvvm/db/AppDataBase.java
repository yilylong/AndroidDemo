package com.zhl.androiddemo.mvvm.db;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.zhl.androiddemo.mvvm.DAO.AddressDao;
import com.zhl.androiddemo.mvvm.DAO.CarDao;
import com.zhl.androiddemo.mvvm.DAO.PersonDao;
import com.zhl.androiddemo.mvvm.bean.Address;
import com.zhl.androiddemo.mvvm.bean.Car;
import com.zhl.androiddemo.mvvm.bean.Person;

/**
 * 描述：
 * Created by zhaohl on 2020/3/5.
 */
@Database(entities = {Person.class, Address.class, Car.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract PersonDao getPersonDao();

    public abstract AddressDao getAddressDao();

    public abstract CarDao getCarDao();

    public static final Migration migration1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE person ADD COLUMN age INTEGER NOT NULL DEFAULT 0");
        }
    };
    public static final Migration migration2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE person ADD COLUMN carId INTEGER NOT NULL DEFAULT 0");
            database.execSQL("CREATE TABLE [car](\n" +
                    "  carId INT PRIMARY KEY NOT NULL, \n" +
                    "  mode TEXT, \n" +
                    "  brand TEXT, \n" +
                    "  user_id INT REFERENCES person(id), \n" +
                    "  car_number TEXT)");
        }
    };

}
