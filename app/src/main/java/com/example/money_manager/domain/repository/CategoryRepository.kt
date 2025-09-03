package com.example.money_manager.domain.repository

import com.example.money_manager.domain.model.Category
import com.example.money_manager.utils.TransactionType
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getAllCategories(): Flow<List<Category>>
    suspend fun getCategoryById(id: Long): Category
    suspend fun getCategoriesByType(type: TransactionType): Flow<List<Category>>
    suspend fun insertCategory(category: Category)
    suspend fun updateCategory(category: Category)
    suspend fun deleteCategory(category: Category)
}