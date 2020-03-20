package com.zhl.androiddemo.kotlin.beans

import android.util.Log

/**
 * 描述：
 * Created by zhaohl on 2020-3-18.
 */
class Animal {
    var type: String = "哺乳动物"
        get() {
            return field + "ddd";
        }
        set(value) {
            field = value + "set 拼接"
        };
    var intArray: IntArray = intArrayOf(1, 23, 4, 45, 5)

    fun testForEach() {
        // lambda表达式加闭包
        intArray.forEach { i -> Log.d("mytag", "" + i) }

        // 过滤生成新集合
        val newList: List<Int> = intArray.filter { i -> i != 23 }
        // map 转换
        val newList2: List<Int> = intArray.map { i -> i + 1 }
        // flatMap：遍历每个元素，并为每个元素创建新的集合，最后合并到一个集合中
        var newList3 = intArray.flatMap { i -> listOf("${i + 1}", "a") }
        newList3.forEach { i -> Log.d("mytag", i) }

        var range:IntRange = 0..100
        var charRange:CharRange = 'A'..'Z'
        // 半开区间 不包括10
        var range2:IntRange = 0 until 10

        for(i in range){
            Log.d("mytag", "range闭区间"+i);
        }
        // step 设置步长
        for(i in range step 10){
            Log.d("mytag", "stpe设置步长10="+i);
        }
        // 递减
        for(i in 10 downTo 1){
            Log.d("mytag", "递减downTo"+i);
        }
        //
        for(i in 0..10){
            Log.d("mytag", "直接使用区间0..10做循序和"+i);
        }

    }
}

class Dog constructor(type: String) {
    var type: String = type

    var str1 ="equals"
    var str2 ="equals"
    var str3 =str1
    var str4 ="equ"

    fun equalsTest(){
        Log.d("mytag","==相当于java的equals--"+(str1==str2))
        Log.d("mytag","===相当于java的==--"+(str4===str3))
    }
}

class Cat {
    var type: String;

    constructor(type: String) {
        this.type = type
    }
}

/**
 * 多个次构造器的情况
 */
class Sheep constructor(type: String) {
    var type: String = type
    lateinit var oriea: String
    lateinit var color: String

    constructor(type: String, oriea: String) : this(type) {
        this.oriea = oriea
    }

    constructor(type: String, oriea: String, color: String) : this(type, oriea) {
        this.oriea = oriea
        this.color = color
    }
}

/**
 * 柱构造函数可以省略constructor关键字
 */
class Fish(type: String) {

}

/**
 * 主构造器上使用可见性修饰符的时候不能省略 constructor关键字
 */
class Duck private constructor(type: String) {
    var rawString: String = """
        这是原生字段
        \n $ #7
        \r
        除了'$'取值还有效其他转义符不会转义 
        原样输出
        
    """.trimIndent()

}

/**
 * 主构造器上直接可以声明属性
 */
class Chicken(var type: String, var productPlace: String) {
    fun sayHi(name: String = "清远chicken", weight: Int) {
        Log.d("mytag", "我是" + name + " 体重${weight}kg")
    }
}
