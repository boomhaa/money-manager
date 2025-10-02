package com.example.money_manager.domain.usecase.firebase.transactions

import com.example.money_manager.data.mapper.toDomain
import com.example.money_manager.data.mapper.toFirebaseDto
import com.example.money_manager.domain.repository.FirebaseTransactionRepository
import com.example.money_manager.domain.repository.TransactionRepository
import com.example.money_manager.domain.usecase.NoParamUseCase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SyncTransactionsUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val firebaseTransactionRepository: FirebaseTransactionRepository
) : NoParamUseCase<Unit> {
    override suspend operator fun invoke() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        val localTransactions = transactionRepository.getAllTransactions().first()
        val remoteTransactions =
            firebaseTransactionRepository.getAllTransactionsFirebase(userId).map { it.toDomain() }

        val remoteIds = remoteTransactions.map { it.globalId }.toSet()

        localTransactions.forEach {
            if (it.globalId !in remoteIds) {
                firebaseTransactionRepository.insertTransactionFirebase(it.toFirebaseDto())
            } else {
                firebaseTransactionRepository.updateTransactionFirebase(it.toFirebaseDto())
            }
        }

        val localIds = localTransactions.map { it.globalId }

        remoteTransactions.forEach {
            if (it.globalId !in localIds) {
                transactionRepository.insertTransaction(it)
            } else {
                transactionRepository.updateTransaction(it)
            }
        }
    }
}