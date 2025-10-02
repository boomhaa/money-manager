package com.example.money_manager.presentation.viewmodel.authviewmodel

import com.example.money_manager.domain.model.User

data class AuthUiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val isGuest: Boolean = false,
    val error: String? = null
)