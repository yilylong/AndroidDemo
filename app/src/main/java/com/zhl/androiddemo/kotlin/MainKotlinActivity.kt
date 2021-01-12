package com.zhl.androiddemo.kotlin

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.FutureTarget
import com.zhl.androiddemo.ActivityBWithFragment
import com.zhl.androiddemo.BlankFragment
import com.zhl.androiddemo.R
import com.zhl.androiddemo.databinding.ActivityMainKotlinBinding
import com.zhl.androiddemo.kotlin.beans.Animal
import com.zhl.androiddemo.kotlin.beans.Dog
import com.zhl.androiddemo.mvvm.bean.Person
import com.zhl.androiddemo.mvvm.utils.ClickUtils
import kotlinx.android.synthetic.main.activity_main_kotlin.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class MainKotlinActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityMainKotlinBinding
    lateinit var img: ImageView
    var v1 = "String" //类型推断 省去变量类型
    val v2 = 10 // val 声明只读变量 不可更改
    val v3: String? = "zhsangsan"
    val v4 = "李四"
    var person: Person? = null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_kotlin)
        binding = DataBindingUtil.setContentView<ActivityMainKotlinBinding>(this, R.layout.activity_main_kotlin)
        binding.tvResult = v1 + v2
        binding.setClickListener {
            this
        }
        btn_test.setOnClickListener {
            val intent = Intent(this,ActivityBWithFragment::class.java)
            startActivity(intent)
        }
        btn_recyclerview.setOnClickListener{
//            val intent = Intent(this,ListActivity::class.java)
//            startActivity(intent)
            com.zhl.androiddemo.kotlin.extend.startActivity<KotlinListActivity>(this){
                putExtra("data","11111")
                putExtra("params","参数")
            }
        }
        // 匿名内部类的写法 object:
//        binding.setClickListener { object :View.OnClickListener{
//            override fun onClick(v: View?) {
//                ClickUtils().changeTextStr(v)
//            }
//        } }
//        binding.setClickListener{v:View->Unit
//            ClickUtils().changeTextStr(v)
//        }
        // 满足SAM 最后简化成如下：
//        binding.setClickListener { v -> ClickUtils().changeTextStr(v) }
        img = findViewById(R.id.image)
//        generatePerson(v3); 可空变量不能传给 不可空参数
        person = generatePerson(v4);
        binding?.tvResult = "名字叫：" + person?.name + Utils.TAG;
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

        coroutineTest()

        CoroutinesTest().coroutinesTest()
    }


    /**
     * kotlin里参数默认是val 类型
     */
    fun generatePerson(name: String): Person {
        var p = Person(name);
        return p;
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_kotlin -> {
                ClickUtils().changeTextStr(v)
            }
            R.id.btn_test-> {
                val intent = Intent(this,ActivityBWithFragment::class.java)
                startActivity(intent)
            }
        }
    }

    fun coroutineTest(){
        var coroutineScope = CoroutineScope(Dispatchers.Main)
        coroutineScope.launch() {
            val url = "http://t8.baidu.com/it/u=3571592872,3353494284&fm=79&app=86&size=h300&n=0&g=4n&f=jpeg?sec=1585620259&t=e9dac2d52076735f269300a8e0234048"
            val futureTarget:FutureTarget<File>  = withContext(Dispatchers.IO){
                Glide.with(binding.image).load(url).downloadOnly(200,200)
            }
            val requestBuilder:RequestBuilder<Drawable> = withContext(Dispatchers.IO){
                Glide.with(binding.image).load(futureTarget.get())
            }
            requestBuilder.into(binding.image)
        }
    }

}
