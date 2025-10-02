package com.example.money_manager.domain.usecase.firebase.categories

import com.example.money_manager.domain.model.FirebaseCategory
import com.example.money_manager.domain.repository.FirebaseCategoryRepository
import com.example.money_manager.domain.usecase.UseCase
import javax.inject.Inject

class InsertCategoryFirebaseUseCase @Inject constructor(
    private val firebaseCategoryRepository: FirebaseCategoryRepository
): UseCase<FirebaseCategory, Unit>{
    override suspend operator fun invoke(params: FirebaseCategory) {
        firebaseCategoryRepository.insertCategoryFirebase(params)
    }
}