package com.example.money_manager.domain.model

import com.example.money_manager.utils.TransactionType
import java.time.LocalDateTime

data class EditTransactionUiState(
    val transaction: Transaction? = null,
    val amount: String = "",
    val description: String = "",
    val transactionType: TransactionType = TransactionType.EXPENSE,
    val selectedCategory: Category? = null,
    val date: LocalDateTime = LocalDateTime.now(),
    val categories: List<Category> = emptyList(),
    val error: String? = null,
    val isSuccess: Boolean = false,
    val isLoading: Boolean = true
)
