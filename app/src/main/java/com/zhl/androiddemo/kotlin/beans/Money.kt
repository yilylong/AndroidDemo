package com.zhl.androiddemo.kotlin.beans

/**
 * 描述：
 * Created by zhaohl on 2020-6-23.
 */
class Money(val cast:Float) {
    //运算符重载
    operator fun plus(money: Money):Money{
        return Money(cast+money.cast)
    }

    operator fun plus(newCast:Int):Money{
        return Money(cast+newCast)
    }
    operator fun plus(newCast:Float):Money{
        return Money(cast+newCast)
    }
}