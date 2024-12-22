package com.andreypmi.myapplication.domain.usecase

import com.andreypmi.myapplication.domain.entity.ContactEntity
import com.andreypmi.myapplication.domain.repository.ContactRepository

class UpdateContactUseCase(private val repository: ContactRepository) {
    suspend fun execute(contact: ContactEntity) {
        repository.updateContact(contact)
    }
}