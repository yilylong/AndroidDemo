package com.zhl.androiddemo.kotlin

import com.zhl.androiddemo.kotlin.beans.*
import com.zhl.androiddemo.kotlin.beans.Utils
import com.zhl.androiddemo.kotlin.extend.beginsWith
import com.zhl.androiddemo.kotlin.extend.build
import com.zhl.androiddemo.kotlin.extend.num1Andnum2
import com.zhl.androiddemo.kotlin.extend.speak
import com.zhl.androiddemo.kotlin.network.ApiService
import com.zhl.androiddemo.kotlin.network.GithubService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * 描述：
 * Created by zhaohl on 2020-6-22.
 */
fun main() {
    println("hello kotlin=======")
//    forFun1()
//    forFun2()
//    forFun3()
    beanFunTest1()
    dataClassFun()
    listFun()
    nullableFun("空指针检查机制")
    standardFun()
    extendFun()
    retrofitFun("square","retrofit")
}

fun forFun1(){
    for(i in 1..10){
        println("for循环--in range:$i")
    }
}
fun forFun2(){
    for(i in 0 until 10 step 2){
        println("for循环--until 左闭右开 步长2:$i")
    }
}

fun forFun3(){
    for(i in 10 downTo 0){
        println("for循环--downTo降序:$i")
    }
}

fun beanFunTest1(){
    val p = Person("张三");
    p.eat()
    val s = Student(3,1002,"张三")
    s.study()
    val s2 = Student(3,1003,5,"")
    s2.name = "李四"
    s2.study()
    // 面向接口多态编程
    doStudy(s);
    doStudy(s2)
}

fun doStudy(study: Study?){
    study?.studyEnglish()
    study?.studyMaths()
}

fun dataClassFun(){
    val cellphone = CellPhone("小米","xiaomi 10",5999)
    val cellphone2 = CellPhone("小米","xiaomi 10",5999)
    println(cellphone.toString()+"--hashcode=="+cellphone.hashCode())
    Singleton.log("cellphone==cellphone2="+cellphone.equals(cellphone2))
}

fun listFun(){
    val list1 = listOf("ad","adf","asdf","adw4");
    val list2 = listOf(1,2,4,45,56,6,66);
    println("listof 创建不可变集合 list1 size="+list1.size+"--第1项是="+list1.get(1))
    var list3 = mutableListOf<String>("adfadf","asdfasd","asdf","dd","q4d")
    list3.add("addlist-element")
    list3.add("addlist-element2")

// lambda 表达式用法
    println("list2中最大值元素是："+list2.maxBy { it })
    println("list1中最大值元素是："+list1.maxBy { it.length })

    Singleton.divider("map 转换")
    val list4 = list3.map { it.toUpperCase()+"_list4" }
    for(i in list4){
        println(i)
    }
    Singleton.divider("filter 过滤")
    val list5 = list4.filter { it.length<14 }
    for(i in list5){
        println(i)
    }

    for(i in list3){
        println(i)
    }
    Singleton.divider("map")
    var map = mutableMapOf<String,String>("LISI" to "zhagnsan","wangwu" to "mazi")
    map.put("jiayi","bingding")
    map["上"] = "下"
    map["左"] = "右"

    for(i in map){
        println(i.key+"=="+i.value)
    }
    Singleton.divider("第二种map迭代方式")
    for((k,v)in map){
        println(k+"=="+v)
    }

}

fun nullableFun(tx:String){
    Singleton.divider("可空操作符判断")
    println("字符长度是："+getTextLenth(tx))
}

fun getTextLenth(tx:String?)= tx?.length?:0

/**
 * 标准函数 with run apply
 */
fun standardFun(){
    Singleton.divider("with 函数")
    val with = with(Student(3,103,"jack")){
        studyMaths()
        studyEnglish()
    }
    Singleton.divider("run 函数 必须要用对象来调用")
    val run = Student(3,103,"jack").run{
        studyMaths()
        studyEnglish()
    }
    Singleton.divider("apply 函数 无法指定返回对象 返回的就是调用对象本身")
    val apply = Student(3,103,"jack").apply{
        studyMaths()
        studyEnglish()
    }
    println(apply.name)
    Utils.fun2()
    Utils.fun3()

}

fun extendFun(){
    Singleton.divider("扩展函数")
    Person("王五").speak("我是王五你叫什么名字");
    Singleton.divider("运算符重载使用operator")
    val money = Money(2030f)
    val money2 = Money(24323f)
    val money3 = money+245+money2+24423.23f+2233
    println(money3.cast)
    Singleton.divider("高阶函数")
    println("计算结果${num1Andnum2(234,53,::plus)}")
    println("计算结果${num1Andnum2(234,53,::minus)}")
    println("计算结果使用Lambda方式${num1Andnum2(234,53,{i,v->i-v})}")
    Singleton.divider("扩展函数与高阶函数结合")
    var list = mutableListOf<String>("jack","lili","bobo","tom","esyblle")
    val sb = StringBuilder().build {
        append("start append \n")
        for(i in list){
            append(i).append("\n")
        }
        append("append finish \n")
    }
    println(sb.toString());
    Singleton.divider("infix 函数测试 可以使用特殊的语法糖格式调用函数")
    if("hello kotlin" beginsWith "hello"){
        println("使用特殊的语法糖格式调用 'hello kotlin' beginsWith 'hello'")
    }
}

fun plus(n1:Int,n2:Int):Int{
    return n1+n2
}

fun minus(n1:Int,n2:Int):Int{
    return n1*n2
}

fun retrofitFun(group:String,name:String){
    Singleton.divider("retrofit 联网测试")
    ApiService.create<GithubService>().getGithubDetail(group,name).enqueue(object :Callback<ResponseBody>{

        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            println(response.body()?.byteStream().toString());
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            println("请求失败：url=${call.request().url()} 错误信息：${t.message}");
        }

    })
}