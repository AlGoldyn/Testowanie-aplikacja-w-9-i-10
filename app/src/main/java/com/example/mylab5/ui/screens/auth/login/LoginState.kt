package com.example.mylab5.ui.screens.auth.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val loginError: Int? = null
)
