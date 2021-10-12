package com.example.meddybuddyassigment.di.component

import com.example.meddybuddyassigment.di.modules.ApplicationModule
import com.example.meddybuddyassigment.di.modules.NetworkModule
import com.example.meddybuddyassigment.app.MyApplication
import com.example.meddybuddyassigment.chat.views.activity.UserListActivity
import com.example.meddybuddyassigment.chat.views.activity.ChatActivity
import com.example.meddybuddyassigment.di.modules.RoomModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        NetworkModule::class,
        RoomModule::class
    ]
)
interface AppComponent {
    fun inject(myApplication: MyApplication)
    fun inject(myApplication: ChatActivity)
    fun inject(addUserActivity: UserListActivity)
}