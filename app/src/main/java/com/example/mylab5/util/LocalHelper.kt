package com.example.mylab5.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences
import android.content.res.Configuration
import java.util.Locale

object LocalHelper {

    private const val PREF_NAME = "language_prefs"
    private const val KEY_LANG = "lang"

    private fun prefs(context: Context): SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveLanguage(context: Context, lang: String) {
        prefs(context).edit().putString(KEY_LANG, lang).apply()
    }

    fun getSavedLanguage(context: Context): String {
        return prefs(context).getString(KEY_LANG, "pl") ?: "pl"
    }

    fun setLocale(context: Context, lang: String): ContextWrapper {
        val locale = Locale(lang)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)

        return ContextWrapper(context.createConfigurationContext(config))
    }

    fun applyLanguageAndRestart(activity: Activity, lang: String) {
        saveLanguage(activity, lang)
        activity.recreate()
    }
}
