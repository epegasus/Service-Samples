package dev.epegasus.servicesamples.services.foreground

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import dev.epegasus.servicesamples.MainActivity
import dev.epegasus.servicesamples.R
import dev.epegasus.servicesamples.TAG


@Suppress("DEPRECATION")
class MyForegroundService : Service() {

    lateinit var notificationManager: NotificationManager

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

    /**
     *  ANR can be generate if later than 5 seconds
     *
     *  @see
     *         -> STOP_FOREGROUND_DETACH: Notification will remain after killing foreground service
     *         -> STOP_FOREGROUND_REMOVE: Notification will be removed as well
     */

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.d(TAG, "onStartCommand: called")

        val builder = createNotification()
        startForeground(100, builder.build())


        Handler(Looper.getMainLooper()).postDelayed({
            Log.d(TAG, "stopForeground: called")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                stopForeground(STOP_FOREGROUND_REMOVE)
            } else {
                stopForeground(true)
            }
            stopSelf()
        }, 60_000)


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

    override fun onBind(intent: Intent): IBinder? {
        Log.d(TAG, "onBind: called")
        return null
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: called")
        super.onDestroy()
    }
}