package com.example.money_manager.utils

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit
import com.example.money_manager.domain.model.Currency
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.serialization.json.Json

@Singleton
class PreferencesManager @Inject constructor(
    context: Context
) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    companion object {
        const val IS_GUEST = "is_guest"
        const val IS_AUTHORIZED = "is_authorized"
        const val CURRENCY = "selected_currency"

        const val CONVERT_EXISTS = "convert_exists"
    }

    var isGuest: Boolean
        get() = prefs.getBoolean(IS_GUEST, false)
        set(value) = prefs.edit { putBoolean(IS_GUEST, value) }

    var isAuthorized: Boolean
        get() = prefs.getBoolean(IS_AUTHORIZED, false)
        set(value) = prefs.edit { putBoolean(IS_AUTHORIZED, value) }

    var currency: Currency
        get() {
            val json = prefs.getString(CURRENCY, null)
            return if (json != null) {
                try {
                    Json.decodeFromString(json)
                } catch (e: Exception){
                    Currency("RUB", "Российский рубль", "₽")
                }
            } else {
                Currency("RUB", "Российский рубль", "₽")
            }
        }
        set(value){
            val json = Json.encodeToString(value)
            prefs.edit { putString(CURRENCY, json) }
        }

    val selectedCurrencyFlow: Flow<String> = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == CURRENCY) trySend(currency.code)
        }
        prefs.registerOnSharedPreferenceChangeListener(listener)

        trySend(currency.code)

        awaitClose { prefs.unregisterOnSharedPreferenceChangeListener(listener) }
    }.distinctUntilChanged()

    var convertExists: Boolean
        get() = prefs.getBoolean(CONVERT_EXISTS, true)
        set(value) = prefs.edit { putBoolean(CONVERT_EXISTS, value) }
}