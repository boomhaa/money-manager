package com.example.money_manager.presentation.viewmodel.homeviewmodel

import com.example.money_manager.domain.model.Currency
import com.example.money_manager.domain.model.TransactionWithCategory

data class HomeUiState(
    val transactions: List<TransactionWithCategory> = emptyList(),
    val totalIncome: Double = 0.0,
    val totalExpense: Double = 0.0,
    val balance: Double = 0.0,
    val isLoading: Boolean = false,
    val error: String? = null
)