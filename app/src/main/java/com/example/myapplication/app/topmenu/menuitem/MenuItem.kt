package com.example.myapplication.app.topmenu.menuitem

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.HomeWork
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.app.theme.AppTheme

@Composable
fun MenuItem(menuItem: MenuItemModel) {
    Row(
        Modifier
            .background(color = Color.White)
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { menuItem.onClick() }) {
        Text(
            text = menuItem.title,
            Modifier.weight(1f),
            style = MaterialTheme.typography.h5, color = Color.Black, fontWeight = FontWeight.Bold

        )
        Icon(
            imageVector = menuItem.iconVector,
            contentDescription = menuItem.contentDescription,
            tint = MaterialTheme.colors.onPrimary
        )
    }
}

@Preview
@Composable
fun MenuItemPreview() {
    AppTheme(darkTheme = true) {
        MenuItem(MenuItemModel("1", "Home", Icons.Default.Home, "Home") {
            println("click")
        })
    }
}

