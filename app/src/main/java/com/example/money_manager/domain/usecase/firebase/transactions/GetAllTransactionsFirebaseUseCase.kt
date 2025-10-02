package com.example.money_manager.domain.usecase.firebase.transactions

import com.example.money_manager.domain.model.FirebaseTransaction
import com.example.money_manager.domain.repository.FirebaseTransactionRepository

import com.example.money_manager.domain.usecase.UseCase
import javax.inject.Inject

class GetAllTransactionsFirebaseUseCase @Inject constructor(
    private val firebaseTransactionRepository: FirebaseTransactionRepository
): UseCase<String, List<FirebaseTransaction>> {
    override suspend fun invoke(params: String): List<FirebaseTransaction> {
        return firebaseTransactionRepository.getAllTransactionsFirebase(params)
    }
}