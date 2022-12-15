package com.example.contactsapp_compose.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(
    val title: String,
    val description: String?,
    val isDone: Boolean,
 @PrimaryKey val id:Int? = null
)
