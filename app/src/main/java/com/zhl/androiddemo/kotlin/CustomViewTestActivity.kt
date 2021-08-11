package com.zhl.androiddemo.kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zhl.androiddemo.R

/**
 * 描述：
 * Created by zhaohl on 2021/1/14.
 */
class CustomViewTestActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_customview)
    }
}