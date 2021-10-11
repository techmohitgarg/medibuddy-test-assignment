package com.example.meddybuddyassigment.chat.views.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.activity.viewModels
import com.example.meddybuddyassigment.R
import com.example.meddybuddyassigment.chat.local.UserEntity
import com.example.meddybuddyassigment.chat.viewmodel.ChatViewModel
import com.example.meddybuddyassigment.chat.viewmodel.ChatViewModelViewModelFactory
import com.example.meddybuddyassigment.chat.views.adapter.SetUserAdapter
import com.example.meddybuddyassigment.databinding.AddUserActivityDataBinding
import com.example.meddybuddyassigment.di.DaggerProvider
import com.example.meddybuddyassigment.network.Status
import com.example.meddybuddyassigment.util.ConstantsUtil
import com.example.meddybuddyassigment.common.base.activity.BaseDataBindingActivity
import javax.inject.Inject

class AddUserActivity :
    BaseDataBindingActivity<AddUserActivityDataBinding>(R.layout.activity_add_user) {

    @Inject
    lateinit var viewModelFactory: ChatViewModelViewModelFactory

    private val viewModel by viewModels<ChatViewModel>(factoryProducer = { viewModelFactory })
    private var setUserAdapter: SetUserAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObserver()
        // Set Toolbar Title
        supportActionBar?.title = "User List"
    }

    override fun injectDaggerComponent() {
        DaggerProvider.getAppComponent()?.inject(this)
    }

    override fun onDataBindingCreated() {
        setUserListAdapter()
        setListener()
        binding.adapter = setUserAdapter

        // Get All User form the database
        viewModel.getAllUser()
    }

    private fun setUserListAdapter(data: List<UserEntity> = emptyList()) {
        if (setUserAdapter == null)
            setUserAdapter = SetUserAdapter() { userData, i ->
                startActivity(Intent(this, ChatActivity::class.java).apply {
                    putExtras(Bundle().apply {
                        putString(ConstantsUtil.USERNAME, userData.externalID)
                    })
                })
            }
        else {
            setUserAdapter?.apply { updateAll(data.toMutableList()) }
        }
    }

    private fun setupObserver() {
        viewModel.resultUserList.observe(this, {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.ERROR -> {
                }
                Status.SUCCESS -> {
                    val result = it.data
                    result?.let { it ->
                        setUserListAdapter(result)
                    }
                }
            }
        })
    }

    private fun setListener() {
        binding.btnAdd.setOnClickListener {
            val username = binding.edtEnterUserName.text.toString()
            if (!TextUtils.isEmpty(username)) {
                viewModel.insertUser(userEntity = UserEntity(0, username))
                // Update the Message into list

                //Clear the edit text
                binding.edtEnterUserName.setText("")

                //Get List From the DB
                viewModel.getAllUser()
            }
        }
    }
}