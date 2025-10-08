package com.example.money_manager.domain.usecase.currency

import com.example.money_manager.domain.model.Currency
import com.example.money_manager.domain.repository.CurrenciesRepository
import com.example.money_manager.domain.usecase.NoParamUseCase
import javax.inject.Inject

class GetAllCurrenciesUseCase @Inject constructor(
    private val repository: CurrenciesRepository
): NoParamUseCase<List<Currency>> {
    override suspend operator fun invoke(): List<Currency> {
        return repository.getAllCurrencies()
    }
}