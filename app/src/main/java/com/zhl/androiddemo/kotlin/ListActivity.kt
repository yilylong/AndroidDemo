package com.zhl.androiddemo.kotlin

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zhl.androiddemo.R
import kotlinx.android.synthetic.main.layout_list.*

/**
 * 描述：
 * Created by zhaohl on 2020/9/29.
 */
class ListActivity : AppCompatActivity() {
    private val data: ArrayList<String> = ArrayList()
    private lateinit var mAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_list)
        initData()
        initView()
    }

    private fun initData() {
        Log.d("mytag", intent.getSerializableExtra("data") as String)
        for (i in 0..10) {
            data.add("i===" + i)
        }
    }

    private fun initView() {
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = MyAdapter(data)
    }

    inner class MyAdapter(val list: ArrayList<String>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.ViewHolder {
            var view = LayoutInflater.from(this@ListActivity).inflate(R.layout.item_list, null);
            return ViewHolder(view)
        }

        override fun getItemCount() = list.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.tv.setText(list.get(position))
            holder.convertView.setOnClickListener {
                holder.convertView.context.startService(Intent(holder.convertView.context,MyService::class.java))
            }
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var convertView = itemView
            var tv: TextView = itemView.findViewById(R.id.item_tv)
        }

    }
}