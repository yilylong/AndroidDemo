package com.zhl.androiddemo.mvvm.bean;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * 描述：
 * @Entity ： 数据表的实体类。
 * @PrimaryKey ： 每一个实体类都需要一个唯一的标识。
 * @ColumnInfo ： 数据表中字段名称。
 * @Ignore ： 标注不需要添加到数据表中的属性。
 * @Embedded ： 实体类中引用其他实体类。
 * @ForeignKey ： 外键约束
 *
 * Created by zhaohl on 2020/3/5.
 */
@Entity(tableName = "person")
public class Person {

    public Person(String name){
        this.name = name;
    }
    @NonNull
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    private String gender;
    private String skin;
    private String nation;
    @Ignore
    private Language language = Language.CHINESE;
    @Embedded
    private Address address;
    // 增加一个字段测试room版本升级
    private int age;

//    private String[] hobbies;

//    private List<Integer> telphones;

    private int carId;




    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

//    public String[] getHobbies() {
//        return hobbies;
//    }
//
//    public void setHobbies(String[] hobbies) {
//        this.hobbies = hobbies;
//    }

//    public List<Integer> getTelphones() {
//        return telphones;
//    }
//
//    public void setTelphones(List<Integer> telphones) {
//        this.telphones = telphones;
//    }


    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }
}
