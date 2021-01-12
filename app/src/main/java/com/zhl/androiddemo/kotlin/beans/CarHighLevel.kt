package com.zhl.androiddemo.kotlin.beans

import com.zhl.androiddemo.kotlin.injectGus

/**
 * 描述：扩展函数
 * Created by zhaohl on 2021/1/12.
 */
fun Car.injectGas(gas:String,rating:Int){
    injectGus(gas,rating){
        gas,rating->
        println(model+"加入$gas 标号 $rating 的汽油");
        rating>93;
    }
}