package com.example.money_manager.domain.model

data class Category(
    val id: Long = 0,
    val name: String,
    val isDefault: Boolean = false
)