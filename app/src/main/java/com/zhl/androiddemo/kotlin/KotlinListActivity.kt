package com.zhl.androiddemo.kotlin

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zhl.androiddemo.R
import com.zhl.androiddemo.ViewHolder
import com.zhl.androiddemo.ViewHolderLeft
import com.zhl.androiddemo.ViewHolderRight
import kotlinx.android.synthetic.main.activity_kotlin_list.*

/**
 * 描述：
 * Created by zhaohl on 2021/1/12.
 */
class KotlinListActivity : AppCompatActivity() {
    val mContext by lazy { this }

    //    val data: MutableList<String> = mutableListOf();
    val data = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_list)
        initData();
        initRecyclerview();
    }

    fun initData() {
        for (i in 0..100) {
            data.add("Kotlin 列表测试：$i")
        }
    }

    private fun initRecyclerview() {
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = MyAdapter()

        //高阶函数+扩展函数的结合运用
        getSharedPreferences("highFuncTest", Context.MODE_PRIVATE).open {
            putString("test1","adasdf")
            putString("test2","adfag242")
        }
        getSharedPreferences("fromktx",Context.MODE_PRIVATE).edit {
            putString("test1","adasdf")
            putString("test2","adfag242")
        }
    }

    inner class MyAdapter : RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_kotlin_list, parent, false)
            if (viewType == 0) {
                val holder1 = ViewHolderLeft(itemView)
                holder1.itemView.setOnClickListener{
                    Toast.makeText(parent.context,"列表item点击 ViewHolderLeft",Toast.LENGTH_SHORT).show()
                }
                return holder1
            } else {
                val holder2 = ViewHolderRight(itemView)
                holder2.itemView.setOnClickListener{
                    Toast.makeText(parent.context,"列表item点击 ViewHolderRight",Toast.LENGTH_SHORT).show()
                }
                return holder2
            }
        }

        override fun getItemViewType(position: Int): Int {
            if (position % 2 == 0) {
                return 1;
            } else {
                return 0;
            }
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            when (holder) {
                is ViewHolderLeft -> {
                    holder.tv.setText(data[position])
                    holder.img.setImageResource(R.mipmap.image_view)
                }
                is ViewHolderRight->{
                    holder.tv.setText(data[position])
                    holder.img.setImageResource(R.mipmap.lake)
                }
            }

        }

        override fun getItemCount(): Int {
            return data.size
        }

//        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//            var tv:TextView = itemView.findViewById(R.id.tv);
//            var img:ImageView = itemView.findViewById(R.id.img);
//        }

    }


}