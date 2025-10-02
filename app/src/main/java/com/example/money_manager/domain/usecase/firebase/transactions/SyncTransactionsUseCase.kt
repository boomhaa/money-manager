package com.example.money_manager.domain.usecase.firebase.transactions

import com.example.money_manager.data.mapper.toDomain
import com.example.money_manager.data.mapper.toFirebaseDto
import com.example.money_manager.domain.repository.FirebaseRepository
import com.example.money_manager.domain.repository.TransactionRepository
import com.example.money_manager.domain.usecase.NoParamUseCase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SyncTransactionsUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val firebaseRepository: FirebaseRepository
) : NoParamUseCase<Unit> {
    override suspend operator fun invoke() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        val localTransactions = transactionRepository.getAllTransactions().first()
        val remoteTransactions = firebaseRepository.getAllTransactionsFirebase(userId).map { it.toDomain() }

        val remoteIds = remoteTransactions.map { it.globalId }.toSet()
        val newTransactionsToRemote = localTransactions.filter { it.globalId !in remoteIds }
        newTransactionsToRemote.forEach { firebaseRepository.insertTransactionFirebase(it.toFirebaseDto()) }

        val localIds = localTransactions.map { it.globalId }
        val newTransactionsToLocal = remoteTransactions.filter { it.globalId !in localIds }
        newTransactionsToLocal.forEach { transactionRepository.insertTransaction(it) }
    }
}