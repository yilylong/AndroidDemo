package com.zhl.androiddemo.mvvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.zhl.androiddemo.R
import com.zhl.androiddemo.mvvm.viewmodel.KotlinViewModel
import com.zhl.androiddemo.mvvm.viewmodel.KotlinViewModelFactory
import kotlinx.android.synthetic.main.activity_mvvm_kotlin.*

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

//        refreshResult()
    }

    private fun refreshResult() {
        counter_result.text = viewModel.counter.toString()
    }
}