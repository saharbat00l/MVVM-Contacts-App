package com.example.contactsapp_compose.data

import kotlinx.coroutines.flow.Flow

class ContactRepoImplementation(
    private val dao: ContactDao
): ContactRepository {
    override suspend fun insertContact(todo: Contact) {
        dao.insertContact(todo)
    }

    override suspend fun deleteContact(todo: Contact) {
        dao.deleteContact(todo)
    }

    override suspend fun getContactById(id: Int): Contact? {
       return dao.getContactById(id)
    }

    override fun getContacts(): Flow<List<Contact>> {
        return dao.getContacts()
    }

}