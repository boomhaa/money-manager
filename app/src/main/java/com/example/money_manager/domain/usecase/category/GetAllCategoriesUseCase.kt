package com.example.money_manager.domain.usecase.category

import com.example.money_manager.domain.model.Category
import com.example.money_manager.domain.repository.CategoryRepository
import com.example.money_manager.domain.usecase.NoParamFlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
): NoParamFlowUseCase<List<Category>> {
    override suspend fun invoke(): Flow<List<Category>> {
        return repository.getAllCategories()
    }
}