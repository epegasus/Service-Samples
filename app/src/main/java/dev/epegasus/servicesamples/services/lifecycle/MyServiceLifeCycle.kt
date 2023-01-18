package dev.epegasus.servicesamples.services.lifecycle

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.IInterface
import android.os.Parcel
import android.util.Log
import android.widget.Toast
import dev.epegasus.servicesamples.TAG
import java.io.FileDescriptor

class MyServiceLifeCycle : Service() {

    override fun onCreate() {
        super.onCreate()
        // The service is being created
        Log.d(TAG, "onCreate: called")
    }


    /**
     * @Deprecated
     */

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
    }


    /**
     *  @param intent: It will be received in this method, if any data has been passed, we can get it here!
     *  @return     1) START_STICKY:		    (Will restart with null intent)
     *              2) START_NOT_STICKY:		(Will not restart)   e.g. for alarm closing
     *              3) START_REDELIVER_INTENT:	(Will restart with previous intent)
     */

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // The service is starting, due to a call to startService()
        Log.d(TAG, "onStartCommand: called")
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show()


        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        // In case of onStartCommand
        // return null

        // A client is binding to the service with bindService()
        return object : IBinder{
            override fun getInterfaceDescriptor(): String? {
                return null
            }

            override fun pingBinder(): Boolean {
                return false
            }

            override fun isBinderAlive(): Boolean {
                return false
            }

            override fun queryLocalInterface(descriptor: String): IInterface? {
                return null
            }

            override fun dump(fd: FileDescriptor, args: Array<out String>?) {

            }

            override fun dumpAsync(fd: FileDescriptor, args: Array<out String>?) {

            }

            override fun transact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
                return false
            }

            override fun linkToDeath(recipient: IBinder.DeathRecipient, flags: Int) {

            }

            override fun unlinkToDeath(recipient: IBinder.DeathRecipient, flags: Int): Boolean {
                return false
            }
        }

    }

    override fun onUnbind(intent: Intent): Boolean {
        // All clients have unbound with unbindService()
        return true
    }

    override fun onRebind(intent: Intent) {
        // A client is binding to the service with bindService(),
        // after onUnbind() has already been called
    }

    override fun onDestroy() {
        // The service is no longer used and is being destroyed
        Log.d(TAG, "onDestroy: called")
    }
}