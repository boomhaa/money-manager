package com.example.money_manager.domain.model

data class TransactionWithCategory(
    val transaction: Transaction,
    val category: Category
)
