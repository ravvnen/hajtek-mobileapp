package com.example.myapplication.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.app.theme.AppTheme

class TrashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
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

                        Text(
                            text = "Trash",
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 30.dp, vertical = 8.dp)
                        )
                    }

                    MailUtilComposables.TrashView()
                }



            }
        }
    }
}