package com.Hajtek.MailClient.app

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.Hajtek.MailClient.app.topmenu.WriteMail
import com.Hajtek.MailClient.backend.Email
import com.Hajtek.MailClient.backend.EmailUtil
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
                val mails = EmailUtil.fetchInbox(activeUser.imapHost, activeUser.emailAddress, activeUser.password)
                emails = mails.reversed()
            }catch(e : Throwable){
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }



        LazyColumn {
            items(emails) { email ->
                Button(
                    onClick = {
                        val intent = Intent(context, ViewMail::class.java)
                        intent.putExtra("mailSubject", email.subject)
                        intent.putExtra("mailFrom", email.from)
                        intent.putExtra("mailDate", email.receivedDate)
                        intent.putExtra("mailBody", email.body)
                        startActivity(context, intent, null) },
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
                            text = email.subject,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis

                        )
                        Text(
                            text = email.from,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = email.receivedDate.slice(0..15),
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = email.body.trim(),
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.Gray,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }


    @Composable
    fun ViewMail(mailSubject: String, mailFrom: String, mailDate: String, mailBody: String) {

                    Column(horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .padding(16.dp)) {
                        Card() {
                            Column(
                                modifier = Modifier
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = mailSubject,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Left,
                                    modifier = Modifier.fillMaxWidth(),
                                    color = Color.Black
                                )
                                Text(
                                    text = mailFrom,
                                    fontWeight = FontWeight.Normal,
                                    textAlign = TextAlign.Left,
                                    modifier = Modifier.fillMaxWidth(),
                                    color = Color.Black

                                )
                                Text(
                                    text = mailDate.slice(0..15),
                                    fontWeight = FontWeight.Normal,
                                    textAlign = TextAlign.Left,
                                    modifier = Modifier.fillMaxWidth(),
                                    color = Color.Gray
                                )
                            }

                        }
                        Spacer(modifier = Modifier.size(8.dp))

                        Card() {
                            Column(
                                modifier = Modifier
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = mailBody,
                                    fontWeight = FontWeight.Normal,
                                    textAlign = TextAlign.Left,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .verticalScroll(rememberScrollState(0)),
                                    color = Color.Black,

                                    )

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
                val mails = EmailUtil.fetchSpamEmails(activeUser.imapHost, "587", activeUser.emailAddress, activeUser.password)
                emails = mails.reversed()
            }catch(e : Throwable){
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        LazyColumn {
            items(emails) { email ->
                Button(
                    onClick = { val intent = Intent(context, ViewMail::class.java)
                        intent.putExtra("mailSubject", email.subject)
                        intent.putExtra("mailFrom", email.from)
                        intent.putExtra("mailDate", email.receivedDate)
                        intent.putExtra("mailBody", email.body)
                        startActivity(context, intent, null) },
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
                            text = email.subject,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis

                        )
                        Text(
                            text = email.from,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = email.receivedDate.slice(0..15),
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = email.body.trim(),
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.Gray,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
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
                val mails = EmailUtil.fetchSent(activeUser.imapHost, activeUser.emailAddress, activeUser.password)
                emails = mails.reversed()
            }catch(e : Throwable){
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        LazyColumn {
            items(emails) { email ->
                Button(
                    onClick = { val intent = Intent(context, ViewMail::class.java)
                        intent.putExtra("mailSubject", email.subject)
                        intent.putExtra("mailFrom", email.from)
                        intent.putExtra("mailDate", email.receivedDate)
                        intent.putExtra("mailBody", email.body)
                        startActivity(context, intent, null) },
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
                            text = email.subject,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis

                        )
                        Text(
                            text = email.from,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = email.receivedDate.slice(0..15),
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = email.body.trim(),
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.Gray,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
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
                val mails = EmailUtil.fetchTrashEmails(activeUser.imapHost, "587", activeUser.emailAddress, activeUser.password)
                emails = mails.reversed()
            }catch(e : Throwable){
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        LazyColumn {
            items(emails) { email ->
                Button(
                    onClick = { val intent = Intent(context, ViewMail::class.java)
                        intent.putExtra("mailSubject", email.subject)
                        intent.putExtra("mailFrom", email.from)
                        intent.putExtra("mailDate", email.receivedDate)
                        intent.putExtra("mailBody", email.body)
                        startActivity(context, intent, null) },
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
                            text = email.subject,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = email.from,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = email.receivedDate.slice(0..15),
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = email.body.trim(),
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.Gray,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
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
                                    host = activeUser.smtpHost,
                                    port = activeUser.smtpPort,
                                    username = activeUser.emailAddress,
                                    password = activeUser.password,
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
    fun EmailItem(email: Email, onItemHold: () -> Unit, showOptionsMenu: Boolean = false, options: List<com.Hajtek.MailClient.app.EmailOption> = emptyList()) {
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

