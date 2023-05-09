package com.Hajtek.MailClient.app

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
import androidx.compose.material.icons.filled.*
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.Hajtek.MailClient.R
import com.Hajtek.MailClient.app.theme.AppTheme
import com.Hajtek.MailClient.app.topmenu.AppBar
import com.Hajtek.MailClient.app.topmenu.Drawer
import com.Hajtek.MailClient.app.topmenu.*
import com.Hajtek.MailClient.app.topmenu.menuitem.MenuItemModel
import kotlinx.coroutines.launch

class UIActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            AppTheme(darkTheme = true) {
                val context = LocalContext.current

                val menuItems = listOf(
                    MenuItemModel(Icons.Filled.Email,"1", "Inbox", "Inbox", onClick = {
                        val intent = Intent(context, com.Hajtek.MailClient.app.InboxActivity::class.java)
                        ContextCompat.startActivity(context, intent, null)
                    }),
                    MenuItemModel(Icons.Filled.Send,"2", "Sent",  "Sent", onClick = {
                        val intent = Intent(context, SentActivity::class.java)
                        ContextCompat.startActivity(context, intent, null)
                    }),
                    MenuItemModel(Icons.Default.Warning,"3", "Spam",  "Spam", onClick = {
                        val intent = Intent(context, SpamActivity::class.java)
                        ContextCompat.startActivity(context, intent, null)
                    }),
                    MenuItemModel(Icons.Default.Delete,"4", "Trash",  "Trash", onClick = {
                        val intent = Intent(context, TrashActivity::class.java)
                        ContextCompat.startActivity(context, intent, null)
                    })
                )


                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                Scaffold(scaffoldState = scaffoldState, topBar = {
                    AppBar(title = "Hajtek Mail Client",
                        onWriteMail = {
                            val intent = Intent(this@UIActivity, WriteMail::class.java)
                            startActivity(intent)
                        }) {
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
                    }
                },
                    drawerContent = {
                    Drawer(
                         menuItems
                    )
                })
            }
        }
    }
}