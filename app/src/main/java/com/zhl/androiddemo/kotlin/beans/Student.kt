package com.zhl.androiddemo.kotlin.beans

import com.zhl.androiddemo.kotlin.Study

/**
 * 描述：
 * Created by zhaohl on 2020-6-22.
 */
class Student(var grade:Int,var sno:Int,name:String):Person(name),Study {
    lateinit var gender:String;
    init{
        println("柱构造函数会调用的函数体init")
    }
    var classNo:Int = 0;
    constructor(grade:Int,sno:Int,classNo:Int,name: String):this(grade,sno,name){
        this.classNo = classNo
    }
    fun study(){
        println("$name 学号：$sno 今年$grade 年级")
    }

    override fun studyMaths() {
        println("$name 学习数学")
    }

    override fun studyEnglish() {
        println("$name 学习英语")
    }

    fun myGender(gender:String){
       this.gender = gender
    }
}