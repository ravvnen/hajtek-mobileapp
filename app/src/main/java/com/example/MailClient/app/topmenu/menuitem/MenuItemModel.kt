package com.example.MailClient.app.topmenu.menuitem

import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItemModel(
    val Icon: ImageVector,
    val id: String,
    val title: String,
    val contentDescription: String,
    val onClick: () -> Unit

)

