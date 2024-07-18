package com.example.boxgame.caller

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.util.Log
import androidx.core.content.ContextCompat

class CallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("CallReceiver", "onReceive: intent action = ${intent.action}")

        if (intent.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            if (state == TelephonyManager.EXTRA_STATE_RINGING) {
                val incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
                Log.d("CallReceiver", "Incoming call from: $incomingNumber")
                showCallerInfo(context, "incomingNumber")

                if (incomingNumber != null) {
                    showCallerInfo(context, incomingNumber)
                }
            }
        }
    }

    private fun showCallerInfo(context: Context, number: String) {
        val intent = Intent(context, CallerInfoService::class.java)
        intent.putExtra("NUMBER", number)
        ContextCompat.startForegroundService(context, intent)
        Log.d("CallReceiver", "Started CallerInfoService with number: $number")
    }
}