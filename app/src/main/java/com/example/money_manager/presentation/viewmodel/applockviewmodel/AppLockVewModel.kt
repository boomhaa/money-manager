package com.example.money_manager.presentation.viewmodel.applockviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.money_manager.data.store.AppLockManager
import com.example.money_manager.data.store.BiometricAuth
import com.example.money_manager.domain.model.AppLockEffect
import com.example.money_manager.domain.repository.AppLockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppLockVewModel @Inject constructor(
    private val repository: AppLockRepository,
    private val lockManager: AppLockManager,
    private val biometricAuth: BiometricAuth
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppLockUiState())
    val uiState: StateFlow<AppLockUiState> = _uiState.asStateFlow()

    private val _effects = Channel<AppLockEffect>(Channel.BUFFERED)
    val effects: Flow<AppLockEffect> = _effects.receiveAsFlow()

    private val pinBuffer = StringBuilder()
    private var cooldownJobActive = false

    init {
        viewModelScope.launch {
            combine(
                repository.biometricEnable,
                flowOf(biometricAuth.canUseBiometric())
            ) { enabled, deviceCapable ->
                _uiState.update {
                    it.copy(
                        biometricEnabled = enabled,
                        canUseBiometrics = enabled && deviceCapable
                    )
                }
            }.collect()
        }
    }

    private fun unlock() {
        lockManager.unlock()
        viewModelScope.launch { _effects.send(AppLockEffect.UnlockSuccess) }
        pinBuffer.clear()
        _uiState.update { it.copy(isLoading = false, pinLength = 0, error = null) }
    }

    private fun startCooldown() {
        if (cooldownJobActive) return
        cooldownJobActive = true
        viewModelScope.launch {
            var left = COOLDOWN_SEC
            while (left > 0) {
                _uiState.update { it.copy(cooldownLeftSec = left) }
                delay(1000)
                left--
            }
            _uiState.update {
                it.copy(
                    cooldownLeftSec = 0,
                    attemptsLeft = MAX_ATTEMPTS,
                    error = null
                )
            }
            cooldownJobActive = false
        }
    }

    fun onBackspace() {
        if (_uiState.value.isLoading || _uiState.value.cooldownLeftSec > 0) return
        if (pinBuffer.isNotEmpty()) {
            pinBuffer.deleteAt(pinBuffer.lastIndex)
            _uiState.update {
                it.copy(
                    pinLength = pinBuffer.length,
                    error = null
                )
            }
        }
    }

    fun onClearAll() {
        if (_uiState.value.isLoading) return
        pinBuffer.clear()
        _uiState.update {
            it.copy(
                pinLength = 0,
                error = null
            )
        }
    }

    fun onBiometricClick() {
        if (_uiState.value.canUseBiometrics && !_uiState.value.isLoading && _uiState.value.cooldownLeftSec == 0) {
            viewModelScope.launch {
                _effects.send(AppLockEffect.TriggerBiometric)
            }
        }
    }

    fun onBiometricSuccess() {
        unlock()
    }

    fun onBiometricFallback(){
        _uiState.update {
            it.copy(
                error = "Биометрия отменена. Введите PIN"
            )
        }
    }

    fun onBiometricError(msg: String?) {
        viewModelScope.launch {
            _effects.send(AppLockEffect.ErrorHaptic)
        }
        _uiState.update {
            it.copy(
                error = "Ошибка биометрии: ${msg?.takeIf { it.isNotBlank() } ?: "неизвестно"}"
            )
        }
    }

    private fun verifyPin() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val ok = repository.checkPin(pinBuffer.toString().toCharArray())
            if (ok) {
                unlock()
            } else {
                val attemptsLeft = (_uiState.value.attemptsLeft - 1).coerceAtLeast(0)
                _effects.send(AppLockEffect.ErrorHaptic)
                if (attemptsLeft == 0) {
                    startCooldown()
                }
                pinBuffer.clear()
                _uiState.update {
                    it.copy(
                        attemptsLeft = attemptsLeft,
                        isLoading = false,
                        pinLength = 0,
                        error = if (attemptsLeft == 0) {
                            "Количество попыток закончилось. Подождите $COOLDOWN_SEC с."
                        } else {
                            "Неверный PIN. Осталось $attemptsLeft попыток"
                        }
                    )
                }
            }
        }
    }

    fun onDigit(digit: Int) {
        if (_uiState.value.isLoading || _uiState.value.cooldownLeftSec > 0) return
        if (pinBuffer.length >= PIN_LENGTH) return
        pinBuffer.append(digit)
        _uiState.update { it.copy(pinLength = pinBuffer.length, error = null) }

        if (pinBuffer.length == PIN_LENGTH) {
            verifyPin()
        }
    }
}