package com.example.money_manager.domain.usecase.firebase.transactions

import com.example.money_manager.domain.model.FirebaseTransaction

import com.example.money_manager.domain.repository.FirebaseTransactionRepository
import com.example.money_manager.domain.usecase.UseCase
import javax.inject.Inject

class DeleteTransactionFirebaseUseCase @Inject constructor(
    private val firebaseTransactionRepository: FirebaseTransactionRepository
): UseCase<FirebaseTransaction, Unit>{
    override suspend operator fun invoke(params: FirebaseTransaction) {
        firebaseTransactionRepository.deleteTransactionFirebase(params)
    }
}