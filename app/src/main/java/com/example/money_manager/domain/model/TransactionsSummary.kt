package com.example.money_manager.domain.model

data class TransactionsSummary(
    val transactionsWithCategories: List<TransactionWithCategory>,
    val totalIncome: Double,
    val totalExpense: Double,
    val balance: Double
)
