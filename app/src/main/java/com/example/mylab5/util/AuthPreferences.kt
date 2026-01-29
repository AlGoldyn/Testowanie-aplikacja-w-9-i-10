package com.example.mylab5.util

import android.content.Context

object AuthPreferences {

    private const val PREFS = "auth_prefs"
    private const val KEY_LOGGED = "is_logged"

    fun setLoggedIn(context: Context, value: Boolean) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_LOGGED, value)
            .apply()
    }

    fun isLoggedIn(context: Context): Boolean {
        return context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getBoolean(KEY_LOGGED, false)
    }
}
