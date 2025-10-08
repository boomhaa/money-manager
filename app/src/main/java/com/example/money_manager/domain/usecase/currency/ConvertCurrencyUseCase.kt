package com.example.money_manager.domain.usecase.currency

import com.example.money_manager.domain.repository.CurrenciesRepository
import com.example.money_manager.domain.usecase.UseCase
import javax.inject.Inject

class ConvertCurrencyUseCase @Inject constructor(
    private val currenciesRepository: CurrenciesRepository
): UseCase<Triple<String, String, Double>, Result<Double>> {
    override suspend operator fun invoke(params: Triple<String, String, Double>): Result<Double> {
        return currenciesRepository.convertCurrency(params.first, params.second, params.third)
    }
}