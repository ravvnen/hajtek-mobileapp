package com.Hajtek.MailClient.app.topmenu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.Hajtek.MailClient.app.activeUser
import com.Hajtek.MailClient.app.theme.AppTheme


class AccountActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme(darkTheme = true) {
            Surface(modifier = Modifier
                .fillMaxSize(),
            color = Color.White)
            {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth()
                        .padding(18.dp)
                        .background(MaterialTheme.colors.background),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    //Spacer(modifier = Modifier.size(8.dp))
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Account",
                        modifier = Modifier
                            .padding(15.dp)
                            .wrapContentWidth(Alignment.CenterHorizontally)
                            .size(192.dp)
                            .offset(y = 40.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        tint = Color.Black
                    )

                    Text(
                        text = "Gmail account information",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        backgroundColor = Color.White,
                        elevation = 4.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Name: ${activeUser.firstName} ${activeUser.lastName}",
                                fontSize = 16.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Left
                            )
                            Text(
                                text = "Email: ${activeUser.emailAddress}",
                                fontSize = 16.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Left
                            )
                            Text(
                                text = "Password: **********",
                                fontSize = 16.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Left
                            )
                            Text(
                                text = "SMTP host: ${activeUser.smtpHost}",
                                fontSize = 16.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Left
                            )
                            Text(
                                text = "SMTP port: ${activeUser.smtpPort}",
                                fontSize = 16.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Left
                            )

                            Text(
                                text = "IMAP host: ${activeUser.imapHost}",
                                fontSize = 16.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Left
                            )
                            Text(
                                text = "IMAP port: ${activeUser.imapPort}",
                                fontSize = 16.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Left
                            )

                        }

                    }
                    Spacer(modifier = Modifier.size(8.dp))
                    Button(
                        onClick = { finish() },
                        modifier = Modifier.width(180.dp),
                        elevation = ButtonDefaults.elevation(
                            defaultElevation = 4.dp
                        ),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0078CE))
                    ) {
                        Text("Return", color = Color.White)
                    }
                }
            }
                
            }
        }
    }
}
