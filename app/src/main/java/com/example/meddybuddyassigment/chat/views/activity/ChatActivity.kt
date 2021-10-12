package com.example.meddybuddyassigment.chat.views.activity

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import androidx.activity.viewModels
import com.example.meddybuddyassigment.R
import com.example.meddybuddyassigment.chat.local.ChatEntity
import com.example.meddybuddyassigment.chat.model.ChatMessage
import com.example.meddybuddyassigment.chat.viewmodel.ChatViewModel
import com.example.meddybuddyassigment.chat.viewmodel.ChatViewModelViewModelFactory
import com.example.meddybuddyassigment.chat.views.adapter.SetChatAdapter
import com.example.meddybuddyassigment.databinding.ChatActivityDataBinding
import com.example.meddybuddyassigment.di.DaggerProvider
import com.example.meddybuddyassigment.network.Status
import com.example.meddybuddyassigment.util.ConstantsUtil
import com.example.meddybuddyassigment.common.base.activity.BaseDataBindingActivity
import javax.inject.Inject
import android.view.MenuItem
import com.example.meddybuddyassigment.broadcastreceiver.BroadCastObservable
import com.example.meddybuddyassigment.service.NetworkJobScheduler
import com.example.meddybuddyassigment.util.ServiceManager
import java.util.*


class ChatActivity : BaseDataBindingActivity<ChatActivityDataBinding>(R.layout.activity_chat),
    Observer {

    @Inject
    lateinit var viewModelFactory: ChatViewModelViewModelFactory

    private val viewModel by viewModels<ChatViewModel>(factoryProducer = { viewModelFactory })

    private var setChatAdapter: SetChatAdapter? = null
    private var jobScheduler: JobScheduler? = null
    private lateinit var jobInfo: JobInfo

    private var externalID: String = ""

    @Inject
    lateinit var serviceManager: ServiceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObserver()
        // Set Toolbar Title
        supportActionBar?.title = "$externalID"
    }

    override fun onStart() {
        super.onStart()
        BroadCastObservable.getInstance().addObserver(this)
        scheduleJob()
    }

    override fun onStop() {
        super.onStop()

        jobScheduler?.apply {
            cancel(jobInfo.id)
        }
    }

    override fun processIntent(intent: Intent) {
        getIntentData()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun injectDaggerComponent() {
        DaggerProvider.getAppComponent()?.inject(this)
    }

    override fun onDataBindingCreated() {
        setAddressListAdapter()
        setListener()
        binding.adapter = setChatAdapter

        // Get All the Message form the local DB
        viewModel.getAllMessages(externalID)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.getItemId()) {
            R.id.action_add -> {
                startActivity(Intent(this, AddUserActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun update(o: Observable?, arg: Any?) {
        arg?.let {
            val isConnected = arg as Boolean
            Log.e("isConnected ", isConnected.toString())
        }
    }

    private fun getIntentData() {
        intent?.extras?.let {
            externalID =
                it.getString(ConstantsUtil.USERNAME, "")

            Log.e("UserName", externalID)
        }
    }

    private fun setListener() {
        binding.btnSend.setOnClickListener {
            val mesage = binding.edtEnterMessage.text.toString()
            if (TextUtils.isEmpty(mesage)) {
                return@setOnClickListener
            }
            // This is safe check to prevent the api call in case of
            if (TextUtils.isEmpty(externalID)) {
                return@setOnClickListener
            }
            if (!serviceManager.isNetworkAvailable()) {

            } else {
                val dataMap = hashMapOf<String, Any>(
                    "apiKey" to "${ConstantsUtil.API_KEY}",
                    "chatBotID" to "${ConstantsUtil.CHAT_BOX_ID}",
                    "externalID" to externalID,
                    "message" to mesage
                )
                viewModel.sendMessage(dataMap)
                // Update the Message into list
                updateMessage(message = mesage, sender = true, isSync = true)
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
                        updateMessage(
                            it.message.chatBotName, it.message.message,
                            sender = false,
                            isSync = true
                        )
                    }
                }
            }
        })

        viewModel.resultMessageList.observe(this, {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.ERROR -> {
                }
                Status.SUCCESS -> {
                    val result = it.data
                    result?.let { it ->
                        setAddressListAdapter(result)
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

    private fun updateMessage(
        chatBotName: String = "",
        message: String,
        sender: Boolean,
        isSync: Boolean
    ) {
        val chatMessage = ChatMessage(
            chatBotName = chatBotName,
            chatBotID = ConstantsUtil.CHAT_BOX_ID.toInt(),
            message = message,
            sender = sender
        )
        setChatAdapter?.let {
            it.update(chatMessage)
        }
        //Insert into Local DB
        viewModel.insertMessage(
            ChatEntity(
                0,
                externalID,
                message,
                sender,
                chatBotName,
                ConstantsUtil.CHAT_BOX_ID.toInt(),
                isSync
            )
        )
    }

    private fun scheduleJob() {
        jobInfo = JobInfo.Builder(0, ComponentName(this, NetworkJobScheduler::class.java))
            .setRequiresCharging(true)
            .setMinimumLatency(1000)
            .setOverrideDeadline(2000)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .setPersisted(true)
            .build()
        jobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler?.apply {
            schedule(jobInfo)
        }
    }
}