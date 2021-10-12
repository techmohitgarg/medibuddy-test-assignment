package com.example.meddybuddyassigment.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.meddybuddyassigment.util.ServiceManager

class NetworkListener : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val service = ServiceManager(context)
            NetWorkUpdateObservable.getInstance().updateValue(service.isNetworkAvailable())
        }
    }
}