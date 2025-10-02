package com.example.money_manager.domain.model

import com.example.money_manager.utils.TransactionType

data class FirebaseCategory(
    val id: Long = 0,
    val globalId: String = "",
    val name: String = "",
    val type: String = TransactionType.EXPENSE.name,
    val isDefault: Boolean = false,
    val iconName: String? = null
)
