package com.example.money_manager.domain.usecase.category

import com.example.money_manager.domain.model.Category
import com.example.money_manager.domain.repository.CategoryRepository
import com.example.money_manager.domain.usecase.FLowUseCase
import com.example.money_manager.utils.TransactionType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesByTypeUseCase @Inject constructor(
    private val repository: CategoryRepository
): FLowUseCase<TransactionType, List<Category>> {
    override suspend fun invoke(params: TransactionType): Flow<List<Category>> {
        return repository.getCategoriesByType(params)
    }
}