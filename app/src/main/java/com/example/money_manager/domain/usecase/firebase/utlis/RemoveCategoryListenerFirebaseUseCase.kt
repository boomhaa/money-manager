package com.example.money_manager.domain.usecase.firebase.utlis

import com.example.money_manager.domain.repository.FirebaseCategoryRepository
import javax.inject.Inject

class RemoveCategoryListenerFirebaseUseCase @Inject constructor(
    private val firebaseCategoryRepository: FirebaseCategoryRepository
) {
    fun execute(){
        firebaseCategoryRepository.removeListener()
    }
}