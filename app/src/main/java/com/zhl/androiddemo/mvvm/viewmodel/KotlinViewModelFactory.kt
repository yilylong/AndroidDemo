package com.zhl.androiddemo.mvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * 描述：
 * Created by zhaohl on 2020-6-29.
 */
class KotlinViewModelFactory(private val count:Int):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return KotlinViewModel(count) as T
    }
}