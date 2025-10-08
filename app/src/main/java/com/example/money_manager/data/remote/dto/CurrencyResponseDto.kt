package com.example.money_manager.data.remote.dto

data class CurrencyResponseDto(
    val status: String,
    val from: String? = null,
    val to: String? = null,
    val amount: Double? = null,
    val result: Double? = null,
    val message: String? = null
)
