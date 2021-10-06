package com.example.meddybuddyassigment.chat.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.meddybuddyassigment.R
import com.example.meddybuddyassigment.chat.model.ChatMessage
import com.example.meddybuddyassigment.databinding.ChatItemDataBinding


class SetChatAdapter(
    private var list: MutableList<ChatMessage> = mutableListOf()
) :
    RecyclerView.Adapter<SetChatAdapter.ViewHolder>() {

    // ViewHolder class
    class ViewHolder(val ItemDataBinding: ChatItemDataBinding) :
        RecyclerView.ViewHolder(ItemDataBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ChatItemDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.view_item_chat, parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.ItemDataBinding.chatmessage = data
        holder.ItemDataBinding.executePendingBindings()
    }

    fun updateAll(data: List<ChatMessage>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    fun update(message: ChatMessage) {
        list.add(message)
        notifyDataSetChanged()
    }
}