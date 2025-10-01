package com.example.money_manager.presentation.viewmodel.authviewmodel

import com.example.money_manager.domain.model.User

data class AuthUiState(
    val user: User? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
