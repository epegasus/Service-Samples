package dev.epegasus.servicesamples

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import dev.epegasus.servicesamples.databinding.ActivityMainBinding
import dev.epegasus.servicesamples.services.background.MyBackgroundService
import dev.epegasus.servicesamples.services.bound.MyBoundService
import dev.epegasus.servicesamples.services.foreground.MyForegroundService
import dev.epegasus.servicesamples.services.lifecycle.MyServiceLifeCycle

const val TAG = "MyTag"

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    // think you are going to make instagram & Music Player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //initServiceLifeCycle()
        //initBackgroundService()
        //initForegroundService()
        //initBoundService()
        initBoundService()

        binding.btnBind.setOnClickListener { onBindClick() }
        binding.mbResult.setOnClickListener { onResultClick() }
    }

    private fun initServiceLifeCycle() {
        val intent = Intent(this, MyServiceLifeCycle::class.java)
        startService(intent)
    }

    private fun initBackgroundService() {
        val intent = Intent(this, MyBackgroundService::class.java)
        startService(intent)
        //startService(intent)

        Handler(Looper.getMainLooper()).postDelayed({
            startService(intent)
        }, 3000)
    }

    private fun initForegroundService() {
        val intent = Intent(this, MyForegroundService::class.java)
        ContextCompat.startForegroundService(this, intent)
    }

    fun isServiceRunning(serviceClassName: String?): Boolean {
        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        for (runningServiceInfo in activityManager.getRunningServices(Int.MAX_VALUE)) {
            if (runningServiceInfo.service.className == serviceClassName) {
                return true
            }
        }
        return false
    }


    /* --------------------------------------- Bound --------------------------------------- */

    private var serviceConnection: ServiceConnection? = null
    private var myBoundService: MyBoundService? = null
    private val boundIntent by lazy { Intent(this, MyBoundService::class.java) }

    private fun initBoundService() {
        ContextCompat.startForegroundService(this, boundIntent)
    }

    private fun onBindClick() {
        if (serviceConnection != null) {
            return
        }

        serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                binding.tvStatus.text = "Connection Established"
                myBoundService = (service as MyBoundService.LocalBinder).getService()
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                binding.tvStatus.text = "Connection Disconnected"
            }
        }.also {
            bindService(boundIntent, it, BIND_AUTO_CREATE)
        }
    }

    private fun onResultClick() {
        val result = "${myBoundService?.getCounter() ?: -1}"
        binding.tvStatus.text = result
        if (myBoundService?.getCounter() == 101) {
            serviceConnection?.let { unbindService(it) }
        }
    }
}