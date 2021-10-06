package com.groofy.login.remote

import com.example.meddybuddyassigment.chat.model.ChatResponseModel
import com.example.meddybuddyassigment.chat.remote.ChatApiServices

/**
 * @author Mohit Garg
 */
class ChatDataSourceImp(private val chatApiServices: ChatApiServices) : ChatDataSource {
    override suspend fun sendMessage(data: HashMap<String, Any>): ChatResponseModel {
        return chatApiServices.sendMessage(data)
    }
}