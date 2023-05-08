package com.example.myapplication.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.myapplication.app.theme.AppTheme

class SpamActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme(darkTheme = false) {
                MailUtilComposables.SpamEmailsScreen(
                    onEmailDeleted = {
                    },
                    onEmailMovedToInbox = {

                    })
            }
        }
    }
}