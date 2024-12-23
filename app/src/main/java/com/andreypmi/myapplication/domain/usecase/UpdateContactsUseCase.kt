package com.andreypmi.myapplication.domain.usecase

import com.andreypmi.myapplication.domain.entity.ContactEntity
import com.andreypmi.myapplication.domain.repository.ContactRepository

class UpdateContactsUseCase(private val repository: ContactRepository) {
    suspend fun execute(contact : List<ContactEntity>) {
        repository.updateContacts(contact)
    }
}