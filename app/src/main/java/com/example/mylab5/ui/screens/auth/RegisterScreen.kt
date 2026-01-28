package com.example.mylab5.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylab5.R

private val PurpleText = Color(0xFF471AA0)
private val PurpleButton = Color(0xFFBB84E8)
private val BorderPurple = Color(0xFF9747FF)
private val LightPurple = Color(0xFFBB84E8)
private val DarkPurple = Color(0xFF471AA0)
private val Radius = 16.dp

@Composable
fun RegisterScreen(
    onBackToLogin: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F6FF))

    ) {

        // ===== DECORATIVE CIRCLES =====
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            // CIEMNE (MNIEJSZE) – POD SPODEM
            Box(
                modifier = Modifier
                    .size(128.dp)
                    .offset(x = 40.dp, y = (-88).dp)
                    .background(Color(0xFF471AA0), CircleShape)
                    .align(Alignment.TopEnd)
            )

            // JASNE (WIĘKSZE) – NA WIERZCHU
            Box(
                modifier = Modifier
                    .size(142.dp)
                    .offset(x = 110.dp, y = (-47).dp)
                    .background(Color(0xFFBB84E8), CircleShape)
                    .align(Alignment.TopEnd)
            )
        }


        // ===== CONTENT =====
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .align(Alignment.TopCenter)
        ) {

            Spacer(modifier = Modifier.height(59.dp))

            // BACK
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onBackToLogin() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = null,
                    tint = PurpleText
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(R.string.back),
                    color = PurpleText
                )
            }

            Spacer(modifier = Modifier.height(101.dp))

            // TITLE
            Text(
                text = stringResource(R.string.register_title),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = PurpleText
            )

            Spacer(modifier = Modifier.height(46.dp))

            // FULL NAME
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                placeholder = { Text(stringResource(R.string.register_name)) },
                leadingIcon = {
                    Icon(Icons.Outlined.PersonOutline, null, tint = PurpleText)
                },
                singleLine = true,
                shape = RoundedCornerShape(Radius),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BorderPurple,
                    unfocusedBorderColor = BorderPurple
                )
            )

            Spacer(modifier = Modifier.height(40.dp))

            // EMAIL
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                placeholder = { Text(stringResource(R.string.register_email)) },
                leadingIcon = {
                    Icon(Icons.Outlined.Email, null, tint = PurpleText)
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                shape = RoundedCornerShape(Radius),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BorderPurple,
                    unfocusedBorderColor = BorderPurple
                )
            )

            Spacer(modifier = Modifier.height(40.dp))

            // PASSWORD
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                placeholder = { Text(stringResource(R.string.login_password)) },
                leadingIcon = {
                    Icon(Icons.Outlined.Lock, null, tint = PurpleText)
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible)
                                Icons.Outlined.VisibilityOff
                            else
                                Icons.Outlined.Visibility,
                            contentDescription = null,
                            tint = PurpleText
                        )
                    }
                },
                visualTransformation = if (passwordVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
                singleLine = true,
                shape = RoundedCornerShape(Radius),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BorderPurple,
                    unfocusedBorderColor = BorderPurple
                )
            )

            Spacer(modifier = Modifier.height(40.dp))

            // CONFIRM PASSWORD
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                placeholder = { Text(stringResource(R.string.register_confirm_password)) },
                leadingIcon = {
                    Icon(Icons.Outlined.Lock, null, tint = PurpleText)
                },
                trailingIcon = {
                    IconButton(onClick = {
                        confirmPasswordVisible = !confirmPasswordVisible
                    }) {
                        Icon(
                            imageVector = if (confirmPasswordVisible)
                                Icons.Outlined.VisibilityOff
                            else
                                Icons.Outlined.Visibility,
                            contentDescription = null,
                            tint = PurpleText
                        )
                    }
                },
                visualTransformation = if (confirmPasswordVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
                singleLine = true,
                shape = RoundedCornerShape(Radius),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BorderPurple,
                    unfocusedBorderColor = BorderPurple
                )
            )

            Spacer(modifier = Modifier.height(80.dp))

            // SIGN UP BUTTON
            Button(
                onClick = onRegisterSuccess,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(Radius),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PurpleButton
                )
            ) {
                Text(
                    text = stringResource(R.string.register_button),
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        // ===== BOTTOM SIGN IN =====
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.register_have_account),
                fontSize = 15.sp,
                color = PurpleText
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(R.string.register_sign_in),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = PurpleText,
                modifier = Modifier.clickable { onBackToLogin() }
            )
        }
    }
}
