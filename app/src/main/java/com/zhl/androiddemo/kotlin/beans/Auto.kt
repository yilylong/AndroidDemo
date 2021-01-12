package com.zhl.androiddemo.kotlin.beans

/**
 * 描述：汽车
 * Created by zhaohl on 2021/1/11.
 */
open abstract class Auto(val brand:String,val model:String):Controller{
    constructor():this("","")
    var color:String = ""
    var length:Float = 5.0f
    var width:Float = 2.0f
    var height:Float = 1.5f
    lateinit var Fueltype:String

    abstract fun checkSelf():Boolean
}