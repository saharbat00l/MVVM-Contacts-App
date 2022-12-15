package com.example.contactsapp_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.contactsapp_compose.ui.theme.ContactsAppComposeTheme
import com.example.contactsapp_compose.ui.theme.add_edit_contact.AddEditTodoScreen
import com.example.contactsapp_compose.ui.theme.contact_list.ContactScreen
import com.example.contactsapp_compose.utility.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactsAppComposeTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Routes.CONTACT_LIST)
                {
                    composable(Routes.CONTACT_LIST){
                        ContactScreen(
                            onNavigate = {
                                navController.navigate(it.route)
                            }) }

                    composable(Routes.ADD_EDIT_CONTACT + "?contactId={contactId}",
                    arguments = listOf(
                        navArgument(name = "contactId"){
                            type = NavType.IntType
                            defaultValue = -1
                        }
                    )){
                        AddEditTodoScreen(onPopBackStack = {
                            navController.popBackStack()
                        })
                        
                    }
                 
                 
                    
                }
            }
        }
    }
}
