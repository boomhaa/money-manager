package com.example.money_manager.domain.usecase.category

import com.example.money_manager.domain.model.Category
import com.example.money_manager.domain.repository.CategoryRepository
import com.example.money_manager.domain.usecase.UseCase
import javax.inject.Inject

class InsertCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository
): UseCase<Category, Unit> {
    override suspend fun invoke(params: Category) {
        repository.insertCategory(params)
    }
}