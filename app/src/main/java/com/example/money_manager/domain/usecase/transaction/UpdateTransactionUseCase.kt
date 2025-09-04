package com.example.money_manager.domain.usecase.transaction

import com.example.money_manager.domain.model.Transaction
import com.example.money_manager.domain.repository.TransactionRepository
import com.example.money_manager.domain.usecase.UseCase
import javax.inject.Inject

class UpdateTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
): UseCase<Transaction, Unit> {
    override suspend fun invoke(params: Transaction) {
        repository.updateTransaction(params)
    }
}