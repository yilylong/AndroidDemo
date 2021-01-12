package com.zhl.androiddemo

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * 描述：
 * Created by zhaohl on 2021/1/12.
 */
sealed class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
class ViewHolderLeft(itemView: View) : ViewHolder(itemView) {
    var tv: TextView = itemView.findViewById(R.id.tv);
    var img: ImageView = itemView.findViewById(R.id.img);
}

class ViewHolderRight(itemView: View) : ViewHolder(itemView) {
    var tv: TextView = itemView.findViewById(R.id.tv);
    var img: ImageView = itemView.findViewById(R.id.img);
}