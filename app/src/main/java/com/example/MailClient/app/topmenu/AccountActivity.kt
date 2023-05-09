package com.example.MailClient.app.topmenu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class AccountActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.size(100.dp))
                    Icon(
                        Icons.Default.ManageAccounts,
                        contentDescription = "Account",
                        modifier = Modifier
                            .padding(15.dp)
                            .wrapContentWidth(Alignment.CenterHorizontally)
                            .size(192.dp),
                        tint = Color.Black)
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Email:",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 0.dp, end = 0.dp, top = 20.dp, bottom = 5.dp),
                        color = Color.Black, textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Password:",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        color = Color.Black, textAlign = TextAlign.Center
                    )
                    /*
                    Text(
                        text = "",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        color = Color.Black, textAlign = TextAlign.Center
                    )
                     */
                    Spacer(modifier = Modifier.size(200.dp))
                    Button(
                        onClick = { finish() },
                        modifier = Modifier.width(180.dp),
                        elevation = ButtonDefaults.elevation(
                            defaultElevation = 10.dp
                        ),
                        shape = RoundedCornerShape(30),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0078CE))
                    ) {
                        Text("Return", color = Color.White)
                    }
                }
                
                
            }
        }
    }
}
