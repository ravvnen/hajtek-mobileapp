package com.example.MailClient.app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Key
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.MailClient.app.theme.AppTheme
import com.example.MailClient.app.topmenu.*
import com.example.myapplication.R

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme(darkTheme = false) {
                //LoginPage(onLogin = { email, password -> handleLogin(email, password) })
                //Login_page()

                setContent {
                    //var showLoginPage by remember { mutableStateOf(true) }
                    //SignUpPage(
                    //    onSignUp = { email, password -> handleSignUp(email, password) },
                    //    onNavigateToLogin = { showLoginPage = true }
                    //)

                    var showLoginPage by remember { mutableStateOf(true) }
                    if (showLoginPage) {
                        LoginPage(
                            onLogin = { email, password -> handleLogin(email, password) },
                            onNavigateToSignUp = { showLoginPage = false }
                        )
                    } else {
                        SignUpPage(
                            onSignUp = { email, password -> handleSignUp(email, password) },
                            onNavigateToLogin = { showLoginPage = true }
                        )
                    }


                }
            }
        }

    }
    private fun handleLogin(email: String, password: String) {
        val auth = Firebase.auth
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Login success
                    Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser
                    val intent = Intent(this@MainActivity, UIActivity::class.java)
                    //intent.putExtra("kode", user?.email)
                    startActivity(intent)
                    // Do something with the logged-in user
                } else {
                    // Login failed
                    Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun handleSignUp(email: String, password: String) {
        val auth = Firebase.auth
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign-up success
                    Toast.makeText(this, "Sign-up success", Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser
                    // Do something with the signed-up user
                } else {
                    // Sign-up failed
                    Toast.makeText(this, "Sign-up failed", Toast.LENGTH_SHORT).show()
                }
            }
    }
}





@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Login_page(){
    val context = LocalContext.current
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Box() {
            Column(
                Modifier
                    .fillMaxSize(),
                //horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(painterResource(id = R.drawable.hajteklogo2), contentDescription = null)
                Text(
                    text = "Welcome to your new mail client!",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                )

                val username = remember { mutableStateOf(TextFieldValue()) }
                val password = remember { mutableStateOf(TextFieldValue()) }
                val keyboardController = LocalSoftwareKeyboardController.current

                OutlinedTextField(
                    value = username.value,
                    leadingIcon = { Icon(imageVector = Icons.Default.Email,
                        contentDescription = "Icon",
                        tint = Black
                    ) },
                    onValueChange = {
                        username.value = it
                    },
                    label = { Text(text = "Username") },
                    //keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedLabelColor = Black,
                        focusedBorderColor = Black,
                        unfocusedLabelColor = Black,
                        unfocusedBorderColor = Black)
                )

                OutlinedTextField(
                    value = password.value,
                    leadingIcon = { Icon(imageVector = Icons.Default.Key,
                        contentDescription = "Icon",
                        tint = Black
                    ) },
                    onValueChange = {
                        username.value = it
                    },
                    label = { Text(text = "Password") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedLabelColor = Black,
                        focusedBorderColor = Black,
                        unfocusedLabelColor = Black,
                        unfocusedBorderColor = Black)
                )



                //TextFieldWithIcons("Email address", Icons.Default.Email,KeyboardType.Email)
                //TextFieldWithIcons("Password", Icons.Default.Key,KeyboardType.Password)



                Button(onClick = { Toast.makeText(context, username.toString(), Toast.LENGTH_SHORT).show()}) {
                    Text("Login")
                }
            }
            Box(modifier = Modifier
                .fillMaxSize(),
                contentAlignment = Alignment.BottomStart) {
                Text("Made by \nOliver Rosengreen Henriksen",
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 16.sp)
                )
            }
        }
    }
}

@Composable
fun SignUpPage(onSignUp: (email: String, password: String) -> Unit, onNavigateToLogin: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier
            .padding(10.dp)){
            Icon(painterResource(id = R.drawable.hajteklogo2), contentDescription = null)
        }
        Text(
            text = "Welcome, please sign up!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = Black,
                focusedBorderColor = Black,
                unfocusedLabelColor = Black,
                unfocusedBorderColor = Black),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = Black,
                focusedBorderColor = Black,
                unfocusedLabelColor = Black,
                unfocusedBorderColor = Black),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Button(
            onClick = {
                onSignUp(email, password)
                onNavigateToLogin()},
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0078CE))
        ) {
            Text(text = "Sign Up",
            color = Color.White)
        }
        TextButton(
            onClick = { onNavigateToLogin() },
            modifier = Modifier
                .padding(top = 0.dp)
        ) {
            Text(text = "Already have an account? Click here to go back!",
                color = Color.Gray)
        }
    }
}



@Composable
fun LoginPage(onLogin: (email: String, password: String) -> Unit, onNavigateToSignUp: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier
            .padding(10.dp)){
            Icon(painterResource(id = R.drawable.hajteklogo2), contentDescription = null)
        }
        Text(
            text = "Please log in to continue",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = Black,
                focusedBorderColor = Black,
                unfocusedLabelColor = Black,
                unfocusedBorderColor = Black),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = Black,
                focusedBorderColor = Black,
                unfocusedLabelColor = Black,
                unfocusedBorderColor = Black),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Button(
            onClick = { onLogin(email, password) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0078CE))
        ) {
            Text(text = "Login",
                color = Color.White)
        }
        TextButton(
            onClick = { onNavigateToSignUp() },
            modifier = Modifier
                .padding(top = 0.dp)
        ) {
            Text(text = "Don't have an account? Sign up here",
            color = Color.Gray)
        }
    }
}





@Composable
fun TextFieldWithIcons(placeholder_text: String, icon: ImageVector, keyboardtype: KeyboardType) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    Text(text = text.text)
    return OutlinedTextField(
        value = text,
        leadingIcon = { Icon(imageVector = icon,
            contentDescription = "Icon",
            tint = Black
        ) },
        onValueChange = {
            text = it
        },
        label = { Text(text = placeholder_text) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardtype),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedLabelColor = Black,
            focusedBorderColor = Black,
            unfocusedLabelColor = Black,
            unfocusedBorderColor = Black)
    )
}