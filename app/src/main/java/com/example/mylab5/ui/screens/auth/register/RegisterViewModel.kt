package com.example.mylab5.ui.screens.auth.register

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.example.mylab5.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RegisterViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    fun onNameChange(v: String) {
        _state.value = _state.value.copy(name = v, nameError = null)
    }

    fun onEmailChange(v: String) {
        _state.value = _state.value.copy(email = v, emailError = null)
    }

    fun onPasswordChange(v: String) {
        _state.value = _state.value.copy(password = v, passwordError = null)
    }

    fun onConfirmPasswordChange(v: String) {
        _state.value = _state.value.copy(confirmPassword = v, confirmPasswordError = null)
    }

    fun submit(onSuccess: () -> Unit) {
        val s = _state.value
        var ok = true

        val email = s.email.trim()

        if (s.name.isBlank()) {
            _state.value = _state.value.copy(
                nameError = R.string.error_required
            )
            ok = false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
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

        if (s.password != s.confirmPassword) {
            _state.value = _state.value.copy(
                confirmPasswordError = R.string.error_password_mismatch
            )
            ok = false
        }

        if (!ok) return

        _state.value = _state.value.copy(loading = true)

        auth.createUserWithEmailAndPassword(email, s.password)
            .addOnSuccessListener {
                Log.d("REGISTER", "User created OK: $email")

                _state.value = _state.value.copy(
                    loading = false,
                    registerError = null
                )

                onSuccess()
            }
            .addOnFailureListener { e ->

                Log.d("REGISTER", "Firebase error: ${e.message}")

                _state.value = _state.value.copy(
                    loading = false,
                    registerError = R.string.error_invalid_login
                )
            }
    }
}
