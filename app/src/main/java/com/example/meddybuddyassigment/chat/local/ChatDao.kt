package com.example.meddybuddyassigment.chat.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ChatDao {
    @Query("SELECT * FROM ChatEntity WHERE externalID IN (:externalID)")
    suspend fun loadAllByIds(externalID: String): List<ChatEntity>

    @Insert
    suspend fun insertMessage(chatEntity: ChatEntity): Long

    @Update
    suspend fun updateMessage(chatEntity: ChatEntity)

    @Query("SELECT * FROM ChatEntity WHERE isSync like (:isSync) and externalID like (:externalID)")
    suspend fun loadAllUnSyncMessage(isSync: Boolean, externalID: String): List<ChatEntity>
}