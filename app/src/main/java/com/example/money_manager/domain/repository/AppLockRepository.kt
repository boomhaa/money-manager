package com.example.money_manager.domain.repository

import kotlinx.coroutines.flow.Flow

interface AppLockRepository {
    val lockEnable: Flow<Boolean>
    val biometricEnable: Flow<Boolean>
    val timeoutSec: Flow<Int>

    suspend fun setPin(pin: CharArray)
    suspend fun disableLock()
    suspend fun checkPin(pin: CharArray): Boolean
    suspend fun setBiometricEnabled(state: Boolean)
    suspend fun setTimeout(seconds: Int)
}