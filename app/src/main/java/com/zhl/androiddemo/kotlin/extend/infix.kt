package com.zhl.androiddemo.kotlin.extend

/**
 * 描述：infix 函数
 * Created by zhaohl on 2020-6-24.
 */
infix fun String.beginsWith(prefix:String)=startsWith(prefix)