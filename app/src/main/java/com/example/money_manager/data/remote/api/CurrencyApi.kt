package com.example.money_manager.data.remote.api

import com.example.money_manager.data.remote.dto.CurrencyResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi{
    @GET("convert")
    suspend fun convertCurrency(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Double,
    ): CurrencyResponseDto
}