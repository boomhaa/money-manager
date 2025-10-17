package com.example.money_manager.domain.usecase.category

import com.example.money_manager.domain.repository.CategoryRepository
import com.example.money_manager.domain.repository.TransactionRepository
import com.example.money_manager.domain.usecase.NoParamUseCase
import javax.inject.Inject

class DeleteAllCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
): NoParamUseCase<Unit> {
    override suspend fun invoke() {
        repository.deleteAllCategories()
    }
}