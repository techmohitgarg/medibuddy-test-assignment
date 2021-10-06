package com.example.meddybuddyassigment.chat.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meddybuddyassigment.chat.model.ChatResponseModel
import com.example.meddybuddyassigment.chat.repository.ChatRepository
import com.example.meddybuddyassigment.errorProvider.ErrorProvider
import kotlinx.coroutines.launch

class ChatViewModel(
    private val chatRepository: ChatRepository,
    private val errorProvider: ErrorProvider
) : ViewModel() {

    private val _resultMessage =
        MutableLiveData<com.example.meddybuddyassigment.network.Result<ChatResponseModel>>()
    val resultMessage: LiveData<com.example.meddybuddyassigment.network.Result<ChatResponseModel>>
        get() = _resultMessage

    fun sendMessage(data: HashMap<String, Any>) {
        viewModelScope.launch {
            try {
                _resultMessage.postValue(com.example.meddybuddyassigment.network.Result.loading())
                val response = chatRepository.sendMessage(data)
                _resultMessage.postValue(
                    com.example.meddybuddyassigment.network.Result.success(
                        response
                    )
                )
            } catch (exception: Exception) {
                _resultMessage.postValue(
                    com.example.meddybuddyassigment.network.Result.error(
                        errorProvider.getErrorMessage(exception)
                    )
                )
            }
        }
    }
}