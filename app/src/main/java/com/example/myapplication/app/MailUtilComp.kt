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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MoveToInbox
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
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
                    Column(horizontalAlignment = Alignment.Start) {
                        Text(
                            text = "From: ${email.from}",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth()

                        )
                        Text(
                            text = "Received: ${email.receivedDate.slice(0..15)}",
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = "Subject: ${email.subject}",
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth()
                        )

                    }
                }
            }
        }
    }

    @Composable
    fun SpamView() {

        var emails by remember { mutableStateOf(listOf<Email>()) }

        val context = LocalContext.current

        LaunchedEffect(Unit){
            try {
                val mails = EmailUtil.fetchSpamEmails("smtp.gmail.com", "587", EmailUtil.mailFrom, EmailUtil.password)
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
                    Column(horizontalAlignment = Alignment.Start) {
                        Text(
                            text = "From: ${email.from}",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth()

                        )
                        Text(
                            text = "Received: ${email.receivedDate.slice(0..15)}",
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = "Subject: ${email.subject}",
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth()
                        )

                    }
                }
            }
        }
    }

    @Composable
    fun SentView() {

        var emails by remember { mutableStateOf(listOf<Email>()) }

        val context = LocalContext.current

        LaunchedEffect(Unit){
            try {
                val mails = EmailUtil.fetchSent("smtp.gmail.com", EmailUtil.mailFrom, EmailUtil.password)
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
                    Column(horizontalAlignment = Alignment.Start) {
                        Text(
                            text = "From: ${email.from}",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth()

                        )
                        Text(
                            text = "Received: ${email.receivedDate.slice(0..15)}",
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = "Subject: ${email.subject}",
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth()
                        )

                    }
                }
            }
        }
    }

    @Composable
    fun TrashView() {

        var emails by remember { mutableStateOf(listOf<Email>()) }

        val context = LocalContext.current

        LaunchedEffect(Unit){
            try {
                val mails = EmailUtil.fetchTrashEmails("smtp.gmail.com", "587", EmailUtil.mailFrom, EmailUtil.password)
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
                    Column(horizontalAlignment = Alignment.Start) {
                        Text(
                            text = "From: ${email.from}",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth()

                        )
                        Text(
                            text = "Received: ${email.receivedDate.slice(0..15)}",
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = "Subject: ${email.subject}",
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth()
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
    fun TrashView1() {

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
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = "Subject: ${email.subject}",
                            fontWeight = FontWeight.Normal,
                            color = Color.Black
                        )
                        Text(
                            text = "Received: ${email.receivedDate}",
                            fontWeight = FontWeight.Normal,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun SendEmailView(onEmailSent: () -> Unit) {
        var to by remember { mutableStateOf("") }
        var subject by remember { mutableStateOf("") }
        var body by remember { mutableStateOf("") }

        val context = LocalContext.current

        Column(Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 16.dp)) {
            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "New mail",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
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
                                    onEmailSent()

                                }
                            } catch (e: Exception) {
                                // Handle any exceptions
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(context, "Failed to send email: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    },
                    shape = RoundedCornerShape(30),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0078CE))


                ) {
                    Text("Send", color = Color.White)
                }

            }
            val focusManager = LocalFocusManager.current

                OutlinedTextField(
                    value = to,
                    onValueChange = { to = it },
                    label = { Text("To") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedLabelColor = Color.Black,
                        focusedBorderColor = Color.Black,
                        unfocusedLabelColor = Color.Black,
                        unfocusedBorderColor = Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                )
                OutlinedTextField(
                    value = subject,
                    onValueChange = { subject = it },
                    label = { Text("Subject") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedLabelColor = Color.Black,
                        focusedBorderColor = Color.Black,
                        unfocusedLabelColor = Color.Black,
                        unfocusedBorderColor = Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                )
                OutlinedTextField(
                    value = body,
                    onValueChange = { body = it },
                    label = { Text("Body") },
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done

                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedLabelColor = Color.Black,
                        focusedBorderColor = Color.Black,
                        unfocusedLabelColor = Color.Black,
                        unfocusedBorderColor = Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                )
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
                Column() {
                    Text(
                        text = email.from,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 8.dp)
                    )
                    Text(
                        text = email.sentDate,
                        modifier = Modifier.padding(end = 8.dp),
                        color = Color.Black
                    )
                }
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
                            modifier = Modifier.padding(end = 8.dp),
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}

