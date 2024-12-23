package com.andreypmi.myapplication.domain

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.andreypmi.myapplication.databinding.ItemContactBinding
import com.andreypmi.myapplication.domain.entity.ContactEntity


class ContactAdapter (
    private val onItemClick: (ContactEntity) -> Unit,
    private val isContactSelected: (ContactEntity) -> Boolean
) : ListAdapter<ContactEntity, ContactAdapter.ContactViewHolder>(ContactDiffCallback()) {

    var isSelectionMode = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = getItem(position)
        holder.bind(contact, isSelectionMode)
    }

    inner class ContactViewHolder(private val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: ContactEntity, isSelectionMode: Boolean) {
            binding.firstNameTextView.text = contact.firstName
            binding.lastNameTextView.text = contact.lastName
            binding.numberTextView.text = contact.phoneNumber
            binding.checkbox.isChecked = isContactSelected(contact)
            binding.checkbox.visibility = if(isSelectionMode) View.VISIBLE else View.GONE

            itemView.setOnClickListener {
                onItemClick(contact)
            }
        }
    }
}

class ContactDiffCallback : DiffUtil.ItemCallback<ContactEntity>() {
    override fun areItemsTheSame(oldItem: ContactEntity, newItem: ContactEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ContactEntity, newItem: ContactEntity): Boolean {
        return oldItem == newItem
    }
}