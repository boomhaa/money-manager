package com.example.money_manager.domain.usecase.firebase.utlis

import com.example.money_manager.domain.repository.FirebaseTransactionRepository
import javax.inject.Inject

class RemoveTransactionListenerFirebaseUseCase @Inject constructor(
    private val firebaseTransactionRepository: FirebaseTransactionRepository
) {
    fun execute(){
        firebaseTransactionRepository.removeListener()
    }
}