package com.zhl.androiddemo.kotlin.beans

/**
 * 描述：
 * Created by zhaohl on 2020-6-22.
 */
class Utils {
    fun fun1(){

    }

    companion object{
        /**
         * 静态方法
         */
        fun fun2(){
            println("companion object 构建静态方法")
        }

        /**
         * 静态方法
         */
        @JvmStatic
        fun fun3(){
            println("@JvmStatic 注解编译成真正的静态方法")
        }
    }
}