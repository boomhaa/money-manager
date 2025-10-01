package com.example.money_manager.presentation.viewmodel.authviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.money_manager.domain.repository.AuthRepository
import com.example.money_manager.utils.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val prefs: PreferencesManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    init {

        val wasGuest = prefs.isGuest
        _uiState.value = _uiState.value.copy(
            isGuest = wasGuest,
            isLoading = false
        )

        viewModelScope.launch {
            authRepository.currentUserFlow.collectLatest { user ->
                _uiState.value = _uiState.value.copy(
                    user = user,
                    isLoading = false,
                    error = if (user != null) null else _uiState.value.error,
                    isGuest = wasGuest
                )
            }
        }
    }

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )
            val result = authRepository.singInWithGoogle(idToken)
            if (result.isFailure) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = result.exceptionOrNull()?.message
                )
            }else {
                prefs.isGuest = false
                prefs.isAuthorized = true
            }
        }
    }

    fun continueAsGuest() {
        _uiState.value = _uiState.value.copy(
            user = null,
            isGuest = true,
            isLoading = false,
            error = null
        )
        prefs.isGuest = true
    }

    fun signOut() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, isGuest = true)
            authRepository.signOut()
            prefs.isGuest = true
            prefs.isAuthorized = false
        }
    }

    fun cleanError() {
        _uiState.value = _uiState.value.copy(error = null)
    }


}