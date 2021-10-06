package com.example.meddybuddyassigment.chat.repository

import com.example.meddybuddyassigment.chat.model.ChatResponseModel
import retrofit2.http.QueryMap

/**
 * @author Mohit Garg
 */
interface ChatRepository {
    suspend fun sendMessage(@QueryMap data: HashMap<String, Any>): ChatResponseModel
}