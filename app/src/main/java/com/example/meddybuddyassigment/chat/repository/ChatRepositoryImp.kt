package com.example.meddybuddyassigment.chat.repository

import com.example.meddybuddyassigment.chat.model.ChatResponseModel
import com.example.meddybuddyassigment.chat.remote.ChatApiServices
import com.groofy.login.remote.ChatDataSource

/**
 * @author Mohit Garg
 */
class ChatRepositoryImp(private val chatDataSource: ChatDataSource) : ChatRepository {
    override suspend fun sendMessage(data: HashMap<String, Any>): ChatResponseModel {
        return chatDataSource.sendMessage(data)
    }
}