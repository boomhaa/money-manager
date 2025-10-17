package com.example.money_manager.data.store

import android.content.Context
import com.google.crypto.tink.Aead
import com.google.crypto.tink.aead.AesGcmKeyManager
import com.google.crypto.tink.integration.android.AndroidKeysetManager
import com.google.crypto.tink.subtle.Base64
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppLockCrypto @Inject constructor(
    @ApplicationContext context: Context
) {
    private val aead: Aead by lazy {
        val keyManager = AesGcmKeyManager.aes256GcmTemplate()
        val handle = AndroidKeysetManager.Builder()
            .withSharedPref(context, "app_lock_keyset", "app_lock_prefs")
            .withKeyTemplate(keyManager)
            .withMasterKeyUri("android-keystore://app_lock_master_key")
            .build()
            .keysetHandle
        handle.getPrimitive(Aead::class.java)
    }

    fun encrypt(plain: ByteArray, aad: ByteArray = "v1".toByteArray()): String{
        val ct = aead.encrypt(plain, aad)
        return Base64.encodeToString(ct, Base64.NO_WRAP)
    }

    fun decrypt(b64: String, aad: ByteArray = "v1".toByteArray()): ByteArray{
        val ct = Base64.decode(b64, Base64.NO_WRAP)
        return aead.decrypt(ct, aad)
    }
}