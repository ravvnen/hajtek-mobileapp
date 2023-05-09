package com.example.myapplication.app.topmenu.menuitem

import androidx.activity.ComponentActivity
import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItemModel(
    val Icon: ImageVector,
    val id: String,
    val title: String,
    val contentDescription: String,
    val onClick: () -> Unit,
)

