package com.example.money_manager.utils.helpers

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

sealed interface BiometricStatus {
    data object Available : BiometricStatus
    data object NoHardware : BiometricStatus
    data object HardwareUnavailable : BiometricStatus
    data object NoneEnrolled : BiometricStatus
    data object SecurityUpdateRequired : BiometricStatus
    data class Error(val message: String) : BiometricStatus
}

fun checkBiometricStatus(ctx: Context): BiometricStatus {
    val bm = BiometricManager.from(ctx)
    return when (bm.canAuthenticate(
        BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
    )) {
        BiometricManager.BIOMETRIC_SUCCESS -> BiometricStatus.Available
        BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> BiometricStatus.NoHardware
        BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> BiometricStatus.HardwareUnavailable
        BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> BiometricStatus.NoneEnrolled
        BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> BiometricStatus.SecurityUpdateRequired
        else -> BiometricStatus.Error("Неизвестная ошибка биометрии")
    }
}

fun launchBiometricPrompt(
    activity: FragmentActivity,
    title: String = "Вход по биометрии",
    onSuccess: () -> Unit,
    onFallbackToPin: (String) -> Unit,
    onError: (String) -> Unit
) {
    try {
        val executor = ContextCompat.getMainExecutor(activity)
        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                onSuccess()
            }
            override fun onAuthenticationError(code: Int, errString: CharSequence) {
                if (code == BiometricPrompt.ERROR_NEGATIVE_BUTTON ||
                    code == BiometricPrompt.ERROR_CANCELED) {
                    onFallbackToPin("Отменено пользователем")
                    return
                }
                val msg = when (code) {
                    BiometricPrompt.ERROR_LOCKOUT -> "Слишком много попыток. Попробуйте позже."
                    BiometricPrompt.ERROR_LOCKOUT_PERMANENT -> "Биометрия временно заблокирована."
                    BiometricPrompt.ERROR_NO_BIOMETRICS -> "На устройстве не настроена биометрия."
                    BiometricPrompt.ERROR_NO_DEVICE_CREDENTIAL -> "Не настроен системный PIN/пароль."
                    else -> errString.toString()
                }
                onError(msg)
            }
        }
        val prompt = BiometricPrompt(activity, executor, callback)

        val info = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setSubtitle("Используйте отпечаток")
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    setAllowedAuthenticators(
                        BiometricManager.Authenticators.BIOMETRIC_STRONG or
                                BiometricManager.Authenticators.DEVICE_CREDENTIAL
                    )
                } else {
                    @Suppress("DEPRECATION")
                    setDeviceCredentialAllowed(true)
                }
            }
            .build()

        prompt.authenticate(info)
    } catch (t: Throwable) {
        onError(t.message ?: t.toString())
    }
}

@RequiresApi(Build.VERSION_CODES.R)
fun buildEnrollIntent(): Intent =
    Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
        putExtra(
            Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
            BiometricManager.Authenticators.BIOMETRIC_STRONG
        )
    }