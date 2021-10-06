package com.example.meddybuddyassigment.chat.views.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.activity.viewModels
import com.example.meddybuddyassigment.R
import com.example.meddybuddyassigment.chat.model.ChatMessage
import com.example.meddybuddyassigment.chat.viewmodel.ChatViewModel
import com.example.meddybuddyassigment.chat.viewmodel.ChatViewModelViewModelFactory
import com.example.meddybuddyassigment.chat.views.adapter.SetChatAdapter
import com.example.meddybuddyassigment.databinding.ChatActivityDataBinding
import com.example.meddybuddyassigment.di.DaggerProvider
import com.example.meddybuddyassigment.network.Status
import com.example.meddybuddyassigment.util.ConstantsUtil
import com.google.gson.Gson
import com.groofy.common.base.activity.BaseDataBindingActivity
import javax.inject.Inject

class ChatActivity : BaseDataBindingActivity<ChatActivityDataBinding>(R.layout.activity_chat) {

    @Inject
    lateinit var viewModelFactory: ChatViewModelViewModelFactory

    private val viewModel by viewModels<ChatViewModel>(factoryProducer = { viewModelFactory })

    private var setChatAdapter: SetChatAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObserver()
    }

    override fun injectDaggerComponent() {
        DaggerProvider.getAppComponent()?.inject(this)
    }

    override fun onDataBindingCreated() {
        setAddressListAdapter()
        setListener()
        binding.adapter = setChatAdapter
    }


    private fun setListener() {
        binding.btnSend.setOnClickListener {
            val mesage = binding.edtEnterMessage.text.toString()
            if (!TextUtils.isEmpty(mesage)) {
                val dataMap = hashMapOf<String, Any>(
                    "apiKey" to "${ConstantsUtil.API_KEY}",
                    "chatBotID" to "${ConstantsUtil.CHAT_BOX_ID}",
                    "externalID" to "mohitgarg103",
                    "message" to mesage
                )
                viewModel.sendMessage(dataMap)
                // Update the Message into list
                updateMessage(message = mesage, sender = true)
                //Clear the edit text
                binding.edtEnterMessage.setText("")
            }
        }
    }

    private fun setupObserver() {
        viewModel.resultMessage.observe(this, {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.ERROR -> {
                }
                Status.SUCCESS -> {
                    val result = it.data
                    result?.let { it ->
                        updateMessage(it.message.chatBotName, it.message.message, false)
                    }
                }
            }
        })
    }

    private fun setAddressListAdapter(data: List<ChatMessage> = emptyList()) {
        if (setChatAdapter == null)
            setChatAdapter = SetChatAdapter()
        else {
            setChatAdapter?.apply { updateAll(data.toMutableList()) }
        }
    }

    private fun updateMessage(chatBotName: String = "", message: String, sender: Boolean) {
        val chatMessage = ChatMessage(
            chatBotName = chatBotName,
            chatBotID = ConstantsUtil.CHAT_BOX_ID.toInt(),
            message = message,
            sender = sender
        )
        setChatAdapter?.let {
            it.update(chatMessage)
        }
    }

}