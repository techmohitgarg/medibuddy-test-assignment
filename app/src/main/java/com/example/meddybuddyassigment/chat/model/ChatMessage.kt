package com.example.meddybuddyassigment.chat.model

data class ChatMessage(
    val chatBotName: String = "",
    val chatBotID: Int,
    val message: String,
    val sender: Boolean
)