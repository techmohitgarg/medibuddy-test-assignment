package com.example.meddybuddyassigment.chat.views

import android.os.Bundle
import com.example.meddybuddyassigment.R
import com.example.meddybuddyassigment.databinding.ChatActivityDataBinding
import com.example.meddybuddyassigment.di.DaggerProvider
import com.groofy.common.base.activity.BaseDataBindingActivity

class ChatActivity : BaseDataBindingActivity<ChatActivityDataBinding>(R.layout.activity_chat) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun injectDaggerComponent() {
        DaggerProvider.getAppComponent()?.inject(this)
    }

    override fun onDataBindingCreated() {

    }
}