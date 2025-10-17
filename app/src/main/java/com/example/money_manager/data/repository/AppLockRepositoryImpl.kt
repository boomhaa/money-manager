package com.example.money_manager.data.repository

import com.example.money_manager.data.store.AppLockStore
import com.example.money_manager.data.store.PinHasher
import com.example.money_manager.domain.repository.AppLockRepository
import javax.inject.Inject

class AppLockRepositoryImpl @Inject constructor(
    private val store: AppLockStore,
    private val pinHasher: PinHasher,
): AppLockRepository {
    override val lockEnable = store.isLockEnabled
    override val biometricEnable = store.isBiometricEnabled
    override val timeoutSec = store.lockTimeoutSec

    override suspend fun setPin(pin: CharArray) {
        val result = pinHasher.hash(pin)
        store.savePinHash(result.hash, result.salt, result.iter)
    }

    override suspend fun disableLock() {
        store.setLockEnable(false)
    }

    override suspend fun checkPin(pin: CharArray): Boolean {
       val meta = store.loadPinMeta() ?: return false
        return pinHasher.verify(pin, meta.first, meta.second, meta.third)
    }

    override suspend fun setBiometricEnabled(state: Boolean) {
        store.setBiometricEnable(state)
    }

    override suspend fun setTimeout(seconds: Int) {
        store.setTimeoutSec(seconds)
    }
}