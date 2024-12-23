package com.andreypmi.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andreypmi.myapplication.data.ContactRepositoryImpl
import com.andreypmi.myapplication.domain.usecase.AddContactUseCase
import com.andreypmi.myapplication.domain.usecase.DeleteContactsUseCase
import com.andreypmi.myapplication.domain.usecase.GetContactsUseCase
import com.andreypmi.myapplication.domain.usecase.UpdateContactUseCase
import com.andreypmi.myapplication.domain.usecase.UpdateContactsUseCase

class ContactViewModelFactory : ViewModelProvider.Factory {
    private val repository = ContactRepositoryImpl()
    private val getContactsUseCase = GetContactsUseCase(repository)
    private val addContactUseCase = AddContactUseCase(repository)
    private val updateContactUseCase = UpdateContactUseCase(repository)
    private val updateContactsUseCase = UpdateContactsUseCase(repository)
    private val deleteContactsUseCase = DeleteContactsUseCase(repository)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContactViewModel(
                getContactsUseCase,
                addContactUseCase,
                updateContactUseCase,
                updateContactsUseCase,
                deleteContactsUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}