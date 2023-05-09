package com.example.myapplication.app.topmenu

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.app.topmenu.WriteMail
import com.example.myapplication.app.theme.AppTheme
import com.example.myapplication.app.topmenu.menuitem.MenuItemModel


const val TAG_APP_BAR = "AppBar"



@Composable
fun AppBar(title: String, onWriteMail: () -> Unit, onMenu: () -> Unit) {
    TopAppBar(backgroundColor = MaterialTheme.colors.background,
        title = { Text(title) },
        elevation = 0.dp,
        navigationIcon = {
            IconButton(onClick = {
                Log.v(TAG_APP_BAR, "ONCLICK")
                onMenu()
            }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Toggle drawer")
            }
        },
        actions = {
            Button(
                onClick = {
                Log.v(TAG_APP_BAR, "New Mail")
                onWriteMail()

                },
                Modifier
                    .padding(end = 8.dp),
                shape = RoundedCornerShape(30),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0078CE)))
            {
                Text(text = "Write mail ", color = Color.White)
                Spacer(Modifier
                    .width(6.dp))
                Icon(Icons.Filled.Email, null, tint = Color.White)

            }
        }
    )
}

@Preview
@Composable
fun WriteMailPreview() {
    Button(onClick = {/* OPEN WRITE MAIL VIEW */ },
        shape = RoundedCornerShape(30),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0078CE)))
    {
        Text(text = "Write mail ", color = Color.White)
        Spacer(Modifier.width(6.dp))
        Icon(Icons.Filled.Email, null, tint = Color.White)

    }
}