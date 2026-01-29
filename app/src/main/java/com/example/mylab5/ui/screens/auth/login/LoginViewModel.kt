package com.example.mylab5.ui.screens.auth.login

import androidx.lifecycle.ViewModel
import com.example.mylab5.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun onEmailChange(v: String) {
        _state.value = _state.value.copy(email = v, emailError = null, loginError = null)
    }

    fun onPasswordChange(v: String) {
        _state.value = _state.value.copy(password = v, passwordError = null, loginError = null)
    }

    fun submit(
        onSuccess: () -> Unit,
        saveLogin: (Boolean) -> Unit
    ) {
        val s = _state.value

        var ok = true

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(s.email).matches()) {
            _state.value = _state.value.copy(emailError = R.string.error_invalid_login)
            ok = false
        }

        if (s.password.length < 6) {
            _state.value = _state.value.copy(passwordError = R.string.error_password_short)
            ok = false
        }

        if (!ok) return

        auth.signInWithEmailAndPassword(s.email, s.password)
            .addOnSuccessListener {
                saveLogin(true)
                onSuccess()
            }
            .addOnFailureListener {
                _state.value = _state.value.copy(
                    loginError = R.string.error_invalid_login
                )
            }
    }
}
