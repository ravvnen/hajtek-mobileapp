package com.example.myapplication.app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.app.theme.AppTheme
import com.example.myapplication.app.topmenu.AppBar
import com.example.myapplication.app.topmenu.Drawer
import com.example.myapplication.app.topmenu.*
import com.example.myapplication.app.topmenu.menuitem.MenuItemModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val menuItems = listOf(
        MenuItemModel("1", "Inbox", Icons.Rounded.WbSunny, "Inbox") {
            val intent = Intent(this@MainActivity, InboxActivity::class.java)
            startActivity(intent)
        },
        MenuItemModel("2", "Sent", Icons.Rounded.WbSunny, "Sent") {
            val intent = Intent(this@MainActivity, InboxActivity::class.java)
            startActivity(intent)
        },
        MenuItemModel("3", "Spam", Icons.Rounded.WbSunny, "Spam") {
            val intent = Intent(this@MainActivity, InboxActivity::class.java)
            startActivity(intent)
        },
        MenuItemModel("4", "Trash", Icons.Rounded.WbSunny, "Trash") {
            val intent = Intent(this@MainActivity, InboxActivity::class.java)
            startActivity(intent)
        },
    )

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme(darkTheme = true) {
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                Scaffold(scaffoldState = scaffoldState, topBar = {
                    AppBar(title = "Hajtek Mail Client") {
                        Log.v(this::class.simpleName, "before launch Click")
                        scope.launch {
                            Log.v(this::class.simpleName, "in launch launch Click")
                            scaffoldState.drawerState.open()
                        }
                    }
                }, content = {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(all = 12.dp)
                            .background(Color.White)
                    ) {
                        val image = painterResource(id = R.drawable.hajteklogo)
                        // Create a box to overlap image and texts
                        Box {
                            Image(
                                modifier = Modifier
                                    .size(size = 600.dp),
                                painter = image,
                                contentDescription = null
                            )
                        }
                        Text(text = "and senior consultant Ravn", color = Color.Black, fontSize = 25.sp)
                    }
                },
                    drawerContent = {
                    Drawer(
                        "HAJTEK", menuItems
                    )
                })
            }
        }
    }
}