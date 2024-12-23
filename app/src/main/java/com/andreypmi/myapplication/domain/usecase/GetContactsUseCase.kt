package com.andreypmi.myapplication.domain.usecase

import com.andreypmi.myapplication.domain.entity.ContactEntity
import com.andreypmi.myapplication.domain.repository.ContactRepository

class GetContactsUseCase (private val repository: ContactRepository) {
    suspend fun execute(): List<ContactEntity> {
        return repository.getContacts()
    }
}