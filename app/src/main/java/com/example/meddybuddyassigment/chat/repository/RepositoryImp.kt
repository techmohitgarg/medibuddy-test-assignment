package com.example.meddybuddyassigment.chat.repository

import com.example.meddybuddyassigment.chat.local.ChatEntity
import com.example.meddybuddyassigment.chat.model.ChatResponseModel
import com.example.meddybuddyassigment.chat.local.LocalSource
import com.example.meddybuddyassigment.chat.local.UserEntity
import com.example.meddybuddyassigment.chat.model.ChatMessage
import com.example.meddybuddyassigment.chat.remote.ChatDataSource

class RepositoryImp(
    private val chatDataSource: ChatDataSource,
    private val chatLocalSource: LocalSource
) : Repository {
    override suspend fun sendMessage(data: HashMap<String, Any>): ChatResponseModel {
        return chatDataSource.sendMessage(data)
    }

    override suspend fun getAllMessage(externalID: String): List<ChatMessage> {
        val listOfMessage = chatLocalSource.loadAllByIds(externalID)

        val listOfChatMessage = mutableListOf<ChatMessage>()
        listOfMessage.forEach {
            listOfChatMessage.add(ChatMessage(it.chatBotName, it.chatBotID, it.message, it.sender))
        }
        return listOfChatMessage
    }

    override suspend fun insertMessage(chatEntity: ChatEntity): Long {
        return chatLocalSource.insertMessage(chatEntity)
    }

    override suspend fun updateMessage(chatEntity: ChatEntity) {
        return chatLocalSource.updateMessage(chatEntity)
    }

    override suspend fun insertUser(userEntity: UserEntity) {
        return chatLocalSource.insertUser(userEntity)
    }

    override suspend fun loadAllUser(): List<UserEntity> {
        return chatLocalSource.loadAllUser()
    }

    override suspend fun loadAllUnSyncMessage(
        isSync: Boolean,
        externalID: String
    ): List<ChatEntity> {
        return chatLocalSource.loadAllUnSyncMessage(isSync, externalID)
    }
}