package com.example.meddybuddyassigment.chat.local


interface LocalSource {
    suspend fun loadAllByIds(externalID: String): List<ChatEntity>

    suspend fun insertMessage(chatEntity: ChatEntity): Long

    suspend fun insertUser(userEntity: UserEntity)

    suspend fun loadAllUser(): List<UserEntity>
}