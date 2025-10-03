package com.example.money_manager.domain.usecase.firebase.categories

import com.example.money_manager.data.mapper.toDomain
import com.example.money_manager.domain.repository.CategoryRepository
import com.example.money_manager.domain.repository.FirebaseCategoryRepository
import com.example.money_manager.domain.usecase.NoParamUseCase
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class SyncCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val firebaseCategoryRepository: FirebaseCategoryRepository
) : NoParamUseCase<Unit> {
    override suspend operator fun invoke() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return



        val remoteCategories =
            firebaseCategoryRepository.getAllCategoriesFirebase(userId).map { it.toDomain() }

        remoteCategories.forEach {
            categoryRepository.insertCategory(it)
        }
    }
}