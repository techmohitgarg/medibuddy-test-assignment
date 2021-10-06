package com.example.meddybuddyassigment.di.component

import com.example.meddybuddyassigment.di.modules.ApplicationModule
import com.example.meddybuddyassigment.di.modules.NetworkModule
import com.example.meddybuddyassigment.app.MyApplication
import com.example.meddybuddyassigment.chat.views.activity.ChatActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        NetworkModule::class
    ]
)
interface AppComponent {
    fun inject(myApplication: MyApplication)
    fun inject(myApplication: ChatActivity)
}