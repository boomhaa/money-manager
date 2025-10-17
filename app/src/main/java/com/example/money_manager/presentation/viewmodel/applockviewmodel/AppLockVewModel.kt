package com.example.money_manager.presentation.viewmodel.applockviewmodel

import androidx.lifecycle.ViewModel
import com.example.money_manager.data.store.AppLockManager
import com.example.money_manager.data.store.BiometricAuth
import com.example.money_manager.domain.repository.AppLockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AppLockVewModel @Inject constructor(
    private val repository: AppLockRepository,
    private val lockManager: AppLockManager,
    private val biometricAuth: BiometricAuth
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppLockUiState())
    val uiState: StateFlow<AppLockUiState> = _uiState.asStateFlow()
}