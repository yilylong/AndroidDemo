package com.zhl.androiddemo.mvvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.zhl.androiddemo.R
import com.zhl.androiddemo.kotlin.workmanager.SimpleWorker
import com.zhl.androiddemo.mvvm.viewmodel.KotlinViewModel
import com.zhl.androiddemo.mvvm.viewmodel.KotlinViewModelFactory
import kotlinx.android.synthetic.main.activity_mvvm_kotlin.*
import java.util.concurrent.TimeUnit

/**
 * 描述：
 * Created by zhaohl on 2020-6-29.
 */
class MvvmKotlinActivity : AppCompatActivity() {
    lateinit var viewModel:KotlinViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvvm_kotlin)
        viewModel = ViewModelProviders.of(this,KotlinViewModelFactory(3)).get(KotlinViewModel::class.java)
        btn_counter.setOnClickListener{
            viewModel.plusOne()
//            refreshResult()
        }
        btn_clear.setOnClickListener{
            viewModel.clear()
        }
        viewModel.counter.observe(this, Observer { count-> counter_result.text = count.toString() })
        btn_work_manager.setOnClickListener {
            val request = OneTimeWorkRequest.Builder(SimpleWorker::class.java)
                    .setInitialDelay(1,TimeUnit.MINUTES)
                    .addTag("simplework")
                    .build()
            WorkManager.getInstance(this).enqueue(request)
        }
//        refreshResult()
    }

    private fun refreshResult() {
        counter_result.text = viewModel.counter.toString()
    }
}