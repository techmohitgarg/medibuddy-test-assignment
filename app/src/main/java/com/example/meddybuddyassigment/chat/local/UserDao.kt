package com.example.meddybuddyassigment.chat.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM UserEntity")
    suspend fun loadAllUser(): List<UserEntity>

    @Insert
    suspend fun insertUser(vararg userEntity: UserEntity)
}