package com.example.contactsapp_compose.ui.theme.contact_list

import com.example.contactsapp_compose.data.Contact

sealed class ContactListEvent {
    data class OnDeleteContact(val contact: Contact): ContactListEvent()
    data class OnDoneChange(val contact: Contact, val isDone: Boolean): ContactListEvent()
    object OnUndoDeleteClick: ContactListEvent()
    data class OnContactClick(val contact: Contact): ContactListEvent()
    object OnAddContactClick: ContactListEvent()
}
