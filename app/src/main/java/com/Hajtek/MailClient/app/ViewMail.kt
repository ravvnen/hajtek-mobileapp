package com.Hajtek.MailClient.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.Hajtek.MailClient.app.theme.AppTheme

class ViewMail : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mailSubject = intent.getStringExtra("mailSubject")
        val mailFrom = intent.getStringExtra("mailFrom")
        val mailDate = intent.getStringExtra("mailDate")
        val mailBody = intent.getStringExtra("mailBody")
        setContent() {
            AppTheme(darkTheme = false) {
                Column() {
                    Spacer(modifier = Modifier.size(8.dp))
                    Row() {
                        /*
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back",
                            )
                        }
                         */


                    }

                    if (mailSubject != null) {
                        if (mailFrom != null) {
                            if (mailDate != null) {
                                if (mailBody != null) {
                                    MailUtilComposables.ViewMail(mailSubject, mailFrom, mailDate, mailBody)
                                }
                            }
                        }
                    }
                }


            }
        }
    }
}






