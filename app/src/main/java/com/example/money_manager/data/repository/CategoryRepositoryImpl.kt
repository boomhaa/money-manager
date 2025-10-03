package com.example.money_manager.data.repository

import com.example.money_manager.data.local.dao.CategoryDao
import com.example.money_manager.data.mapper.toCategory
import com.example.money_manager.data.mapper.toCategoryEntity
import com.example.money_manager.data.mapper.toCategoryList
import com.example.money_manager.domain.model.Category
import com.example.money_manager.domain.repository.CategoryRepository
import com.example.money_manager.utils.TransactionType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoryRepository{
    override fun getAllCategories(): Flow<List<Category>> {
        return categoryDao.getAllCategories().map { it.toCategoryList() }
    }

    override suspend fun getCategoryById(id: Long): Category {
        return categoryDao.getCategoryById(id).toCategory()
    }

    override fun getCategoriesByType(type: TransactionType): Flow<List<Category>> {
        return categoryDao.getCategoriesByType(type).map { it.toCategoryList() }
    }

    override suspend fun insertCategory(category: Category) {
        categoryDao.insertCategory(category.toCategoryEntity())
    }

    override suspend fun updateCategory(category: Category) {
        categoryDao.updateCategory(category.toCategoryEntity())
    }

    override suspend fun deleteCategory(category: Category) {
        categoryDao.deleteCategory(category.toCategoryEntity())
    }

    override suspend fun deleteAllCategories() {
        categoryDao.clearAllNonDefault()
    }
}