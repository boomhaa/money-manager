package com.example.money_manager.presentation.viewmodel.applockviewmodel

const val PIN_LENGTH = 4
const val MAX_ATTEMPTS = 5
const val COOLDOWN_SEC = 30

data class AppLockUiState(
    val pinLength: Int = 0,
    val pinTotal: Int = PIN_LENGTH,
    val isLoading: Boolean = false,
    val error: String? = null,
    val attemptsLeft: Int = MAX_ATTEMPTS,
    val cooldownLeftSec: Int = 0,
    val canUseBiometrics: Boolean = false,
    val biometricEnabled: Boolean = false
)
