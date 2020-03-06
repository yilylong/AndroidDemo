package com.zhl.androiddemo.mvvm.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

/**
 * 描述：
 * Created by zhaohl on 2020/3/4.
 */
public class User extends BaseObservable {
    @Bindable
    public String name;
    private float high;
    private float weight;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public User(){

    }

    public User(String name){
        this.name = name;
    }


    @Bindable
    public float getHigh() {
        return high;
    }

    public void setHigh(float high) {
        this.high = high;
    }
    @Bindable
    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
