package com.example.mylab5.ui.screens.auth.register

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mylab5.R
import androidx.compose.foundation.text.KeyboardOptions


private val PurpleText = Color(0xFF471AA0)
private val PurpleButton = Color(0xFFBB84E8)
private val BorderPurple = Color(0xFF9747FF)
private val PlaceholderColor = Color(0xFF9E9E9E)
private val Radius = 16.dp

@Composable
fun RegisterScreen(
    onBackToLogin: () -> Unit,
    viewModel: RegisterViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    var passVisible by remember { mutableStateOf(false) }
    var confirmVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F6FF))
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .align(Alignment.TopCenter)
        ) {

            Spacer(Modifier.height(59.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onBackToLogin() }
            ) {
                Icon(Icons.Filled.ChevronLeft, null, tint = PurpleText)
                Spacer(Modifier.width(4.dp))
                Text(stringResource(R.string.back), color = PurpleText)
            }

            Spacer(Modifier.height(60.dp))

            Text(
                stringResource(R.string.register_title),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = PurpleText
            )

            Spacer(Modifier.height(40.dp))

            Field(
                state.name,
                viewModel::onNameChange,
                R.string.register_name,
                Icons.Outlined.PersonOutline,
                state.nameError
            )

            Spacer(Modifier.height(24.dp))

            Field(
                state.email,
                viewModel::onEmailChange,
                R.string.register_email,
                Icons.Outlined.Email,
                state.emailError,
                KeyboardType.Email
            )

            Spacer(Modifier.height(24.dp))

            PasswordField(
                state.password,
                viewModel::onPasswordChange,
                passVisible,
                { passVisible = !passVisible },
                state.passwordError
            )

            Spacer(Modifier.height(24.dp))

            PasswordField(
                state.confirmPassword,
                viewModel::onConfirmPasswordChange,
                confirmVisible,
                { confirmVisible = !confirmVisible },
                state.confirmPasswordError
            )

            Spacer(Modifier.height(40.dp))

            Button(
                onClick = {
                    viewModel.submit {
                        onBackToLogin() // ← wracamy do login
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(Radius),
                colors = ButtonDefaults.buttonColors(containerColor = PurpleButton)
            ) {
                Text(stringResource(R.string.register_button), color = Color.White)
            }

            state.registerError?.let {
                Spacer(Modifier.height(12.dp))
                Text(stringResource(it), color = Color.Red)
            }
        }
    }
}

@Composable
private fun Field(
    value: String,
    onChange: (String) -> Unit,
    placeholderRes: Int,
    icon: ImageVector,
    error: Int?,
    type: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value,
        onChange,
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(color = Color.Black),
        placeholder = { Text(stringResource(placeholderRes), color = PlaceholderColor) },
        leadingIcon = { Icon(icon, null, tint = PurpleText) },
        keyboardOptions = KeyboardOptions(keyboardType = type),
        isError = error != null,
        supportingText = { error?.let { Text(stringResource(it), color = Color.Red) } },
        shape = RoundedCornerShape(Radius),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = BorderPurple,
            unfocusedBorderColor = BorderPurple
        )
    )
}

@Composable
private fun PasswordField(
    value: String,
    onChange: (String) -> Unit,
    visible: Boolean,
    toggle: () -> Unit,
    error: Int?
) {
    OutlinedTextField(
        value,
        onChange,
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(color = Color.Black),
        placeholder = { Text(stringResource(R.string.login_password), color = PlaceholderColor) },
        leadingIcon = { Icon(Icons.Outlined.Lock, null, tint = PurpleText) },
        trailingIcon = {
            IconButton(onClick = toggle) {
                Icon(
                    if (visible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                    null
                )
            }
        },
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
        isError = error != null,
        supportingText = { error?.let { Text(stringResource(it), color = Color.Red) } },
        shape = RoundedCornerShape(Radius),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = BorderPurple,
            unfocusedBorderColor = BorderPurple
        )
    )
}
