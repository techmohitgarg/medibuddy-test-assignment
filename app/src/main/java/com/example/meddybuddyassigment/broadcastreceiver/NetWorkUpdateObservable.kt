package com.example.meddybuddyassigment.broadcastreceiver

import java.util.*

class NetWorkUpdateObservable : Observable() {
    companion object {
        lateinit var instance_: NetWorkUpdateObservable

        fun init() {
            instance_ = NetWorkUpdateObservable()
        }

        fun getInstance() = instance_
    }

    fun updateValue(isConnected: Boolean) {
        synchronized(this) {
            setChanged()
            notifyObservers(isConnected)
        }
    }
}