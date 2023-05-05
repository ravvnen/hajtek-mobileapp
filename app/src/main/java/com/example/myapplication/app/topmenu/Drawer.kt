package com.example.myapplication.app.topmenu

import android.content.Intent
import android.icu.text.ListFormatter.Width
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SwitchAccount
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.InspectableModifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.app.InboxActivity
import com.example.myapplication.app.topmenu.menuitem.MenuItem
import com.example.myapplication.app.topmenu.menuitem.MenuItemModel
import com.example.myapplication.app.theme.AppTheme

@Composable
fun Drawer(title: String, menuItems: List<MenuItemModel>) {
    Column {
        DrawerHeader(title)
        DrawerBody(menuItems)
        DrawerUser()
    }
}

@Composable
fun DrawerHeader(title: String) {
    val image = painterResource(id = R.drawable.hajteklogov2)
    Box(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            ,

    ){
        Image(
            modifier = Modifier
                .size(width = 400.dp, height = 70.dp),
            painter = image,
            contentDescription = null
        )
    }
}

@Composable
fun DrawerBody(menuItems: List<MenuItemModel>) {
    LazyColumn(
        Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxWidth()) {
        items(menuItems) { item ->
            MenuItem(Icons.Default.Settings, menuItem = item)
        }
    }
}

@Composable
fun DrawerUser() {
    val image = Icons.Default.SwitchAccount
    Box(modifier = Modifier
        .background(MaterialTheme.colors.background)
        .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter)
    {
        Card(

            modifier = Modifier
                .padding(8.dp)
                .background(MaterialTheme.colors.background),
            elevation = 6.dp
        ) {
            Column(
                Modifier
                    .background(Color.White)
                    .fillMaxSize()
            ){
                Icon(
                imageVector = image,
                contentDescription = "Account",
                modifier = Modifier
                    //.fillMaxWidth()
                    .padding(5.dp)
                    .size(width = 60.dp, height = 90.dp)
                    //.align(Alignment.TopCenter)
            )
                Text(text = "John Test Person",
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(40.dp)
                        .fillMaxWidth(),
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )}

        }

    }
}

@Preview
@Composable
fun DrawerPreview() {
    val menuItems = listOf(
        MenuItemModel("1", "Home",  "Home") {
            Log.v("DrawerPreview", "click home")
        },
        MenuItemModel("2", "Settings", "Settings") {
            Log.v("DrawerPreview", "click settings")
        },
        MenuItemModel("3", "Settings", "Settings") {
            Log.v("DrawerPreview", "click settings")
        },
        MenuItemModel("4", "Settings", "Settings") {
            Log.v("DrawerPreview", "click settings")
        },
    )
    AppTheme(darkTheme = true) {
        Drawer(
            "My awesome menu", menuItems
        )
    }
}
