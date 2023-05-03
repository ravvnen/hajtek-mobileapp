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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.WbSunny
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
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.backend.Email
import com.example.myapplication.backend.EmailUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.util.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(color = MaterialTheme.colors.background) {
                SendEmailView()
            }
        }
    }


    @Composable
    fun SendEmailView() {
        var to by remember { mutableStateOf("") }
        var subject by remember { mutableStateOf("") }
        var body by remember { mutableStateOf("") }

        val context = LocalContext.current

        Column(Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = to,
                onValueChange = { to = it },
                label = { Text("To") }
            )
            OutlinedTextField(
                value = subject,
                onValueChange = { subject = it },
                label = { Text("Subject") }
            )
            OutlinedTextField(
                value = body,
                onValueChange = { body = it },
                label = { Text("Body") },
                maxLines = 10
            )
            Button(
                onClick = {
                    val email = Email(
                        from = "testemailappuser@gmail.com",
                        to = listOf(to),
                        subject = subject,
                        body = body,
                        sentDate = LocalDateTime.now().toString(),
                        receivedDate = ""
                    )

                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            EmailUtil.sendEmail(
                                host = "smtp.gmail.com",
                                port = "587",
                                username = "testemailappuser@gmail.com",
                                password = "TesT1234",
                                mail = email
                            )

                            // Switch back to the main thread to show the toast
                            withContext(Dispatchers.Main) {
                                Toast.makeText(context, "Email sent", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {
                            // Handle any exceptions
                            withContext(Dispatchers.Main) {
                                Toast.makeText(context, "Failed to send email: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Send")
            }
        }
    }
}



