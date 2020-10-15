package com.zhl.androiddemo.kotlin

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.zhl.androiddemo.MainActivity
import com.zhl.androiddemo.R

/**
 * 描述：
 * Created by zhaohl on 2020/9/30.
 */
class MyService : Service() {
    override fun onCreate() {
        super.onCreate()
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(NotificationChannel("1","后台服务",NotificationManager.IMPORTANCE_DEFAULT))
        val pi = PendingIntent.getActivity(this,1,Intent(this,MainActivity::class.java),0)
        val notifacion = NotificationCompat.Builder(this, "1")
                .setContentTitle("KotlinDemo forgegound service")
                .setContentText("前台service测试")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pi)
                .build();
        manager.notify(1,notifacion)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}