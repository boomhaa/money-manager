package com.example.money_manager.domain.usecase.firebase.utlis

import com.example.money_manager.domain.repository.FirebaseRepository
import javax.inject.Inject

class RemoveTransactionListenerFirebaseUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {
    fun execute(){
        firebaseRepository.removeListener()
    }
}