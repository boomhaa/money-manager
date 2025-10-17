package com.example.money_manager.presentation.viewmodel.applockgatehostviewmodel

import androidx.lifecycle.ViewModel
import com.example.money_manager.data.store.AppLockManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AppLockGateHostViewModel @Inject constructor(
    private val lockManager: AppLockManager
) : ViewModel() {
    val locked: StateFlow<Boolean> = lockManager.locked
}