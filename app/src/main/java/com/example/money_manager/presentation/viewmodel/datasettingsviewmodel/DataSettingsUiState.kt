package com.example.money_manager.presentation.viewmodel.datasettingsviewmodel

import com.example.money_manager.domain.model.Currency

data class DataSettingsUiState(
    val selectedCurrency: Currency? = null,
    val currencies: List<Currency> = emptyList(),
    val isLoading: Boolean = false,
    val loadingText: String = ""
)