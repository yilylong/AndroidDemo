package com.zhl.androiddemo.kotlin

import android.util.Log
import com.zhl.androiddemo.kotlin.beans.Animal
import com.zhl.androiddemo.kotlin.beans.Sheep
import kotlinx.coroutines.*

/**
 * 描述：
 * Created by zhaohl on 2020-3-23.
 */
class CoroutinesTest {

    fun coroutinesTest(){
        // 协程第一种方式 通常适用于单元测试的场景，而业务开发中不会用到这种方法，因为它是线程阻塞的。
        runBlocking {
            Log.d("mytag","协程第一种方式-runBlocking")
        }
        /**
         * 方法二和使用 runBlocking 的区别在于不会阻塞线程。但在 Android 开发中同样不推荐这种用法，因为它的生命周期会和 app 一致，且不能取消
          */
        GlobalScope.launch {
            Log.d("mytag","协程第二种方式-GlobalScope.launch")
        }

        /**
         *  方法三，自行通过 CoroutineContext 创建一个 CoroutineScope 对象
        方法三是比较推荐的使用方法，我们可以通过 context 参数去管理和控制协程的生命周期（这里的 context 和 Android 里的不是一个东西，是一个更通用的概念，会有一个 Android 平台的封装来配合使用）。
         */

//                                    👇 需要一个类型为 CoroutineContext 的参数
        val coroutineScope = CoroutineScope(Dispatchers.Main)
        // Dispatchers.IO 切换到IO线程
        coroutineScope.launch(Dispatchers.IO) {
            Log.d("mytag","协程第三种方式-CoroutineScope.launch")
            val imageUrl = withContext(Dispatchers.IO){
                getImage()
            }
            val animal:Animal = getAnimal()
            Log.d("mytag","--获取的animal="+animal.type)
        }
    }

    fun getImage():String{
        Log.d("mytag","获取图片...")
        return Sheep("绵羊").type
    }

    suspend fun getAnimal() = withContext(Dispatchers.IO){
        Log.d("mytag","获取图片动物")
        Animal()
    }

    suspend fun downloadData(){
        withContext(Dispatchers.IO){
            Animal()
        }
    }

}