package com.example.meddybuddyassigment.service

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.IntentFilter
import android.util.Log
import com.example.meddybuddyassigment.broadcastreceiver.NetworkListener
import com.example.meddybuddyassigment.util.ConstantsUtil

class NetworkJobScheduler : JobService() {

    private lateinit var networkListener: NetworkListener
    override fun onCreate() {
        super.onCreate()
        Log.i("NetworkJobScheduler", "onCreate")
        networkListener = NetworkListener()
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.i("NetworkJobScheduler", "onStartJob")
        registerReceiver(networkListener, IntentFilter(ConstantsUtil.NETWORK_FILTER))
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.i("NetworkJobScheduler", "onStopJob")
        unregisterReceiver(networkListener)
        return true
    }
}