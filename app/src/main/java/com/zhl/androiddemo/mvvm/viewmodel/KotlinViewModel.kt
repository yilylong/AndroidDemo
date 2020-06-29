package com.zhl.androiddemo.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * 描述：
 * Created by zhaohl on 2020-6-29.
 */
class KotlinViewModel(value:Int):ViewModel() {
    val counter :LiveData<Int>
    get() = _counter

    private var _counter = MutableLiveData<Int>()

    init {
        _counter.value = value
    }

    fun plusOne(){
        val count = _counter.value?:0
        _counter.value = count+1
    }

    fun clear(){
        _counter.value = 0;
    }
}