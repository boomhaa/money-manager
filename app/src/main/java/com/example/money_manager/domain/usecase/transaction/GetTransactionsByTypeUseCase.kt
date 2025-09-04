package com.example.money_manager.domain.usecase.transaction

import com.example.money_manager.domain.model.Transaction
import com.example.money_manager.domain.repository.TransactionRepository
import com.example.money_manager.domain.usecase.FLowUseCase
import com.example.money_manager.utils.TransactionType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTransactionsByTypeUseCase @Inject constructor(
    private val repository: TransactionRepository
): FLowUseCase<TransactionType, List<Transaction>> {
    override fun invoke(params: TransactionType): Flow<List<Transaction>> {
        return repository.getTransactionsByType(params)
    }
}