package com.example.myapplication.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MoveToInbox
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import com.example.myapplication.backend.Email
import com.example.myapplication.backend.EmailUtil
import kotlinx.coroutines.*
import java.time.LocalDateTime

data class EmailOption(
    val text: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

object MailUtilComposables{

    @Composable
    fun InboxView() {

        var emails by remember { mutableStateOf(listOf<Email>()) }

        val context = LocalContext.current

        LaunchedEffect(Unit){
            try {
                val mails = EmailUtil.fetchInbox("smtp.gmail.com", EmailUtil.mailFrom, EmailUtil.password)
                emails = mails
            }catch(e : Throwable){
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
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
    fun SentEmailsScreen(onEmailDeleted: (Email) -> Unit, onEmailMovedToInbox: (Email) -> Unit) {
        val context = LocalContext.current

        var sentMails by remember { mutableStateOf(listOf<Email>()) }

        LaunchedEffect(Unit){
            try {
                val mails = EmailUtil.fetchSent("smtp.gmail.com", EmailUtil.mailFrom, EmailUtil.password)
                sentMails = mails
            }catch (ex : Exception){
                Toast.makeText(context, "Sent mail folder is empty", Toast.LENGTH_SHORT).show()
            }
        }

        val expandedMenuItemId = remember { mutableStateOf(-1) }
        Column {
            Text(
                text = "Sent Emails",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp)
            )
            LazyColumn {
                items(sentMails) { email ->
                    val menuExpanded = expandedMenuItemId.value == email.hashCode()
                    val options = listOf(
                        EmailOption("Delete", Icons.Default.Delete) {
                            onEmailDeleted(email)
                        },
                        EmailOption("Move to Inbox", Icons.Default.MoveToInbox) {
                            onEmailMovedToInbox(email)
                        }
                    )
                    EmailItem(
                        email = email,
                        onItemHold = {
                            if (menuExpanded) {
                                expandedMenuItemId.value = -1
                            } else {
                                expandedMenuItemId.value = email.hashCode()
                            }
                        },
                        showOptionsMenu = menuExpanded,
                        options = options
                    )
                }
            }
        }
    }

    @Composable
    fun TrashView() {

        var emails by remember { mutableStateOf(listOf<Email>()) }

        var context = LocalContext.current

        LaunchedEffect(Unit){
            try {
                val mails = EmailUtil.fetchTrashEmails("smtp.gmail.com", "587", EmailUtil.mailFrom, EmailUtil.password)
                emails = mails
            }catch (e : Throwable){
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
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
                        id = "",
                        from = EmailUtil.mailFrom,
                        to = listOf(to),
                        subject = subject,
                        body = body,
                        sentDate = LocalDateTime.now().toString(),
                        receivedDate = "",
                        isRead = false
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

    @Composable
    fun SpamEmailsScreen(onEmailDeleted: (Email) -> Unit, onEmailMovedToInbox: (Email) -> Unit) {

        var spamMails by remember { mutableStateOf(listOf<Email>()) }

        LaunchedEffect(Unit){
            val mails = EmailUtil.fetchSpamEmails("smtp.gmail.com", "587", EmailUtil.mailFrom, EmailUtil.password)
            spamMails = mails
        }

        val expandedMenuItemId = remember { mutableStateOf(-1) }
        Column {
            Text(
                text = "Spam Emails",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp)
            )
            LazyColumn {
                items(spamMails) { email ->
                    val menuExpanded = expandedMenuItemId.value == email.hashCode()
                    val options = listOf(
                        EmailOption("Delete", Icons.Default.Delete) {
                            onEmailDeleted(email)
                        },
                        EmailOption("Move to Inbox", Icons.Default.MoveToInbox) {
                            onEmailMovedToInbox(email)
                        }
                    )
                    EmailItem(
                        email = email,
                        onItemHold = {
                            if (menuExpanded) {
                                expandedMenuItemId.value = -1
                            } else {
                                expandedMenuItemId.value = email.hashCode()
                            }
                        },
                        showOptionsMenu = menuExpanded,
                        options = options
                    )
                }
            }
        }
    }

    @Composable
    fun EmailItem(email: Email, onItemHold: () -> Unit, showOptionsMenu: Boolean = false, options: List<EmailOption> = emptyList()) {
        val backgroundColor = if (email.isRead) Color.White else Color.LightGray
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .background(backgroundColor),
            elevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = email.isRead,
                    onCheckedChange = { },
                    modifier = Modifier.padding(start = 8.dp)
                )
                Text(
                    text = email.from,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                )
                Text(
                    text = email.sentDate,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clickable(onClick = onItemHold)
                )
            }
            DropdownMenu(
                expanded = showOptionsMenu,
                onDismissRequest = { onItemHold() }
            ) {
                options.forEachIndexed { _, option ->
                    DropdownMenuItem(
                        onClick = {
                            option.onClick()
                            onItemHold()
                        }
                    ) {
                        Icon(
                            imageVector = option.icon,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = option.text,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

