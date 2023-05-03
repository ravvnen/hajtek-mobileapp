package com.example.myapplication.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.app.theme.AppTheme
import com.example.myapplication.backend.Amascut_Bosses

class ViewMailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme(darkTheme = true) {
                val bossname = intent.getStringExtra("bossname")
                val bossitem = Amascut_Bosses.find{it.name == bossname}

                Column(
                    Modifier
                        .background(color = Color.Black)
                        .fillMaxSize()
                        .padding(50.dp),

                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    Image(
                        painter = painterResource(id = bossitem?.imageName ?: 0),
                        contentDescription = "Boss Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    )
                    Spacer(Modifier.height(16.dp))

                    Text(
                        text = "Name: " + (bossitem?.name ?: ""),
                        fontSize = 24.sp,
                        color = Color.Red,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Level: " + (bossitem?.level ?: ""),
                        fontSize = 24.sp,
                        color = Color.Red,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Max hit: " + (bossitem?.maxHit ?: ""),
                        fontSize = 24.sp,
                        color = Color.Red,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Attack style: " + (bossitem?.attackStyle ?: ""),
                        fontSize = 24.sp,
                        color = Color.Red,
                        textAlign = TextAlign.Center
                    )
                    }

                    Box(
                        Modifier
                            .fillMaxSize()
                            .padding(bottom = 16.dp)
                    ) {
                        Button(
                            onClick = { finish() },
                            modifier = Modifier
                                .width(180.dp)
                                .align(Alignment.BottomCenter),

                            elevation = ButtonDefaults.elevation(
                                defaultElevation = 10.dp
                            ),
                            shape = RoundedCornerShape(30),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
                        ) {
                            Text("Return", color = Color.Red)
                        }
                    }
                }

                }


            }
        }


