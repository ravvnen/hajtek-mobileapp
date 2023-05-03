package com.example.myapplication.app.topmenu

import android.util.Log
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.app.theme.AppTheme


const val TAG_APP_BAR = "AppBar"

@Composable
fun AppBar(title: String, onMenu: () -> Unit) {
    TopAppBar(backgroundColor = MaterialTheme.colors.background,
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = {
                Log.v(TAG_APP_BAR, "ONCLICK")
                onMenu()
            }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Toggle drawer")
            }
        }
    )
}

