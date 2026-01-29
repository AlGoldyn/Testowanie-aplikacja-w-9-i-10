package com.example.mylab5.ui.screens.auth.register

data class RegisterState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",

    val nameError: Int? = null,
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val confirmPasswordError: Int? = null,
    val registerError: Int? = null,

    val loading: Boolean = false
)
