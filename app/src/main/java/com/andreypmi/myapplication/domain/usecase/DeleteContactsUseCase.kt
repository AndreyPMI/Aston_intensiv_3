package com.andreypmi.myapplication.domain.usecase

import com.andreypmi.myapplication.domain.entity.ContactEntity
import com.andreypmi.myapplication.domain.repository.ContactRepository

class DeleteContactsUseCase(private val repository: ContactRepository) {
    suspend fun execute(contacts: List<ContactEntity>) {
        repository.deleteContacts(contacts)
    }
}