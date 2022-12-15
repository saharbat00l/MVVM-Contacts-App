package com.example.contactsapp_compose.ui.theme.add_edit_contact

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactsapp_compose.data.Contact
import com.example.contactsapp_compose.data.ContactRepository
import com.example.contactsapp_compose.utility.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditContactViewModel @Inject constructor(
    private val repository: ContactRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var contact by mutableStateOf<Contact?>(null)
        private set
    var title by mutableStateOf("")
        private set
    var description by mutableStateOf("")
        private set

    private val _uiEvent = Channel<UiEvent> { }
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val contactId = savedStateHandle.get<Int>("contactId")
        if (contactId != -1) {
            viewModelScope.launch {
                if (contactId != null) {
                    repository.getContactById(contactId)?.let { contact ->
                        title = contact.title
                        description = contact.description ?: ""
                        this@AddEditContactViewModel.contact = contact
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditContactEvent) {
        when(event) {
            is AddEditContactEvent.OnTitleChange -> {
                title = event.title
            }
            is AddEditContactEvent.OnDescriptionChange -> {
                description = event.description
            }
            is AddEditContactEvent.OnSaveTodoClick -> {
                viewModelScope.launch {
                    if(title.isBlank()) {
                        sendUiEvent(UiEvent.ShowSnackbar(
                            message = "The title can't be empty"
                        ))
                        return@launch
                    }
                    repository.insertContact(
                        Contact(
                            title = title,
                            description = description,
                            isDone = contact?.isDone ?: false,
                            id = contact?.id
                        )
                    )
                    sendUiEvent(UiEvent.PopBackStack)
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}
