package com.example.money_manager.domain.model

import com.example.money_manager.utils.TransactionType
import java.time.LocalDateTime

data class Transaction(
    val id: Long = 0,
    val amount: Double,
    val type: TransactionType,
    val categoryId: Long,
    val date: LocalDateTime,
    val description: String? = null,
    val timestamp: LocalDateTime = LocalDateTime.now()
)