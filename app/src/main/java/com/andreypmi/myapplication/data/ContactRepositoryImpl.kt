package com.andreypmi.myapplication.data

import com.andreypmi.myapplication.domain.entity.ContactEntity
import com.andreypmi.myapplication.domain.repository.ContactRepository

class ContactRepositoryImpl : ContactRepository {
    private val contacts = mutableListOf<ContactEntity>()
    init {
        repeat(100) {
            contacts.add(
                ContactEntity(
                    id = it + 1,
                    firstName = "Имя$it",
                    lastName = "Фамилия$it",
                    phoneNumber = "+799999999$it"
                )
            )
        }
    }

    override suspend fun getContacts(): List<ContactEntity> {
        return contacts.toList()
    }

    override suspend fun addContact(contact: ContactEntity) {
        contacts.add(contact)
    }

    override suspend fun updateContact(contact: ContactEntity) {
        val index = contacts.indexOfFirst { it.id == contact.id }
        if (index != -1) {
            contacts[index] = contact
        }
    }

    override suspend fun deleteContacts(contactsToDelete: List<ContactEntity>) {
        contacts.removeAll(contactsToDelete)
    }
}