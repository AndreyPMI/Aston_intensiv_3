package com.andreypmi.myapplication.domain.repository

import com.andreypmi.myapplication.domain.entity.ContactEntity

interface ContactRepository {
    suspend fun getContacts(): List<ContactEntity>
    suspend fun addContact(contact: ContactEntity)
    suspend fun updateContact(contact: ContactEntity)
    suspend fun updateContacts(contacts: List<ContactEntity>)
    suspend fun deleteContacts(contacts: List<ContactEntity>)
}