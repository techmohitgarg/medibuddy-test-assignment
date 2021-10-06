package com.example.meddybuddyassigment.chat.remote


import com.example.meddybuddyassigment.chat.model.ChatResponseModel
import retrofit2.http.*

interface ChatApiServices {
    @GET("api/chat/")
    suspend fun sendMessage(@QueryMap data: HashMap<String, Any>): ChatResponseModel
}