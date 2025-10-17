package com.example.money_manager.data.store

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.money_manager.utils.fromBase64
import com.example.money_manager.utils.toBase64
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

private val KEY_LOCK_ENABLED = booleanPreferencesKey("lock_enabled")
private val KEY_BIOMETRIC_ENABLED = booleanPreferencesKey("biometric_enabled")
private val KEY_LOCK_TIMEOUT_SEC = intPreferencesKey("lock_timeout_sec")
private val KEY_ENCRYPTED_PIN_BLOB = stringPreferencesKey("enc_pin_blob")

@Singleton
class AppLockStore @Inject constructor(
    @ApplicationContext context: Context,
    private val crypto: AppLockCrypto
) {
    private val Context.dataStore by preferencesDataStore(name = "app_lock")
    private val ds = context.dataStore

    val isLockEnabled: Flow<Boolean> = ds.data.map { it[KEY_LOCK_ENABLED] ?: false }
    val isBiometricEnabled: Flow<Boolean> = ds.data.map { it[KEY_BIOMETRIC_ENABLED] ?: false }
    val lockTimeoutSec: Flow<Int> = ds.data.map { it[KEY_LOCK_TIMEOUT_SEC] ?: 0 }

    suspend fun savePinHash(hash: ByteArray, salt: ByteArray, iter: Int) {
        val payload =
            """{"hash":"${hash.toBase64()}","salt":"${salt.toBase64()}", "iter":$iter}""".toByteArray()
        val enc = crypto.encrypt(payload)
        ds.edit { it[KEY_ENCRYPTED_PIN_BLOB] = enc }
    }

    suspend fun loadPinMeta(): Triple<ByteArray, ByteArray, Int>? = runCatching {
        ds.data.first()[KEY_ENCRYPTED_PIN_BLOB]?.let { enc ->
            val json = String(crypto.decrypt(enc))
            val obj = JSONObject(json)
            Triple(
                obj.getString("hash").fromBase64(),
                obj.getString("salt").fromBase64(),
                obj.getInt("iter")
            )
        }
    }.getOrNull()

    suspend fun setLockEnable(state: Boolean) = ds.edit { it[KEY_LOCK_ENABLED] = state
        Log.d("AppLockStore", "set value of KEY_LOCK_ENABLED to $state and now the value of KEY_LOCK_ENABLED is ${it[KEY_LOCK_ENABLED]}")
    }
    suspend fun setBiometricEnable(state: Boolean) = ds.edit { it[KEY_BIOMETRIC_ENABLED] = state }
    suspend fun setTimeoutSec(seconds: Int) = ds.edit { it[KEY_LOCK_TIMEOUT_SEC] = seconds }
}