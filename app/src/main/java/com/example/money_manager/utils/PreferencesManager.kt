package com.example.money_manager.utils

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit

@Singleton
class PreferencesManager @Inject constructor(
    context: Context
) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    companion object {
        const val IS_GUEST = "is_guest"
        const val IS_AUTHORIZED = "is_authorized"
    }

    var isGuest: Boolean
        get() = prefs.getBoolean(IS_GUEST, false)
        set(value) = prefs.edit { putBoolean(IS_GUEST, value) }

    var isAuthorized: Boolean
        get() = prefs.getBoolean(IS_AUTHORIZED, false)
        set(value) = prefs.edit { putBoolean(IS_AUTHORIZED, value) }
}