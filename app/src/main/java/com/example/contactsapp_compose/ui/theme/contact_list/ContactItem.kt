package com.example.contactsapp_compose.ui.theme.contact_list

import android.media.Image
import android.widget.RadioGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.contactsapp_compose.data.Contact
import com.example.contactsapp_compose.R


@Composable
fun ContactItem(
    contact: Contact,
    onEvent: (ContactListEvent) -> Unit,
    modifier: Modifier = Modifier
){
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f),
        verticalArrangement = Arrangement.Center) {
            Row(verticalAlignment = Alignment.CenterVertically) {

                Image(
                    painterResource(R.drawable.dp),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .padding(end = 0.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))

                Text(text = contact.title,
                    modifier = Modifier,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold)

                Spacer(modifier = Modifier.width(8.dp))

                contact.description?.let { 
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = it)
                }
                
            }
        }
        IconButton(onClick = {
            onEvent(ContactListEvent.OnDeleteContact(contact))
        }){
            Icon(imageVector = Icons.Default.Delete,
                contentDescription = "Delete" )
        }
//        Checkbox(checked = contact.isDone, onCheckedChange = { isChecked->
//            onEvent(ContactListEvent.OnDoneChange(contact,isChecked))
//        })
    }
}

