package com.example.money_manager.domain.usecase.firebase.categories

import com.example.money_manager.domain.model.FirebaseCategory
import com.example.money_manager.domain.repository.FirebaseCategoryRepository
import com.example.money_manager.domain.usecase.UseCase
import javax.inject.Inject

class GetAllCategoriesFirebaseUseCase @Inject constructor(
    private val firebaseCategoryRepository: FirebaseCategoryRepository
): UseCase<String, List<FirebaseCategory>> {
    override suspend fun invoke(params: String): List<FirebaseCategory> {
        return firebaseCategoryRepository.getAllCategoriesFirebase(params)
    }
}