package com.zhl.androiddemo.kotlin

/**
 * 描述：泛型协变
 * Created by zhaohl on 2020-6-24.
 */
class SimpleData<out T>(val data:T?) {
    fun get():T?{
        return data
    }
}