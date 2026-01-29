package com.example.mylab5.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
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
private val PlaceholderColor = Color(0xFF9E9E9E)
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
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .size(128.dp)
                    .offset(x = 40.dp, y = (-88).dp)
                    .background(PurpleText, CircleShape)
                    .align(Alignment.TopEnd)
            )

            Box(
                modifier = Modifier
                    .size(142.dp)
                    .offset(x = 110.dp, y = (-47).dp)
                    .background(PurpleButton, CircleShape)
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
                    imageVector = Icons.Filled.ChevronLeft,
                    contentDescription = null,
                    tint = PurpleText,
                    modifier = Modifier.size(26.dp)
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

            // ===== NAME =====
            AppTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = stringResource(R.string.register_name),
                icon = Icons.Outlined.PersonOutline
            )

            Spacer(modifier = Modifier.height(40.dp))

            // ===== EMAIL =====
            AppTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = stringResource(R.string.register_email),
                icon = Icons.Outlined.Email,
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(40.dp))

            // ===== PASSWORD =====
            PasswordField(
                value = password,
                onValueChange = { password = it },
                visible = passwordVisible,
                onToggle = { passwordVisible = !passwordVisible }
            )

            Spacer(modifier = Modifier.height(40.dp))

            // ===== CONFIRM PASSWORD =====
            PasswordField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                visible = confirmPasswordVisible,
                onToggle = { confirmPasswordVisible = !confirmPasswordVisible }
            )

            Spacer(modifier = Modifier.height(80.dp))

            // ===== BUTTON =====
            Button(
                onClick = onRegisterSuccess,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
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

@Composable
private fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        textStyle = TextStyle(color = Color.Black),
        placeholder = {
            Text(placeholder, color = PlaceholderColor)
        },
        leadingIcon = {
            Icon(icon, null, tint = PurpleText)
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = true,
        shape = RoundedCornerShape(Radius),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = BorderPurple,
            unfocusedBorderColor = BorderPurple,
            cursorColor = Color.Black
        )
    )
}

@Composable
private fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    visible: Boolean,
    onToggle: () -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        textStyle = TextStyle(color = Color.Black),
        placeholder = {
            Text(stringResource(R.string.login_password), color = PlaceholderColor)
        },
        leadingIcon = {
            Icon(Icons.Outlined.Lock, null, tint = PurpleText)
        },
        trailingIcon = {
            IconButton(onClick = onToggle) {
                Icon(
                    imageVector = if (visible)
                        Icons.Outlined.VisibilityOff
                    else
                        Icons.Outlined.Visibility,
                    contentDescription = null,
                    tint = Color.Black.copy(alpha = 0.7f)
                )
            }
        },
        visualTransformation = if (visible)
            VisualTransformation.None
        else
            PasswordVisualTransformation(),
        singleLine = true,
        shape = RoundedCornerShape(Radius),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = BorderPurple,
            unfocusedBorderColor = BorderPurple,
            cursorColor = Color.Black
        )
    )
}
