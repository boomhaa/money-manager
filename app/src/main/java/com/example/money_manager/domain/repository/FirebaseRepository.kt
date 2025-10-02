package com.example.money_manager.domain.repository

import com.example.money_manager.domain.model.FirebaseTransaction
import com.example.money_manager.domain.model.Transaction

interface FirebaseRepository {
    suspend fun insertTransaction(transaction: FirebaseTransaction)
    suspend fun getAllTransactions(userId: String): List<FirebaseTransaction>
}