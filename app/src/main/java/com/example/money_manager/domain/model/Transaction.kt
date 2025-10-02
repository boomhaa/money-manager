package com.example.money_manager.domain.model

import com.example.money_manager.utils.TransactionType

import java.time.LocalDateTime

data class Transaction(
    val id: Long = 0,
    val globalId: String = "",
    val amount: Double = 0.0,
    val type: TransactionType = TransactionType.EXPENSE,
    val categoryId: Long = 0,
    val date: LocalDateTime = LocalDateTime.now(),
    val description: String? = null,
    val timestamp: LocalDateTime = LocalDateTime.now()
)