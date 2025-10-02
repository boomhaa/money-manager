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

        val localTransactions = categoryRepository.getAllCategories().first()
        val remoteTransactions = firebaseCategoryRepository.getAllCategoriesFirebase(userId).map { it.toDomain() }

        val remoteIds = remoteTransactions.map { it.globalId }.toSet()
        val newTransactionsToRemote = localTransactions.filter { it.globalId !in remoteIds }
        newTransactionsToRemote.forEach { firebaseCategoryRepository.insertCategoryFirebase(it.toFirebaseDto()) }

        val localIds = localTransactions.map { it.globalId }
        val newTransactionsToLocal = remoteTransactions.filter { it.globalId !in localIds }
        newTransactionsToLocal.forEach { categoryRepository.insertCategory(it) }
    }
}