package com.example.money_manager.domain.model

import com.example.money_manager.utils.TransactionType

data class Category(
    val id: Long = 0,
    val name: String,
    val type: TransactionType,
    val isDefault: Boolean = false
)