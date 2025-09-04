package com.example.money_manager.domain.usecase.category

import com.example.money_manager.domain.model.Category
import com.example.money_manager.domain.repository.CategoryRepository
import com.example.money_manager.domain.usecase.UseCase
import javax.inject.Inject

class GetCategoryById @Inject constructor(
    private val repository: CategoryRepository
): UseCase<Long, Category> {
    override suspend fun invoke(params: Long): Category {
        return repository.getCategoryById(params)
    }
}