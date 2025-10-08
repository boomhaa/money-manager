package com.example.money_manager.domain.model

import com.example.money_manager.utils.TransactionType

data class FirebaseTransaction(
    val id: Long = 0,
    val globalId: String = "",
    val amount: Double = 0.0,
    val type: String = TransactionType.EXPENSE.name,
    val currencyCode: String = "RUB",
    val categoryId: Long = 0,
    val date: Long = System.currentTimeMillis(),
    val description: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)
