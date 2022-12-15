package com.example.contactsapp_compose.ui.theme.add_edit_contact

sealed class AddEditContactEvent{
    data class OnTitleChange(val title: String): AddEditContactEvent()
    data class OnDescriptionChange(val description: String): AddEditContactEvent()
    object OnSaveTodoClick: AddEditContactEvent()
}
