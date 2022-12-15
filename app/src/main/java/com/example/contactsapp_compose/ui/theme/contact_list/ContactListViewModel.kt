package com.example.contactsapp_compose.ui.theme.contact_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactsapp_compose.data.Contact
import com.example.contactsapp_compose.data.ContactRepository
import com.example.contactsapp_compose.utility.Routes
import com.example.contactsapp_compose.utility.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ContactListViewModel @Inject constructor(
    private val repository: ContactRepository)
: ViewModel() {

    val contacts = repository.getContacts()

    private val _uiEvent = Channel<UiEvent> {  }
    val uiEvent = _uiEvent.receiveAsFlow()

    private var deletedContact: Contact? = null


    fun onEvent(event: ContactListEvent){
        when (event){
            is ContactListEvent.OnContactClick ->{
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_CONTACT + "?contactId=${event.contact.id}"))
            }
            is ContactListEvent.OnAddContactClick ->{
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_CONTACT))
            }
            is ContactListEvent.OnUndoDeleteClick ->{
                deletedContact?.let { contact ->
                    viewModelScope.launch {
                        repository.insertContact(contact)
                    }
                }
            }
            is ContactListEvent.OnDeleteContact ->{
                viewModelScope.launch {
                    deletedContact = event.contact
                    repository.deleteContact(event.contact)
                    sendUiEvent(UiEvent.ShowSnackbar(
                        message = "Contact deleted!",
                        action = "Undo"
                    ))
                }
            }
            is ContactListEvent.OnDoneChange ->{
                viewModelScope.launch {
                    repository.insertContact(
                        event.contact.copy(
                            isDone = event.isDone
                        )
                    )
                }
            }
        }

    }

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch{
            _uiEvent.send(event)
        }
    }
}