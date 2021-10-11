package com.example.meddybuddyassigment.chat.local

import com.example.meddybuddyassigment.chat.local.ChatDao
import com.example.meddybuddyassigment.chat.local.ChatEntity
import com.example.meddybuddyassigment.chat.local.LocalSource
import com.example.meddybuddyassigment.chat.local.UserDao
import com.example.meddybuddyassigment.chat.local.UserEntity

class LocalSourceImp(private val chatDao: ChatDao, private val userDao: UserDao) : LocalSource {
    override suspend fun loadAllByIds(externalID: String): List<ChatEntity> {
        return chatDao.loadAllByIds(externalID)
    }

    override suspend fun insertMessage(chatEntity: ChatEntity) {
        return chatDao.insertMessage(chatEntity)
    }

    override suspend fun insertUser(userEntity: UserEntity) {
        return userDao.insertUser(userEntity)
    }

    override suspend fun loadAllUser(): List<UserEntity> {
        return userDao.loadAllUser()
    }
}