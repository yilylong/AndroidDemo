package com.zhl.androiddemo.kotlin.beans

/**
 * 描述：
 * Created by zhaohl on 2020-6-23.
 */
sealed class Result
class Success(val msg:String):Result()
class Fail(val msg:String):Result()