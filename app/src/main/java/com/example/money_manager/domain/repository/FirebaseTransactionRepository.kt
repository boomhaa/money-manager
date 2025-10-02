package com.example.money_manager.domain.repository

import com.example.money_manager.domain.model.FirebaseTransaction

interface FirebaseTransactionRepository {
    suspend fun insertTransactionFirebase(transaction: FirebaseTransaction)
    suspend fun updateTransactionFirebase(transaction: FirebaseTransaction)
    suspend fun deleteTransactionFirebase(transaction: FirebaseTransaction)
    suspend fun getAllTransactionsFirebase(userId: String): List<FirebaseTransaction>
    fun observeTransactions()
    fun removeListener()
}