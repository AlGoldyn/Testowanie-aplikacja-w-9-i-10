package com.example.mylab5.ui.screens.auth.login

import androidx.lifecycle.ViewModel
import com.example.mylab5.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    fun onEmailChange(v: String) {
        _state.value = _state.value.copy(email = v, emailError = null)
    }

    fun onPasswordChange(v: String) {
        _state.value = _state.value.copy(password = v, passwordError = null)
    }

    fun submit(onSuccess: () -> Unit) {
        val s = _state.value
        var ok = true

        if (!s.email.contains("@")) {
            _state.value = _state.value.copy(
                emailError = R.string.error_email
            )
            ok = false
        }

        if (s.password.length < 6) {
            _state.value = _state.value.copy(
                passwordError = R.string.error_password_short
            )
            ok = false
        }

        if (ok) {
            if (s.email == "test@test.com" && s.password == "123456") {
                onSuccess()
            } else {
                _state.value = _state.value.copy(
                    passwordError = R.string.error_invalid_login
                )
            }
        }
    }
}
