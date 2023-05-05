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
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import com.example.myapplication.backend.Email
import com.example.myapplication.backend.EmailUtil
import kotlinx.coroutines.*
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

    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    fun InboxList() {

        var emails by remember { mutableStateOf(listOf<Email>()) }

        LaunchedEffect(Unit){
            val mails = EmailUtil.fetchEmails("smtp.gmail.com", EmailUtil.mailFrom, EmailUtil.password)
            emails = mails
        }

        LazyColumn {
            items(emails) { email ->
                Button(
                    onClick = { /* Navigate to full email */ },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(16.dp),
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 8.dp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Column {
                        Text(
                            text = "From: ${email.from}",
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Subject: ${email.subject}",
                            fontWeight = FontWeight.Normal
                        )
                        Text(
                            text = "Received: ${email.receivedDate}",
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
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
                        from = EmailUtil.mailFrom,
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
                                username = EmailUtil.mailFrom,
                                password = EmailUtil.password,
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



