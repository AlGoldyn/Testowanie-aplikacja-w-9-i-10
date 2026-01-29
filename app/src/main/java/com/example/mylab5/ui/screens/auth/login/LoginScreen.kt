package com.example.mylab5.ui.screens.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mylab5.R

private val PurpleText = Color(0xFF471AA0)
private val PurpleButton = Color(0xFFBB84E8)
private val BorderPurple = Color(0xFF9747FF)
private val Radius = 16.dp

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onGoToRegister: () -> Unit,
    vm: LoginViewModel = viewModel()
) {
    val state by vm.state.collectAsState()
    val context = LocalContext.current

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

            Spacer(Modifier.height(40.dp))

            Image(
                painter = painterResource(R.drawable.logo_login),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.height(32.dp))

            Text(
                text = stringResource(R.string.login_title),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = PurpleText
            )

            Spacer(Modifier.height(46.dp))


            OutlinedTextField(
                value = state.email,
                onValueChange = vm::onEmailChange,
                isError = state.emailError != null,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        stringResource(R.string.login_email),
                        color = Color.Gray
                    )
                },
                leadingIcon = {
                    Icon(Icons.Outlined.PersonOutline, null, tint = PurpleText)
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                shape = RoundedCornerShape(Radius),
                supportingText = {
                    state.emailError?.let {
                        Text(
                            stringResource(it),
                            color = Color.Red,
                            fontSize = 13.sp
                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BorderPurple,
                    unfocusedBorderColor = BorderPurple,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedPlaceholderColor = Color.Gray,
                    unfocusedPlaceholderColor = Color.Gray,
                    cursorColor = PurpleText
                )
            )

            Spacer(Modifier.height(40.dp))


            OutlinedTextField(
                value = state.password,
                onValueChange = vm::onPasswordChange,
                isError = state.passwordError != null,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        stringResource(R.string.login_password),
                        color = Color.Gray
                    )
                },
                leadingIcon = {
                    Icon(Icons.Outlined.Lock, null, tint = PurpleText)
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            if (passwordVisible)
                                Icons.Outlined.VisibilityOff
                            else Icons.Outlined.Visibility,
                            contentDescription = null,
                            tint = Color.Black.copy(alpha = 0.7f)
                        )
                    }
                },
                visualTransformation = if (passwordVisible)
                    VisualTransformation.None
                else PasswordVisualTransformation(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                shape = RoundedCornerShape(Radius),
                supportingText = {
                    state.passwordError?.let {
                        Text(
                            stringResource(it),
                            color = Color.Red,
                            fontSize = 13.sp
                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BorderPurple,
                    unfocusedBorderColor = BorderPurple,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedPlaceholderColor = Color.Gray,
                    unfocusedPlaceholderColor = Color.Gray,
                    cursorColor = PurpleText
                )
            )

            Spacer(Modifier.height(40.dp))

            Text(
                text = stringResource(R.string.login_forgot),
                fontWeight = FontWeight.Bold,
                color = PurpleText,
                modifier = Modifier.align(Alignment.End)
            )

            Spacer(Modifier.height(40.dp))


            Button(
                onClick = { vm.submit(context, onLoginSuccess) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(Radius),
                colors = ButtonDefaults.buttonColors(containerColor = PurpleButton)
            ) {
                Text(
                    stringResource(R.string.login_button),
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            state.loginError?.let {
                Spacer(Modifier.height(12.dp))
                Text(
                    stringResource(it),
                    color = Color.Red,
                    fontSize = 13.sp
                )
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
        ) {
            Text(
                stringResource(R.string.login_no_account),
                color = PurpleText
            )

            Spacer(Modifier.width(4.dp))

            Text(
                stringResource(R.string.login_register),
                fontWeight = FontWeight.Bold,
                color = PurpleText,
                modifier = Modifier.clickable { onGoToRegister() }
            )
        }
    }
}
