package dev.epegasus.servicesamples.services.bound

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import dev.epegasus.servicesamples.MainActivity
import dev.epegasus.servicesamples.R
import dev.epegasus.servicesamples.TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Suppress("DEPRECATION")
class MyBoundService : Service() {

    private lateinit var notificationManager: NotificationManager

    /**
     * Init all resources
     */

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: called")
        notificationManager = getSystemService(NotificationManager::class.java)
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val id = "em_id"
        val name = "Soneya"
        val description = "Nawaaa Ayaa?"
        val channel = NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW).apply {
            this.description = description
        }
        notificationManager.createNotificationChannel(channel)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val builder = createNotification()
        startForeground(100, builder.build())

        startWorking()

        return START_STICKY
    }

    private fun createNotification(): NotificationCompat.Builder {
        // If the notification supports a direct reply action, use PendingIntent.FLAG_MUTABLE instead.
        val pendingIntent: PendingIntent = Intent(this, MainActivity::class.java).let { notificationIntent ->
            PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
        }

        val channelId = "em_id"
        val bitTextStyle = NotificationCompat.BigTextStyle().bigText("Much longer text that cannot fit one line...")

        return NotificationCompat.Builder(this, channelId).setSmallIcon(R.drawable.ic_launcher_foreground).setContentTitle("My notification").setContentText("Much longer text that cannot fit one line...").setStyle(bitTextStyle).setPriority(NotificationCompat.PRIORITY_DEFAULT).setContentIntent(pendingIntent)
    }


    /* -------------------------------------- Bound Logic -------------------------------------- */

    private var counter = 0
    private val binder = LocalBinder()

    private fun startWorking() {
        CoroutineScope(Dispatchers.IO).launch {
            if (counter <= 100) {
                Log.d(TAG, "startWorking: $counter")
                counter++
                delay(1000)
                startWorking()
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    stopForeground(STOP_FOREGROUND_REMOVE)
                } else {
                    stopForeground(true)
                }
                stopSelf()
            }
        }
    }

    override fun onBind(intent: Intent): IBinder {
        Log.d(TAG, "onBind: called")
        return binder
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        Log.d(TAG, "onRebind: called")
    }

    fun getCounter(): Int {
        return counter
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: called")
        super.onDestroy()
    }

    inner class LocalBinder : Binder() {
        fun getService(): MyBoundService = this@MyBoundService
    }
}