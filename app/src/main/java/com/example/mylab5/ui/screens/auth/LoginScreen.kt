package com.example.mylab5.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
private val Radius = 16.dp

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onGoToRegister: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            // ===== LOGO =====
            Image(
                painter = painterResource(id = R.drawable.logo_login),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // ===== SIGN IN =====
            Text(
                text = stringResource(R.string.login_title),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = PurpleText,
                modifier = Modifier.fillMaxWidth()
            )

            // SIGN IN → EMAIL (46)
            Spacer(modifier = Modifier.height(46.dp))

            // ===== EMAIL =====
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                placeholder = { Text(stringResource(R.string.login_email)) },
                singleLine = true,
                shape = RoundedCornerShape(Radius),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.PersonOutline,
                        contentDescription = null,
                        tint = PurpleText
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BorderPurple,
                    unfocusedBorderColor = BorderPurple
                )
            )

            // EMAIL → PASSWORD (40)
            Spacer(modifier = Modifier.height(40.dp))

            // ===== PASSWORD =====
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                placeholder = { Text(stringResource(R.string.login_password)) },
                singleLine = true,
                shape = RoundedCornerShape(Radius),
                visualTransformation = if (passwordVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = null,
                        tint = PurpleText
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible)
                                Icons.Outlined.VisibilityOff
                            else
                                Icons.Outlined.Visibility,
                            contentDescription = null,
                            tint = Color.Black.copy(alpha = 0.7f)
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BorderPurple,
                    unfocusedBorderColor = BorderPurple
                )
            )

            // PASSWORD → FORGOT (40)
            Spacer(modifier = Modifier.height(40.dp))

            // ===== FORGOT PASSWORD =====
            Text(
                text = stringResource(R.string.login_forgot),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = PurpleText,
                modifier = Modifier.align(Alignment.End)
            )

            // FORGOT → BUTTON (40)
            Spacer(modifier = Modifier.height(40.dp))

            // ===== SIGN IN BUTTON =====
            Button(
                onClick = onLoginSuccess,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(Radius),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PurpleButton
                )
            ) {
                Text(
                    text = stringResource(R.string.login_button),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        // ===== BOTTOM REGISTER =====
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.login_no_account),
                fontSize = 15.sp,
                color = PurpleText
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(R.string.login_register),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = PurpleText,
                modifier = Modifier.clickable { onGoToRegister() }
            )
        }
    }
}
