package com.zhl.androiddemo.kotlin.beans

import com.zhl.androiddemo.kotlin.Singleton
import com.zhl.androiddemo.kotlin.injectGus

/**
 * 描述：小轿车 加上data 就是数据类型自动实现eqauls hashcode 等等方法 数据类必须有自主构造参数
 * Created by zhaohl on 2021/1/11.
 */
class Car(var level:Int,brand:String,model:String):Auto(brand,model) {
    override fun checkSelf(): Boolean {
        val checkList = listOf("1电瓶检查完毕","2汽油检查完毕","3水温检查完毕","4机油检查完毕","5发动机检查完毕")
        for(check in checkList){
            println(check)
        }
        return true
    }

    override fun startUp():Int{
        // kotlin中不再有三目运算符了
//        return checkSelf()?1:0
        println("--发动车辆开始自检--")
        if(checkSelf()){
            println(brand+model+"启动成功")
            return 1;
        }else{
            println("--自检异常--")
            println(brand+model+"启动失败")
            return 0;
        }
    }

    override fun wipeWindow() {
       println("$model 开启雨刷")
    }

    override fun drive() {
        println("$model 运行中")
    }

    override fun breakUp() {
        println("$model 刹车")
    }

    override fun reverse() {
        println("$model 倒车")
    }

    override fun light() {
        println("$model 打开车灯")
    }

    override fun shutdown() {
        println("$model 熄火停车")
    }



}