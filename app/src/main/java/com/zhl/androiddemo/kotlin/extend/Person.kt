package com.zhl.androiddemo.kotlin.extend

import com.zhl.androiddemo.kotlin.beans.Person

/**
 * 描述：
 * Created by zhaohl on 2020-6-23.
 */
fun Person.speak(language: String){
    println("${this.name}说$language")
}
