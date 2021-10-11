package com.example.meddybuddyassigment.chat.repository

import com.example.meddybuddyassigment.chat.local.ChatEntity
import com.example.meddybuddyassigment.chat.local.UserEntity
import com.example.meddybuddyassigment.chat.model.ChatMessage
import com.example.meddybuddyassigment.chat.model.ChatResponseModel
import retrofit2.http.QueryMap

/**
 * @author Mohit Garg
 */
interface Repository {
    suspend fun sendMessage(@QueryMap data: HashMap<String, Any>): ChatResponseModel

    suspend fun getAllMessage(externalID: String): List<ChatMessage>

    suspend fun insertMessage(chatEntity: ChatEntity)

    suspend fun insertUser(userEntity: UserEntity)

    suspend fun loadAllUser(): List<UserEntity>
}