package com.example.meddybuddyassigment.chat.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ChatDao {
    @Query("SELECT * FROM ChatEntity WHERE externalID IN (:externalID)")
    suspend fun loadAllByIds(vararg externalID: String): List<ChatEntity>

    @Insert
    suspend fun insertMessage(vararg chatEntity: ChatEntity)
}