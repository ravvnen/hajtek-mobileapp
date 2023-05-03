package com.example.myapplication.app.topmenu

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.app.topmenu.menuitem.MenuItem
import com.example.myapplication.app.topmenu.menuitem.MenuItemModel
import com.example.myapplication.app.theme.AppTheme

@Composable
fun Drawer(title: String, menuItems: List<MenuItemModel>) {
    Column {
        DrawerHeader(title)
        DrawerBody(menuItems)
    }
}

@Composable
fun DrawerHeader(title: String) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxWidth()
            .padding(44.dp), contentAlignment = Alignment.Center

    ) {
        Text(title, color = MaterialTheme.colors.onBackground, style = MaterialTheme.typography.h4)
    }
}

@Composable
fun DrawerBody(menuItems: List<MenuItemModel>) {
    LazyColumn(Modifier.background(MaterialTheme.colors.background)) {
        items(menuItems) { item ->
            MenuItem(menuItem = item)
        }
    }
}

@Preview
@Composable
fun DrawerPreview() {
    val menuItems = listOf(
        MenuItemModel("1", "Home", Icons.Default.Home, "Home") {
            Log.v("DrawerPreview", "click home")
        },
        MenuItemModel("2", "Settings", Icons.Default.Settings, "Settings") {
            Log.v("DrawerPreview", "click settings")
        },
    )
    AppTheme(darkTheme = true) {
        Drawer(
            "My awesome menu", menuItems
        )
    }
}
