package com.example.money_manager.presentation.viewmodel.securitysettingsviewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.money_manager.data.store.BiometricAuth
import com.example.money_manager.domain.repository.AppLockRepository
import com.example.money_manager.presentation.viewmodel.applockviewmodel.PIN_LENGTH
import com.example.money_manager.utils.PinStage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecuritySettingsViewModel @Inject constructor(
    private val repository: AppLockRepository,
    private val biometricAuth: BiometricAuth
) : ViewModel() {
    private val _uiState = MutableStateFlow(SecuritySettingsUiState())
    val uiState: StateFlow<SecuritySettingsUiState> = _uiState.asStateFlow()

    private var pinBuffer = StringBuilder()
    private var newPin = ""
    private var currentPinChecked = false

    init {
        viewModelScope.launch {
            combine(
                repository.lockEnable,
                repository.biometricEnable,
                repository.timeoutSec
            ) { lock, bio, timeout ->
                SecuritySettingsUiState(
                    lockEnabled = lock,
                    biometricEnabled = bio,
                    deviceBiometricCapable = biometricAuth.canUseBiometric(),
                    timeoutSec = timeout,
                    showPinSheet = _uiState.value.showPinSheet,
                    pinStage = _uiState.value.pinStage,
                    pinLength = _uiState.value.pinLength,
                    pinError = _uiState.value.pinError,
                    busy = _uiState.value.busy
                )
            }.collect { _uiState.value = it }
        }
    }

    fun onToggleLock(desired: Boolean) {
        if (desired) {
            openPinSheet(
                stage = PinStage.EnterNew,
                clearBuffers = true
            )
        } else {
            viewModelScope.launch {
                repository.disableLock()
                repository.setBiometricEnabled(false)
            }
        }
    }

    fun onChangePin() {
        if (!_uiState.value.lockEnabled) {
            openPinSheet(stage = PinStage.EnterNew, clearBuffers = true)
        } else {
            openPinSheet(stage = PinStage.EnterCurrent, clearBuffers = true)
        }
    }

    fun onBiometricToggle(desired: Boolean) {
        if (!_uiState.value.deviceBiometricCapable) return
        viewModelScope.launch {
            repository.setBiometricEnabled(desired && _uiState.value.lockEnabled)
        }
    }

    fun onTimeOutSelected(sec: Int) {
        viewModelScope.launch { repository.setTimeout(sec) }
    }

    fun onDigit(digit: Int){
        if (!_uiState.value.showPinSheet || _uiState.value.busy || _uiState.value.pinStage == PinStage.None) return
        if (pinBuffer.length >= PIN_LENGTH) return

        pinBuffer.append(digit)
        _uiState.update { it.copy(pinLength = pinBuffer.length, pinError = null) }

        if (pinBuffer.length == PIN_LENGTH){
            when (_uiState.value.pinStage){
                PinStage.EnterCurrent -> verifyCurrentPin()
                PinStage.EnterNew ->  captureNewPinAndAskConfirm()
                PinStage.ConfirmNew -> confirmNewPin()
                else -> Unit
            }
        }
    }

    fun onBackspace(){
        if (!_uiState.value.showPinSheet || _uiState.value.busy) return
        if (pinBuffer.isNotEmpty()){
            pinBuffer.deleteAt(pinBuffer.lastIndex)
            _uiState.update { it.copy(pinLength = pinBuffer.length, pinError = null) }
        }
    }

    fun onClearAll(){
        if (!_uiState.value.showPinSheet || _uiState.value.busy) return
        pinBuffer.clear()
        _uiState.update { it.copy(pinLength = 0, pinError = null) }
    }

    fun closePinSheet() {
        _uiState.update {
            it.copy(showPinSheet = false, pinStage = PinStage.None, pinError = null, pinLength = 0)
        }
        pinBuffer.clear()
        newPin = ""
        currentPinChecked = false
    }

    private fun openPinSheet(stage: PinStage, clearBuffers: Boolean) {
        if (clearBuffers) {
            pinBuffer.clear()
            newPin = ""
            currentPinChecked = false
        }
        _uiState.update {
            it.copy(
                showPinSheet = true,
                pinStage = stage,
                pinLength = 0,
                pinError = null
            )
        }
    }

    private fun verifyCurrentPin() {
        viewModelScope.launch {
            _uiState.update { it.copy(busy = true) }
            val ok = repository.checkPin(pinBuffer.toString().toCharArray())
            if (ok) {
                currentPinChecked = true
                pinBuffer.clear()
                _uiState.update { it.copy(busy = false) }
                openPinSheet(stage = PinStage.EnterNew, clearBuffers = true)
            } else {
                pinBuffer.clear()
                _uiState.update {
                    it.copy(
                        busy = false,
                        pinLength = 0,
                        pinError = "Неверный текущий PIN"
                    )
                }
            }
        }
    }

    private fun captureNewPinAndAskConfirm() {
        newPin = pinBuffer.toString()
        pinBuffer.clear()
        _uiState.update { it.copy(pinLength = 0) }
        openPinSheet(stage = PinStage.ConfirmNew, clearBuffers = false)
    }

    private fun confirmNewPin() {
        val confirm = pinBuffer.toString()
        if (confirm != newPin) {
            pinBuffer.clear()
            _uiState.update {
                it.copy(
                    pinLength = 0,
                    pinError = "PIN не совпадает! Повторите попытку."
                )
            }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(busy = true) }
            Log.d("SecuritySettingsViewModel", "Set new password")
            repository.setPin(newPin.toCharArray())
            _uiState.update { it.copy(busy = false) }
            closePinSheet()
        }
    }
}