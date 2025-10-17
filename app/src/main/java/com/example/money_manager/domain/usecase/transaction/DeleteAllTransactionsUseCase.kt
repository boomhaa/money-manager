package com.example.money_manager.domain.usecase.transaction

import com.example.money_manager.domain.repository.TransactionRepository
import com.example.money_manager.domain.usecase.NoParamUseCase
import javax.inject.Inject

class DeleteAllTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
): NoParamUseCase<Unit> {
    override suspend fun invoke() {
        repository.deleteAllTransactions()
    }
}