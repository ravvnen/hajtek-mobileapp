package com.example.myapplication.app.topmenu.menuitem

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.HomeWork
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.myapplication.app.ViewMailActivity
import com.example.myapplication.app.theme.AppTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MenuItem(menuItem: MenuItemModel) {
    val context = LocalContext.current
    Card(
        onClick = {
            val intent = Intent(context, ViewMailActivity::class.java)
            ContextCompat.startActivity(context, intent, null)
        },
        modifier = Modifier
            .padding(8.dp)
            .height(70.dp),
        backgroundColor = Color.White,
        elevation = 6.dp
    ) {
        Row(modifier = Modifier.padding(all = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
            Icon(
                imageVector = menuItem.Icon,
                contentDescription = menuItem.title,
                modifier = Modifier
                    //.fillMaxWidth()
                    .padding(5.dp)
                    .wrapContentWidth(Alignment.Start)
                    .size(50.dp)
            )
            Spacer(modifier = Modifier.width(width = 8.dp))
            /*
            Af en eller anden grund vises følgende tekst ikke
             */
            Text(
                text = menuItem.contentDescription,
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth()
                    .padding(5.dp),
                color = Color.Black, textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun MenuItemPreview() {
    AppTheme(darkTheme = true) {
        MenuItem(MenuItemModel(Icons.Default.Home,"1", "Home","Home") {
        })
    }
}

