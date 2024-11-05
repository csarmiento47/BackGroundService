package com.inforcap.backgroundservice.service

import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log
import android.widget.Toast
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class BackgroundService : Service() {
    
    private var serviceLooper: Looper? = null
    private var serviceHandler: HandleService? = null

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onCreate() {
        HandlerThread("BackgroundService", Process.THREAD_PRIORITY_BACKGROUND).apply {
            start()
            serviceLooper = looper
            serviceHandler = HandleService(looper)
        }
    }

    override fun onDestroy() {
        Toast.makeText(this, "Deteniendo servicio", Toast.LENGTH_LONG).show()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "Iniciando descarga", Toast.LENGTH_LONG).show()
        serviceHandler?.obtainMessage()?.also { message: Message ->
            message.arg1 = startId
            serviceHandler?.sendMessage(message)
        }
        return START_STICKY
    }

    private fun dummyHttpRequest(): String? {
        val url = URL("https://dummyjson.com/products/1")
        return (url.openConnection() as? HttpURLConnection)?.run {
            requestMethod = "GET"
            inputStream.bufferedReader().readText()
        }
    }


    inner class HandleService(looper: Looper) : Handler(looper) {
        override fun handleMessage(msg: Message) {
            try {
                thread {
                    println("dummy request: ${dummyHttpRequest()}")
                }
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            }
        }
    }





}