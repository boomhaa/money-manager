package com.example.money_manager.domain.usecase.firebase.categories

import com.example.money_manager.data.mapper.toDomain
import com.example.money_manager.data.mapper.toFirebaseDto
import com.example.money_manager.domain.repository.CategoryRepository
import com.example.money_manager.domain.repository.FirebaseCategoryRepository
import com.example.money_manager.domain.usecase.NoParamUseCase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SyncCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val firebaseCategoryRepository: FirebaseCategoryRepository
) : NoParamUseCase<Unit> {
    override suspend operator fun invoke() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        val localCategories = categoryRepository.getAllCategories().first()
        val remoteCategories =
            firebaseCategoryRepository.getAllCategoriesFirebase(userId).map { it.toDomain() }

        val remoteIds = remoteCategories.map { it.globalId }.toSet()

        localCategories.forEach {
            if (it.globalId !in remoteIds) {
                firebaseCategoryRepository.insertCategoryFirebase(it.toFirebaseDto())
            } else {
                firebaseCategoryRepository.updateCategoryFirebase(it.toFirebaseDto())
            }
        }

        val localIds = localCategories.map { it.globalId }

        remoteCategories.forEach {
            if (it.globalId !in localIds) {
                categoryRepository.insertCategory(it)
            } else {
                categoryRepository.updateCategory(it)
            }
        }
    }
}