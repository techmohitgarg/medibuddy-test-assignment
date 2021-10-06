package com.example.meddybuddyassigment.di

import com.example.meddybuddyassigment.app.MyApplication
import com.example.meddybuddyassigment.di.component.AppComponent
import com.example.meddybuddyassigment.di.component.DaggerAppComponent
import com.example.meddybuddyassigment.di.modules.ApplicationModule

class DaggerProvider {

    companion object {
        private var appComponent: AppComponent? = null

        fun initComponent(application: MyApplication) {
            appComponent = DaggerAppComponent.builder()
                .applicationModule(ApplicationModule(application))
                .build()
        }

        fun getAppComponent(): AppComponent? {
            return appComponent
        }
    }
}