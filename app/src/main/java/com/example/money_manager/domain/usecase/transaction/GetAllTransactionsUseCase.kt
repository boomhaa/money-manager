package com.example.money_manager.domain.usecase.transaction

import com.example.money_manager.domain.model.Transaction
import com.example.money_manager.domain.repository.TransactionRepository
import com.example.money_manager.domain.usecase.NoParamFlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
): NoParamFlowUseCase<List<Transaction>> {
    override fun invoke(): Flow<List<Transaction>> {
        return repository.getAllTransactions()
    }
}