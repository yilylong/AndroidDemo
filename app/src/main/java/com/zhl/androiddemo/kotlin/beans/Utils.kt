package com.zhl.androiddemo.kotlin.beans

import android.util.Log

/**
 * 描述：
 * Created by zhaohl on 2020-3-19.
 */
class Utils {
    var strArray:Array<String> = arrayOf("数","组","测","试")
    val map:Map<String,Int> = mapOf("key1" to 1,"key2" to 2,"key3" to 3)

    val list:List<String> = mutableListOf("ss","dd","ds")// mutablelist mutableset mutableMap可变集合


    // 静态变量和方法的实现 Kotlin 中只有基本类型和 String 类型可以声明成常量。
    companion object{
        const val TAG = "静态变量调用";
        fun staticFunTest(){
            Log.d("mytag","静态方法调用")
        }
    }
    fun testArray(){
        Log.d("mytag","数组调用${strArray[2]}"+strArray[3])
    }
    fun testMap(key:String){
        // map 取值使用get 或者 []
        Log.d("mytag","map调用"+map[key])
    }
}