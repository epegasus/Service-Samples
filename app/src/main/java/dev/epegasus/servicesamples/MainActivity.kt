package dev.epegasus.servicesamples

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import dev.epegasus.servicesamples.databinding.ActivityMainBinding
import dev.epegasus.servicesamples.services.background.MyBackgroundService
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
        initBackgroundService()
        //initForegroundService()
        //initBoundService()
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
        startService(intent)
    }

    private fun initBoundService() {

    }
}