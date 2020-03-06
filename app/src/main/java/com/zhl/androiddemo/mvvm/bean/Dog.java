package com.zhl.androiddemo.mvvm.bean;

import androidx.databinding.ObservableField;

/**
 * 描述：其实这个ObservableField就是对BaseObservable的简化，不用继承，不用主动调刷新代码。
 * Created by zhaohl on 2020/3/5.
 */
public class Dog {

    public ObservableField<String> type =  new ObservableField<>();

    public void setType(String type) {
        this.type.set(type);
    }
}
