package com.example.meddybuddyassigment.broadcastreceiver

import java.util.*

class BroadCastObservable : Observable() {
    companion object {
        lateinit var instance_: BroadCastObservable

        fun init() {
            instance_ = BroadCastObservable()
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