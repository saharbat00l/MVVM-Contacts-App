package com.example.contactsapp_compose.data


import kotlinx.coroutines.flow.Flow

interface ContactRepository {

    suspend fun insertContact(todo: Contact)

    suspend fun deleteContact(todo: Contact)

    suspend fun getContactById(id: Int): Contact?

    fun getContacts(): Flow<List<Contact>>



}