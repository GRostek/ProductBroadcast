package com.example.productbroadcast

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class ProductService : Service() {

    private var notificationId = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val modifyActivity = Intent()
        modifyActivity.component = ComponentName("com.example.pjatkshoppinglist", "com.example.pjatkshoppinglist.ModifyActivity")
        modifyActivity.putExtra("id", intent.getLongExtra("ProductID",0))


        //notification channel wykonujemy w service
        createChannel()

        val pendingIntent = PendingIntent.getActivity(
            this,
            notificationId++,
            modifyActivity,
            PendingIntent.FLAG_ONE_SHOT
        )

        var channelId = getString(R.string.channel_id)
                /*= intent.getStringExtra("channel_id")

        if(channelId == null){
            channelId = "channel_default"
        }*/

        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Product has been added")
            .setContentText(intent.getStringExtra("ProductName"))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(this).notify(notificationId,notification)

        startForeground(1,notification)

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder {
            TODO()
    }

    /*override fun onCreate() {
        super.onCreate()
        startForeground(1,)
    }*/

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(){
        val channel = NotificationChannel(
            getString(R.string.channel_id),
            getString(R.string.channel_name),
            NotificationManager.IMPORTANCE_DEFAULT
        )

        NotificationManagerCompat.from(this).createNotificationChannel(channel)
    }
}