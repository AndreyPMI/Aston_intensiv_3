package com.andreypmi.myapplication.presentation

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.andreypmi.myapplication.databinding.DialogContactBinding
import com.andreypmi.myapplication.domain.entity.ContactEntity

class ContactDialogFragment(
    private val contact: ContactEntity? = null,
    private val onSave: (ContactEntity) -> Unit
) : DialogFragment() {
    private lateinit var binding: DialogContactBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogContactBinding.inflate(layoutInflater)

        contact?.let {
            binding.editName.setText(it.firstName)
            binding.editSurname.setText(it.lastName)
            binding.editPhone.setText(it.phoneNumber)
        }

        return AlertDialog.Builder(requireContext())
            .setTitle(if(contact == null) "Add Contact" else "Edit Contact")
            .setView(binding.root)
            .setPositiveButton("Save") { _, _ ->
                val firstName = binding.editName.text.toString()
                val lastName = binding.editSurname.text.toString()
                val number = binding.editPhone.text.toString()
                val newContact = ContactEntity(
                    id = contact?.id ?: 0,
                    firstName = firstName,
                    lastName = lastName,
                    phoneNumber = number
                )
                onSave(newContact)
                dismiss()
            }
            .setNegativeButton("Cancel") {_, _ ->
                dismiss()
            }
            .create()
    }
}