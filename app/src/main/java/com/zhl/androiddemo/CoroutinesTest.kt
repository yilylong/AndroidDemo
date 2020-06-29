package com.zhl.androiddemo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

/**
 * 描述：协程测试
 * Created by zhaohl on 2020-6-28.
 */
fun main() {
//    GlobalScope.launch {
//        println("GlobalScope协程里面运行代码")
//    }
//    Thread.sleep(1000)

//    runBlocking {
//        println("协程runBlocking里面运行代码")
//    }
//    println("runBlocking里面的代码执行完毕，开始执行主线程代码")


    runBlocking {
      val result = async {
            5+5
        }.await()
        println(result)
    }

    runBlocking {

        val result2 = withContext(Dispatchers.IO){
            24+234
        }
        println(result2)
    }

}