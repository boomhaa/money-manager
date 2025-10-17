package com.example.money_manager.domain.model

sealed interface AppLockEffect{
    data object UnlockSuccess: AppLockEffect
    data object TriggerBiometric: AppLockEffect
    data object ErrorHaptic: AppLockEffect
}