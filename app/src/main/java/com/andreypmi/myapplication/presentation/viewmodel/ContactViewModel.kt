package com.andreypmi.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreypmi.myapplication.domain.entity.ContactEntity
import com.andreypmi.myapplication.domain.usecase.AddContactUseCase
import com.andreypmi.myapplication.domain.usecase.DeleteContactsUseCase
import com.andreypmi.myapplication.domain.usecase.GetContactsUseCase
import com.andreypmi.myapplication.domain.usecase.UpdateContactUseCase
import com.andreypmi.myapplication.domain.usecase.UpdateContactsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ContactUiState(
    val contacts: List<ContactEntity> = emptyList(),
    val selectedContacts: List<ContactEntity> = emptyList(),
    val isSelectionMode: Boolean = false
)

class ContactViewModel(
    private val getContactsUseCase: GetContactsUseCase,
    private val addContactUseCase: AddContactUseCase,
    private val updateContactUseCase: UpdateContactUseCase,
    private val updatesContactUseCase: UpdateContactsUseCase,
    private val deleteContactsUseCase: DeleteContactsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ContactUiState())
    val uiState: StateFlow<ContactUiState> = _uiState

    init {
        loadContacts()
    }

    private fun loadContacts() {
        viewModelScope.launch {
            val contacts = getContactsUseCase.execute()
            _uiState.value = _uiState.value.copy(contacts = contacts)
        }
    }

    fun addContact(contact: ContactEntity) {
        viewModelScope.launch {
            addContactUseCase.execute(contact)
            loadContacts()
        }
    }

    fun updateContact(contact: ContactEntity) {
        viewModelScope.launch {
            updateContactUseCase.execute(contact)
            loadContacts()
        }
    }

    fun deleteSelectedContacts() {
        viewModelScope.launch {
            deleteContactsUseCase.execute(_uiState.value.selectedContacts)
            exitSelectionMode()
            loadContacts()
        }
    }

    fun toggleSelectionMode() {
        val newSelectionMode = !_uiState.value.isSelectionMode
        _uiState.value = _uiState.value.copy(isSelectionMode = newSelectionMode, selectedContacts = emptyList())
    }

    fun exitSelectionMode() {
        _uiState.value = _uiState.value.copy(isSelectionMode = false, selectedContacts = emptyList())
    }

    fun selectContact(contact: ContactEntity) {
        val list = _uiState.value.selectedContacts.toMutableList()
        if (list.contains(contact)) {
            list.remove(contact)
        } else {
            list.add(contact)
        }
        _uiState.value = _uiState.value.copy(selectedContacts = list)
    }

    fun isContactSelected(contact: ContactEntity): Boolean {
        return _uiState.value.selectedContacts.contains(contact)
    }

    fun moveContact(fromPosition: Int, toPosition: Int) {
        viewModelScope.launch {
            val list = _uiState.value.contacts.toMutableList()
            val from = list[fromPosition]
            list.removeAt(fromPosition)
            list.add(toPosition, from)
            _uiState.value = _uiState.value.copy(contacts = list)
            updatesContactUseCase.execute(contact = list)
        }
    }
}