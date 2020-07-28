package com.example.retrofitroomsample.ui.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitroomsample.databinding.ItemUserBinding
import com.example.retrofitroomsample.model.Item

class UserHolder(binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

    constructor(parent: ViewGroup) : this(
        ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    private val txtName = binding.txtName
    private val txtUsername = binding.txtUsername
    private val txtEmail = binding.txtEmail
    private val txtAddress = binding.txtAddress
    private val txtPhone = binding.txtPhone

    fun bindView(item: Item, action: (Item, View) -> Unit) {
        txtName.text = item.name
        txtUsername.text = item.username
        txtEmail.text = item.email
        txtAddress.text = item.address.city
        txtPhone.text = item.phone
        itemView.setOnClickListener {
            action(item, itemView)
        }
    }
}