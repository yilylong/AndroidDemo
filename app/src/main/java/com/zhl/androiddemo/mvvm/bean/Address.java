package com.zhl.androiddemo.mvvm.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 描述：
 * Created by zhaohl on 2020/3/5.
 */
@Entity(tableName = "address")
public class Address {
    @PrimaryKey(autoGenerate = true)
    private int aid;
    private int pid;
    private String country;
    private String province;
    private String city;
    private String street;
    private String park;

    public Address(){

    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPark() {
        return park;
    }

    public void setPark(String park) {
        this.park = park;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
}
