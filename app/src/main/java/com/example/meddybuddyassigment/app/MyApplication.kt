package com.example.meddybuddyassigment.app

import android.app.Application
import com.example.meddybuddyassigment.broadcastreceiver.BroadCastObservable
import com.example.meddybuddyassigment.di.DaggerProvider
import com.example.meddybuddyassigment.di.component.AppComponent

class MyApplication : Application() {
    private lateinit var mAppComponent: AppComponent

    companion object {
        lateinit var instance_: MyApplication

        fun getInstance() = instance_
    }

    override fun onCreate() {
        super.onCreate()
        DaggerProvider.initComponent(this)
        DaggerProvider.getAppComponent()?.inject(this)

        BroadCastObservable.init()

    }

}