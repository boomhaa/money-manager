package com.example.money_manager.data.repository

import android.content.Context
import com.example.money_manager.data.remote.api.CurrencyApi
import com.example.money_manager.domain.model.Currency
import com.example.money_manager.domain.repository.CurrenciesRepository
import kotlinx.serialization.json.Json
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val currencyApi: CurrencyApi,
    private val context: Context
) : CurrenciesRepository {
    override suspend fun convertCurrency(
        from: String,
        to: String,
        amount: Double
    ): Result<Double> {
        return try {
            val response = currencyApi.convertCurrency(from, to, amount)
            if (response.status == "success" && response.result != null) {
                Result.success(response.result)
            } else {
                Result.failure(Exception(response.message ?: "Conversion failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getAllCurrencies(): List<Currency> {
        return try {
            val jsonString = context.assets.open("currencies.json").bufferedReader().use { it.readText() }
            Json.decodeFromString<List<Currency>>(jsonString)
        }catch (e: Exception){
            emptyList()
        }
    }
}