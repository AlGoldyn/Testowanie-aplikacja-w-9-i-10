package com.example.mylab5.ui.screens.auth.login

import androidx.lifecycle.ViewModel
import com.example.mylab5.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    // ===== ZASZYTE DANE =====
    private val validEmail = "test@test.com"
    private val validPassword = "123456"

    fun onEmailChange(v: String) {
        _state.update {
            it.copy(
                email = v,
                emailError = null,
                loginError = null
            )
        }
    }

    fun onPasswordChange(v: String) {
        _state.update {
            it.copy(
                password = v,
                passwordError = null,
                loginError = null
            )
        }
    }

    fun submit(onSuccess: () -> Unit) {
        val s = _state.value

        var emailErr: Int? = null
        var passErr: Int? = null

        // ===== EMAIL CHECK =====
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(s.email).matches()) {
            emailErr = R.string.error_email
        }

        // ===== PASSWORD CHECK =====
        if (s.password.length < 6) {
            passErr = R.string.error_password_mismatch
        }

        if (emailErr != null || passErr != null) {
            _state.update {
                it.copy(
                    emailError = emailErr,
                    passwordError = passErr,
                    loginError = null
                )
            }
            return
        }

        // ===== LOGIN MATCH =====
        if (s.email == validEmail && s.password == validPassword) {
            onSuccess()
        } else {
            _state.update {
                it.copy(loginError = R.string.error_invalid_login)
            }
        }
    }
}
