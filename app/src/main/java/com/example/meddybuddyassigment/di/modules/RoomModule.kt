package com.example.meddybuddyassigment.di.modules


import android.app.Application
import androidx.room.Room
import com.example.meddybuddyassigment.chat.local.ChatDao
import com.example.meddybuddyassigment.chat.local.LocalSource
import com.example.meddybuddyassigment.chat.local.UserDao
import com.example.meddybuddyassigment.room.ChatDatabase
import com.example.meddybuddyassigment.chat.local.LocalSourceImp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule(context: Application) {

    private var demoDatabase: ChatDatabase = Room.databaseBuilder(
        context,
        ChatDatabase::class.java, "chat-db"
    ).allowMainThreadQueries().build()

    @Singleton
    @Provides
    fun providesRoomDatabase(): ChatDatabase {
        return demoDatabase
    }

    @Singleton
    @Provides
    fun providesChatDao(demoDatabase: ChatDatabase): ChatDao {
        return demoDatabase.chatDao()
    }

    @Singleton
    @Provides
    fun providesUserDao(demoDatabase: ChatDatabase): UserDao {
        return demoDatabase.userDao()
    }

    @Singleton
    @Provides
    fun provideChatLocalService(chatDao: ChatDao, userDao: UserDao): LocalSource {
        return LocalSourceImp(chatDao, userDao)
    }
}