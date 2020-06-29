package com.zhl.androiddemo.kotlin.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.zhl.androiddemo.kotlin.Singleton

/**
 * 描述：
 * Created by zhaohl on 2020-6-29.
 */
class SimpleWorker(ctx:Context,workerParameters: WorkerParameters):Worker(ctx,workerParameters) {
    override fun doWork(): Result {
        Singleton.divider("开始后台work")
        for(i in 0..10){
            Log.d("mytag","后台工作中$i")
        }
        Singleton.divider("后台work结束")
        return Result.success()
    }
}