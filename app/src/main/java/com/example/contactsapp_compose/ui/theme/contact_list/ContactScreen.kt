package com.example.contactsapp_compose.ui.theme.contact_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.contactsapp_compose.utility.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun ContactScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: ContactListViewModel = hiltViewModel()
){

    val contacts = viewModel.contacts.collectAsState(initial = emptyList())
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ event->
            when (event){
                is UiEvent.ShowSnackbar->{
                  val result=  scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                    if (result==SnackbarResult.ActionPerformed){
                        viewModel.onEvent(ContactListEvent.OnUndoDeleteClick)
                    }
                }
                is UiEvent.Navigate-> onNavigate(event)
                else -> Unit
            }
        }
    }
    
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(ContactListEvent.OnAddContactClick)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) {
        Text(text = "Contacts",
            fontSize = 30.sp,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(20.dp, 10.dp)
        )
        LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 50.dp)){
            items(contacts.value){ contact ->
                ContactItem(contact = contact,
                    onEvent = viewModel::onEvent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.onEvent(ContactListEvent.OnContactClick(contact))
                        }
                        .padding(10.dp,14.dp)
                )
            }
        }

    }
}