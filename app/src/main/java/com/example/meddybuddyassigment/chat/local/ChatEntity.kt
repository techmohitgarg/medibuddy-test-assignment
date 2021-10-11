package com.example.meddybuddyassigment.chat.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChatEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "externalID") val externalID: String,
    @ColumnInfo(name = "message") val message: String,
    @ColumnInfo(name = "sender") val sender: Boolean,
    @ColumnInfo(name = "chatBotName") val chatBotName: String,
    @ColumnInfo(name = "chatBotID") val chatBotID: Int
)