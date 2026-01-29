package com.example.mylab5.ui.screens.auth.login

import androidx.lifecycle.ViewModel
import com.example.mylab5.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import com.example.mylab5.util.AuthPreferences

class LoginViewModel : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    fun onEmailChange(value: String) {
        _state.update {
            it.copy(
                email = value,
                emailError = null,
                loginError = null
            )
        }
    }

    fun onPasswordChange(value: String) {
        _state.update {
            it.copy(
                password = value,
                passwordError = null,
                loginError = null
            )
        }
    }

    fun submit(context: android.content.Context, onSuccess: () -> Unit) {
        val s = _state.value

        var emailError: Int? = null
        var passwordError: Int? = null

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(s.email).matches()) {
            emailError = R.string.error_invalid_login
        }

        if (s.password.length < 6) {
            passwordError = R.string.error_password_short
        }

        if (emailError != null || passwordError != null) {
            _state.update {
                it.copy(emailError = emailError, passwordError = passwordError)
            }
            return
        }

        val validEmail = "test@test.com"
        val validPassword = "123456"

        if (s.email == validEmail && s.password == validPassword) {

            AuthPreferences.setLoggedIn(context, true)

            onSuccess()
        } else {
            _state.update {
                it.copy(loginError = R.string.error_invalid_login)
            }
        }
    }
}
