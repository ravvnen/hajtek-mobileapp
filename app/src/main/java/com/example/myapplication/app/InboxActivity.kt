package com.example.myapplication.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.app.theme.AppTheme
import com.example.myapplication.backend.Amascut_Bosses

class InboxActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme(darkTheme = true) {
                Column(
                    Modifier.background(color = Color.Black)
                ) {
                    for (boss in Amascut_Bosses)
                    {
                        Card(Modifier.clickable { val intent =
                            Intent(this@InboxActivity, ViewMailActivity::class.java)
                            intent.putExtra("bossname", boss.name)
                            startActivity(intent)}
                            .background(Color.Red)
                            .padding(all=2.dp)
                            .weight(1f))
                        {

                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(all = 30.dp)
                            ) {
                                Text(text = boss.name)

                            Image(
                                painter = painterResource(id = boss.imageName),
                                contentDescription = "vacation image",
                                modifier = Modifier.size(90.dp)
                            )
                            }
                    }
                }
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(64.dp) // Set the height of the Box to a fixed size
                    ) {
                        Button(
                            onClick = { finish() },
                            modifier = Modifier
                                .fillMaxWidth() // Make the button stretch to fill the entire width of the screen
                                .padding(16.dp),
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
}



