package com.example.money_manager.presentation.viewmodel.securitysettingsviewmodel

import com.example.money_manager.utils.PinStage

data class SecuritySettingsUiState(
    val lockEnabled: Boolean = false,
    val biometricEnabled: Boolean = false,
    val deviceBiometricCapable: Boolean = false,
    val timeoutSec: Int = 0,
    val showPinSheet: Boolean = false,
    val pinStage: PinStage = PinStage.None,
    val pinLength: Int = 0,
    val pinError: String? = null,
    val busy: Boolean = false
)
