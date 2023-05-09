package com.Hajtek.MailClient.app.topmenu

import android.content.Intent
import android.media.Image
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.Hajtek.MailClient.R
import com.Hajtek.MailClient.app.theme.AppTheme
import com.Hajtek.MailClient.app.topmenu.menuitem.MenuItem
import com.Hajtek.MailClient.app.topmenu.menuitem.MenuItemModel


/*
ASBJØRN^2s UI MAIN
 */


@Composable
fun Drawer(menuItems: List<MenuItemModel>) {
    Column {
        DrawerHeader()
        DrawerBody(menuItems)
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
            MenuItem(menuItem = item) //tager her et icon? skal den det
        }
    }
}

@Composable
fun DrawerUser() {
    val context = LocalContext.current
    Box(modifier = Modifier
        .background(MaterialTheme.colors.background)
        .fillMaxSize()

    )
    {
        Box(modifier = Modifier.align(Alignment.BottomCenter))
        {
            Column() {
                IconButton(onClick = {
                    val intent = Intent(context, AccountActivity::class.java)
                    ContextCompat.startActivity(context, intent, null)
                },
                    modifier = Modifier
                        .clip(CircleShape)
                        .align(CenterHorizontally)
                ) {
                    Icon(Icons.Default.ManageAccounts,
                        contentDescription = "Account",
                        modifier = Modifier.size(64.dp),
                        tint = Color.Gray)
                }

                Text(text = "John Test Person",
                    fontSize = 30.sp,
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

/*
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
                )

 */



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
