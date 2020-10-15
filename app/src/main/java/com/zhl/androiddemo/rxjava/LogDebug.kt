package com.zhl.androiddemo.rxjava

import android.util.Log

/**
 * 描述：
 * Created by zhaohl on 2020/10/13.
 */
object LogDebug {
    @JvmStatic
    fun d(s:String){
        Log.d("mytag","----$s----")
    }
}