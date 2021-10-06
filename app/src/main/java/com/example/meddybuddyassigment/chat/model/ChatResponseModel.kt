package com.example.meddybuddyassigment.chat.model

data class ChatResponseModel(
    val success: Int,
    val errorMessage: String,
    val message: Message
)

data class Message(
    val chatBotName: String,
    val chatBotID: Int,
    val message: String,
    val emotion: String
)