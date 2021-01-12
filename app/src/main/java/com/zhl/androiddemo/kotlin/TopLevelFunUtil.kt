package com.zhl.androiddemo.kotlin

import android.util.Log

/**
 * 描述：
 * Created by zhaohl on 2020-3-19.
 */
fun topLevelFun1(){
    Log.d("mytag","toplevelFun1")
}

/**
 * 高阶函数
 */
fun injectGus(gasModel:String,rating:Int,func:(String,Int)->Boolean){
    func(gasModel,rating);
}