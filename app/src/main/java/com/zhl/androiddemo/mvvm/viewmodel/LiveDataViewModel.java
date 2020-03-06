package com.zhl.androiddemo.mvvm.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * 描述：
 * Created by zhaohl on 2020/3/5.
 */
public class LiveDataViewModel extends ViewModel {
    private MutableLiveData<String> liveData = new MutableLiveData<>();

    public MutableLiveData<String> getLiveData() {
        return liveData;
    }

}
