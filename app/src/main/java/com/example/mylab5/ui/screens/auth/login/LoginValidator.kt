package com.example.mylab5.ui.screens.auth.login

import com.example.mylab5.R

object LoginValidator {

    private val emailRegex =
        Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")

    fun validateEmail(email: String): Int? {
        if (email.isBlank()) return R.string.error_required
        if (!emailRegex.matches(email)) return R.string.error_email
        return null
    }

    fun validatePassword(password: String): Int? {
        if (password.length < 6) return R.string.error_password_short
        return null
    }
}
