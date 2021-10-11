package com.example.meddybuddyassigment.chat.remote

import com.example.meddybuddyassigment.chat.model.ChatResponseModel
import retrofit2.http.QueryMap

/**
 * @author Mohit Garg
 */
interface ChatDataSource {
    suspend fun sendMessage(@QueryMap data: HashMap<String, Any>): ChatResponseModel
}