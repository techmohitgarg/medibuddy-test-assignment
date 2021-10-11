package com.example.meddybuddyassigment.chat.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meddybuddyassigment.chat.local.ChatEntity
import com.example.meddybuddyassigment.chat.local.UserEntity
import com.example.meddybuddyassigment.chat.model.ChatMessage
import com.example.meddybuddyassigment.chat.model.ChatResponseModel
import com.example.meddybuddyassigment.chat.repository.Repository
import com.example.meddybuddyassigment.errorProvider.ErrorProvider
import kotlinx.coroutines.launch

class ChatViewModel(
    private val chatRepository: Repository,
    private val errorProvider: ErrorProvider
) : ViewModel() {

    private val _resultMessage =
        MutableLiveData<com.example.meddybuddyassigment.network.Result<ChatResponseModel>>()
    val resultMessage: LiveData<com.example.meddybuddyassigment.network.Result<ChatResponseModel>>
        get() = _resultMessage


    private val _resultMessageList =
        MutableLiveData<com.example.meddybuddyassigment.network.Result<List<ChatMessage>>>()
    val resultMessageList: LiveData<com.example.meddybuddyassigment.network.Result<List<ChatMessage>>>
        get() = _resultMessageList


    private val _resultUserList =
        MutableLiveData<com.example.meddybuddyassigment.network.Result<List<UserEntity>>>()
    val resultUserList: LiveData<com.example.meddybuddyassigment.network.Result<List<UserEntity>>>
        get() = _resultUserList

    fun insertMessage(chatEntity: ChatEntity) {
        viewModelScope.launch {
            try {
                chatRepository.insertMessage(chatEntity)
            } catch (exception: Exception) {
            }
        }
    }

    fun insertUser(userEntity: UserEntity) {
        viewModelScope.launch {
            try {
                chatRepository.insertUser(userEntity)
            } catch (exception: Exception) {
            }
        }
    }

    fun getAllMessages(externalID: String) {
        viewModelScope.launch {
            try {
                val data = chatRepository.getAllMessage(externalID)
                _resultMessageList.postValue(
                    com.example.meddybuddyassigment.network.Result.success(
                        data
                    )
                )
            } catch (exception: Exception) {
                _resultMessageList.postValue(
                    com.example.meddybuddyassigment.network.Result.error(
                        errorProvider.getErrorMessage(exception)
                    )
                )
            }
        }
    }

    fun getAllUser() {
        viewModelScope.launch {
            try {
                val data = chatRepository.loadAllUser()
                _resultUserList.postValue(
                    com.example.meddybuddyassigment.network.Result.success(
                        data
                    )
                )
            } catch (exception: Exception) {
                _resultUserList.postValue(
                    com.example.meddybuddyassigment.network.Result.error(
                        errorProvider.getErrorMessage(exception)
                    )
                )
            }
        }
    }

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