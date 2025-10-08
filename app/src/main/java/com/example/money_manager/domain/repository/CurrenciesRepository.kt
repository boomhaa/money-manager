package com.example.money_manager.domain.repository

import com.example.money_manager.domain.model.Currency

interface CurrenciesRepository {
    suspend fun convertCurrency(from: String, to: String, amount: Double): Result<Double>
    fun getAllCurrencies(): List<Currency>
}