package com.example.meddybuddyassigment.chat.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.meddybuddyassigment.R
import com.example.meddybuddyassigment.chat.local.UserEntity
import com.example.meddybuddyassigment.chat.model.ChatMessage
import com.example.meddybuddyassigment.databinding.ChatItemDataBinding
import com.example.meddybuddyassigment.databinding.UserItemDataBinding


class SetUserAdapter(
    private var list: MutableList<UserEntity> = mutableListOf(),
    private var onItemClicked: (UserEntity, Int) -> Unit,
) :
    RecyclerView.Adapter<SetUserAdapter.ViewHolder>() {

    // ViewHolder class
    inner class ViewHolder(
        val ItemDataBinding: UserItemDataBinding,
        val onItemClicked: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(ItemDataBinding.root) {
        init {
            ItemDataBinding.root.setOnClickListener {
                onItemClicked(bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: UserItemDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.view_item_user, parent, false
        )
        return ViewHolder(binding) {
            onItemClicked(list[it], it)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.ItemDataBinding.user = data
        holder.ItemDataBinding.executePendingBindings()
    }

    fun updateAll(data: List<UserEntity>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }
}