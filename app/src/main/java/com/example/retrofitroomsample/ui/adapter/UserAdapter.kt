package com.example.retrofitroomsample.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.retrofitroomsample.model.Item
import com.example.retrofitroomsample.ui.holder.UserHolder

class UserAdapter(private val action: (Item, View) -> Unit) : PagingDataAdapter<Item, UserHolder>(callback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder =
        UserHolder(parent)

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bindView(item, action)
    }

    companion object {
        val callback = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean =
                oldItem == newItem
        }
    }
}