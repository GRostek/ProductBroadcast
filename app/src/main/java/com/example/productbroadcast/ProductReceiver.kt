package com.example.productbroadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi

class ProductReceiver : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {


        if(intent.action == "com.example.productbroadcast.addproduct") {
            val service = Intent(context, ProductService::class.java)
            service.putExtra("ProductName", intent.getStringExtra("ProductName"))
            service.putExtra("ProductID", intent.getLongExtra("ProductID", 0))
            context.startForegroundService(service)

        }

    }
}