package com.zhl.androiddemo.kotlin.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.zhl.androiddemo.kotlin.Singleton

/**
 * 描述：
 * Created by zhaohl on 2021/1/14.
 */
class CustomViewText : View {
    constructor(context: Context) : this(context,null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet,-1)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)
    val paint = TextPaint(Paint.ANTI_ALIAS_FLAG)

    init {
        Singleton.divider("自定义view 构造函数")
        paint.setColor(Color.parseColor("#48aa23"))
        paint.textSize = 100f
//        paint.isStrikeThruText = true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val text = "ACD\n武僧的AAdafjklk34908902\n请我怕是阿萨德年加工费这是一段长文字阿道夫骄傲劳动纪律24odfigu8046大环境"
        val staticLayout = StaticLayout.Builder.obtain(text, 0, text.length, paint, width).setLineSpacing(0f,1.5f).setIncludePad(true).build()
        staticLayout.draw(canvas)
    }

}