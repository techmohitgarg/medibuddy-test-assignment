package com.example.meddybuddyassigment.chat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.meddybuddyassigment.chat.repository.ChatRepository
import com.example.meddybuddyassigment.errorProvider.ErrorProvider
import javax.inject.Inject

class ChatViewModelViewModelFactory @Inject constructor(
    private val chatRepository: ChatRepository,
    private val errorProvider: ErrorProvider
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            return ChatViewModel(chatRepository, errorProvider) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}