package com.zhl.androiddemo.kotlin.extend

/**
 * 描述：高阶函数
 * Created by zhaohl on 2020-6-23.
 */

inline fun num1Andnum2(num1:Int,num2:Int,func:(Int,Int)->Int):Int{
    return func(num1,num2)
}

/**
 * 扩展函数与高阶函数结合
 */
inline fun StringBuilder.build(block:StringBuilder.()->Unit):StringBuilder{
    block()
    return this
}