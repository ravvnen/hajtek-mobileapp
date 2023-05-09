package com.Hajtek.MailClient.app.topmenu

import android.content.Intent
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
import com.Hajtek.MailClient.app.InboxActivity
import com.Hajtek.MailClient.app.MailUtilComposables
import com.Hajtek.MailClient.app.UIActivity
import com.Hajtek.MailClient.app.theme.AppTheme


class WriteMail : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme(darkTheme = true) {
                val intent = Intent(this@WriteMail, UIActivity::class.java)
                com.Hajtek.MailClient.app.MailUtilComposables.SendEmailView(onEmailSent = { startActivity(intent) })
            }
        }
    }
}
