package dev.epegasus.servicesamples.services.background

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import dev.epegasus.servicesamples.TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyBackgroundService : Service() {


    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: called")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: ID = $startId")

        //startOnMainThread()
        //startOnBackgroundThread()
        stopAfterProcess()

        Log.d(TAG, "onStartCommand: done")

        return START_STICKY
    }

    private fun startOnMainThread() {
        for (a in 1..5_00_00_000) {
            Log.d(TAG, "onStartCommand: $a")
        }
    }

    private fun startOnBackgroundThread() {
        CoroutineScope(Dispatchers.Default).launch {
            for (a in 1..5_00_00_000) {
                Log.d(TAG, "onStartCommand: $a")
            }
        }
    }

    private fun stopAfterProcess() {
        for (a in 1..2000) {
            Log.d(TAG, "onStartCommand: $a")
        }
        stopSelf()
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.d(TAG, "onBind: called")
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: called")
    }
}