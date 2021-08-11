package com.zhl.androiddemo.kotlin

import android.content.SharedPreferences

/**
 * 描述：
 * Created by zhaohl on 2021/1/13.
 */
fun SharedPreferences.open(block:SharedPreferences.Editor.()->Unit){
    val editor = edit()
    editor.block()
    editor.apply()
}