package com.example.boxgame.caller

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.core.app.NotificationCompat
import com.example.boxgame.R

class CallerInfoService : Service() {
    private lateinit var windowManager: WindowManager
    private lateinit var overlayView: View
    private val CHANNEL_ID = "CallerInfoServiceChannel"

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        createNotificationChannel()
        Log.d("CallerInfoService", "Service created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val number = intent?.getStringExtra("NUMBER")
        Log.d("CallerInfoService", "Service started with number: $number")

        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Caller Info Service")
            .setContentText("Fetching caller information...")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()

        startForeground(1, notification)

        if (number != null) {
            showOverlay(number)
        }

        return START_NOT_STICKY
    }

    private fun showOverlay(number: String) {
        overlayView = LayoutInflater.from(this).inflate(R.layout.overlay_view, null)

        val layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY else WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        layoutParams.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
        layoutParams.x = 0
        layoutParams.y = 100

        val callerInfoTextView = overlayView.findViewById<TextView>(R.id.callerInfoTextView)
        callerInfoTextView.text = "Incoming call from: $number"

        overlayView.setOnClickListener {
            stopSelf()
        }

        windowManager.addView(overlayView, layoutParams)
        Log.d("CallerInfoService", "Overlay displayed with number: $number")
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::overlayView.isInitialized) {
            windowManager.removeView(overlayView)
        }
        Log.d("CallerInfoService", "Service destroyed")
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Caller Info Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
    }
}