package com.example.money_manager.domain.usecase.firebase.transactions

import com.example.money_manager.data.mapper.toDomain
import com.example.money_manager.domain.repository.FirebaseTransactionRepository
import com.example.money_manager.domain.repository.TransactionRepository
import com.example.money_manager.domain.usecase.NoParamUseCase
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class SyncTransactionsUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val firebaseTransactionRepository: FirebaseTransactionRepository
) : NoParamUseCase<Unit> {
    override suspend operator fun invoke() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        transactionRepository.deleteAllTransactions()

        val remoteTransactions =
            firebaseTransactionRepository.getAllTransactionsFirebase(userId).map { it.toDomain() }

        remoteTransactions.forEach {
            transactionRepository.insertTransaction(it)
        }
    }
}