package com.zhl.androiddemo.kotlin.beans

import com.zhl.androiddemo.mvvm.bean.Language

/**
 * 描述：
 * Created by zhaohl on 2020-6-22.
 */
open class Person(var name:String) {
    var age = 20;
    fun eat(){
        println("$name 会吃饭")
    }
}