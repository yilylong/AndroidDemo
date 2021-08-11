package com.zhl.androiddemo.kotlin

import android.content.Intent
import com.zhl.androiddemo.kotlin.beans.Auto
import com.zhl.androiddemo.kotlin.beans.Car
import com.zhl.androiddemo.kotlin.beans.injectGas
import com.zhl.androiddemo.kotlin.extend.genericType
import kotlin.coroutines.suspendCoroutine

/**
 * 描述：
 * Created by zhaohl on 2021/1/11.
 */
fun main() {
    Singleton.divider("Kotlin study start")

    collectionsTest();
    Singleton.br()
    standardMethod();
    Singleton.br()
    highLevelMethod()


    Singleton.br()
    Singleton.divider("Kotlin study end")
}

/**
 * 集合
 */
fun collectionsTest() {
    Singleton.divider("集合测试开始")
    // mutableListOf得到一个可变集合
    val list = mutableListOf<String>("43adwe", "4g4ar34", "jsdhw534s", "362534der", "q64re", "duiw642ss", "q34a", "2352sd2f")
    //利用函数式API变换集合
    var list2 = list.filter { it.length > 6 }.map { it.toUpperCase() }
    Singleton.line("list 变换前")
    for (letter in list) {
        println(letter)
    }
    Singleton.line("list 变换后")
    for (letter in list2) {
        println(letter)
    }
    // 这里的to 是infix 内联函数
    val map = mapOf<String, Int>("lisi" to 21, "zhangsan" to 20, "wangwu" to 23)
    // 注意这里map的转换
    val map2 = map.map {
        it.key.toUpperCase() to it.value;
    }
    Singleton.line("map 变换前")
    for ((k, v) in map) {
        println("名字$k：年龄：$v")
    }
    Singleton.line("map 变换后")
    for ((k, v) in map2) {
        println("名字$k：年龄：$v")
    }
    Singleton.divider("集合测试结束")
}

/**
 * 标准函数
 */
fun standardMethod(){
    Singleton.divider("标准函数测试开始")
    Singleton.line("let 函数运用")
    drive(Car(3,"丰田","普拉多"))

    Singleton.line("with 函数运用 将在第二个lambda表达式里面传递第一个参数的上下文对象")
    with(Car(2,"本田","思域")){
        startUp();
        drive()
        light()
        wipeWindow()
        breakUp()
        shutdown()
    }

    Singleton.line("run 函数运用 只能由对象调用 且只接收一个lambda表达式 拥有调用对象的上下文关系")
    var benchi = Car(1,"奔驰","E200")
    benchi.run {
        startUp();
        drive()
        light()
        wipeWindow()
        breakUp()
        shutdown()
    }

    Singleton.line("apply 函数运用 跟run函数基本一致 但是返回的是对象本身 不是代码最后一行的值")

    Singleton.divider("标准函数测试结束")
}

/**
 * 高阶函数测试
 */
fun highLevelMethod(){
    Singleton.divider("扩展函数&高阶函数测试开始")
    Car(3,"宝马","X5").injectGas("中国石油",95)
    Singleton.divider("泛型实化");
    Singleton.line("---"+ genericType<Car>()+"---")
}


fun drive(auto: Auto?){
    auto?.let {
        if(it.startUp()>0){
            println("启动成功可以起步")
        }else{
            println("启动失败请检查车辆错误信息")
        }
    }
}