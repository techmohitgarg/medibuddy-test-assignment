package com.example.meddybuddyassigment.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.meddybuddyassigment.chat.local.ChatDao
import com.example.meddybuddyassigment.chat.local.ChatEntity
import com.example.meddybuddyassigment.chat.local.UserDao
import com.example.meddybuddyassigment.chat.local.UserEntity

@Database(entities = [ChatEntity::class, UserEntity::class], version = 1)
abstract class ChatDatabase : RoomDatabase() {
    abstract fun chatDao(): ChatDao
    abstract fun userDao(): UserDao
}