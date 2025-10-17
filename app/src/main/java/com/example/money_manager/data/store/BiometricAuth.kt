package com.example.money_manager.data.store

import android.content.Context
import android.os.Build

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class BiometricAuth @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun canUseBiometric(): Boolean{
        val mgr = BiometricManager.from(context)
        val res = mgr.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
        return res == BiometricManager.BIOMETRIC_SUCCESS
    }

    fun prompt(
        fragmentActivity: FragmentActivity,
        title: String = "Вход по биометрии",
        onSuccess: () -> Unit,
        onFallbackToPin: () -> Unit,
        onError: (String) -> Unit
    ){
        val executor = ContextCompat.getMainExecutor(fragmentActivity)
        val callback = object : BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                onSuccess()
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) onFallbackToPin()
                else onError(errString.toString())
            }
        }

        val prompt = BiometricPrompt(fragmentActivity, executor, callback)

        val info = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setSubtitle("Используйте отпечаток/Face ID")
            .setConfirmationRequired(false)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                    setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
                }else{
                    @Suppress("DEPRECATION")
                    setDeviceCredentialAllowed(true)
                }
                setNegativeButtonText("Ввести PIN")
            }
            .build()
        prompt.authenticate(info)
    }
}