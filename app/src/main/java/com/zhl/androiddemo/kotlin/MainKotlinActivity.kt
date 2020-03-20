package com.zhl.androiddemo.kotlin

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.zhl.androiddemo.R
import com.zhl.androiddemo.databinding.ActivityMainKotlinBinding
import com.zhl.androiddemo.kotlin.beans.Animal
import com.zhl.androiddemo.kotlin.beans.Dog
import com.zhl.androiddemo.kotlin.beans.Utils
import com.zhl.androiddemo.mvvm.bean.Person
import com.zhl.androiddemo.mvvm.utils.ClickUtils

class MainKotlinActivity : AppCompatActivity(),View.OnClickListener {
    lateinit var binding:ActivityMainKotlinBinding
    lateinit var img: ImageView
    var v1 = "String" //类型推断 省去变量类型
    val v2 = 10 // val 声明只读变量 不可更改
    val v3:String? = "zhsangsan"
    val v4 = "李四"
    var person:Person?=null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_kotlin)
        binding = DataBindingUtil.setContentView<ActivityMainKotlinBinding>(this,R.layout.activity_main_kotlin)
        binding.tvResult = v1+v2
        binding.setClickListener { this }
        // 匿名内部类的写法 object:
//        binding.setClickListener { object :View.OnClickListener{
//            override fun onClick(v: View?) {
//                ClickUtils().changeTextStr(v)
//            }
//        } }
        img = findViewById(R.id.image)
//        generatePerson(v3); 可空变量不能传给 不可空参数
        person = generatePerson(v4);
        binding?.tvResult = "名字叫："+ person?.name+Utils.TAG;
        // 静态方法调用
        Utils.staticFunTest()
        Utils().testArray()
        Utils().testMap("key2")
        // 顶层方法调用 （顶层方法属于package级别）
        topLevelFun1();
        // test 集合迭代
        Animal().testForEach()
        // == 和 === 测试
        Dog("金毛").equalsTest()
    }

    /**
     * kotlin里参数默认是val 类型
     */
    fun generatePerson(name:String):Person{
        var p = Person(name);
        return p;
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_kotlin->{
                ClickUtils().changeTextStr(v)}
        }
    }
}
