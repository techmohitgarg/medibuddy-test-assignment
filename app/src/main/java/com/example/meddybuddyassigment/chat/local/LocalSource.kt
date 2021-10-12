package com.example.meddybuddyassigment.chat.local


interface LocalSource {
    suspend fun loadAllByIds(externalID: String): List<ChatEntity>

    suspend fun loadAllUnSyncMessage(isSync: Boolean, externalID: String): List<ChatEntity>

    suspend fun insertMessage(chatEntity: ChatEntity): Long

    suspend fun updateMessage(chatEntity: ChatEntity)

    suspend fun insertUser(userEntity: UserEntity)

    suspend fun loadAllUser(): List<UserEntity>
}