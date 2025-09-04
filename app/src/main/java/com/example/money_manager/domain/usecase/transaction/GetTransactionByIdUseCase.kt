package com.example.money_manager.domain.usecase.transaction

import com.example.money_manager.domain.model.Transaction
import com.example.money_manager.domain.repository.TransactionRepository
import com.example.money_manager.domain.usecase.UseCase
import javax.inject.Inject

class GetTransactionByIdUseCase @Inject constructor(
    private val repository: TransactionRepository
): UseCase<Long, Transaction>{
    override suspend fun invoke(params: Long): Transaction {
        return repository.getTransactionById(params)
    }
}