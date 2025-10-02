package com.example.money_manager.domain.usecase.firebase.transactions

import com.example.money_manager.domain.repository.FirebaseRepository
import com.example.money_manager.domain.usecase.NoParamUseCase
import javax.inject.Inject

class ObserveTransactionsFirebaseUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
): NoParamUseCase<Unit> {
    override suspend operator fun invoke() {
        firebaseRepository.observeTransactions()
    }
}