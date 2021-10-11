package com.example.meddybuddyassigment.chat.remote

import com.example.meddybuddyassigment.chat.model.ChatResponseModel

class ChatDataSourceImp(private val chatApiServices: ChatApiServices) : ChatDataSource {
    override suspend fun sendMessage(data: HashMap<String, Any>): ChatResponseModel {
        return chatApiServices.sendMessage(data)
    }
}