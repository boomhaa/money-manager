package com.example.money_manager.presentation.viewmodel.applockviewmodel

private const val PIN_LENGTH = 4
private const val MAX_ATTEMPTS = 5
private const val COOLDOWN_SEC = 30

data class AppLockUiState(
    val pinLength: Int = 0,
    val pinTotal: Int = PIN_LENGTH,
    val isLoading: Boolean = false,
    val error: String? = null,
    val attemptsLeft: Int = MAX_ATTEMPTS,
    val canUseBiometrics: Boolean = false,
    val biometricEnabled: Boolean = false
)
