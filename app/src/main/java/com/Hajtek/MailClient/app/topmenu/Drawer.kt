package com.Hajtek.MailClient.app.topmenu

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.Hajtek.MailClient.R
import com.Hajtek.MailClient.app.MainActivity
import com.Hajtek.MailClient.app.activeUser
import com.Hajtek.MailClient.app.theme.AppTheme
import com.Hajtek.MailClient.app.topmenu.menuitem.MenuItem
import com.Hajtek.MailClient.app.topmenu.menuitem.MenuItemModel


/*
ASBJØRN^2s UI MAIN
 */


@Composable
fun Drawer(menuItems: List<MenuItemModel>) {
    Column(modifier = Modifier
        .background(MaterialTheme.colors.background)
        .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween) {
        Column() {
            DrawerHeader()
            DrawerBody(menuItems)
        }
        DrawerUser()
    }
}

@Composable
fun DrawerHeader() {
    val image = painterResource(id = R.drawable.hajteklogov2)
    Box(
        modifier = Modifier
            .background(MaterialTheme.colors.background)

    ){
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
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
            .fillMaxWidth()
            .padding(horizontal = 10.dp)){
        items(menuItems) { item ->
            MenuItem(menuItem = item) //tager her et icon? skal den det
        }
    }
}

@Composable
fun DrawerUser() {
    val context = LocalContext.current
    Column(verticalArrangement = Arrangement.Bottom) {

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 18.dp, vertical = 18.dp)
        .clickable {
            val intent = Intent(context, AccountActivity::class.java)
            ContextCompat.startActivity(context, intent, null)
        },
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.White,
        elevation = 4.dp)

        {
            Column(modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.ManageAccounts,
                        contentDescription = "Account",
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        tint = Color.Gray)


                Text(text = "${activeUser.firstName} ${activeUser.lastName}",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                Text(text = "${activeUser.emailAddress}",
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        }
        Spacer(modifier = Modifier.size(0.dp))
        Row(horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = (-10).dp)) {
            Button(
                onClick = { val intent = Intent(context, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    ContextCompat.startActivity(context, intent, null) },
                modifier = Modifier.width(180.dp),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 4.dp
                ),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0078CE))
            ) {
                Text("Logout", color = Color.White)
            }
        }

    }
}





@Preview
@Composable
fun DrawerPreview() {
    val menuItems = listOf(
        MenuItemModel(Icons.Filled.Email,"1", "Home",  "Inbox") {
            Log.v("DrawerPreview", "click home")
        },
        MenuItemModel(Icons.Filled.Send,"2", "Settings", "Sent") {
            Log.v("DrawerPreview", "click settings")
        },
        MenuItemModel(Icons.Default.Warning,"3", "Settings", "Spam") {
            Log.v("DrawerPreview", "click settings")
        },
        MenuItemModel(Icons.Filled.Delete,"4", "Settings", "Trash") {
            Log.v("DrawerPreview", "click settings")
        },
    )
    AppTheme(darkTheme = true) {
        Drawer(
            menuItems
        )
    }
}
