package com.example.money_manager.domain.usecase.firebase.transactions

import com.example.money_manager.domain.repository.FirebaseTransactionRepository
import com.example.money_manager.domain.usecase.NoParamUseCase
import javax.inject.Inject

class ObserveTransactionsFirebaseUseCase @Inject constructor(
    private val firebaseTransactionRepository: FirebaseTransactionRepository
): NoParamUseCase<Unit> {
    override suspend operator fun invoke() {
        firebaseTransactionRepository.observeTransactions()
    }
}