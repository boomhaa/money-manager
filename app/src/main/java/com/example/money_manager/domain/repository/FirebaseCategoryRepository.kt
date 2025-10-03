package com.example.money_manager.domain.repository

import com.example.money_manager.domain.model.FirebaseCategory

interface FirebaseCategoryRepository {
    suspend fun insertCategoryFirebase(category: FirebaseCategory)
    suspend fun updateCategoryFirebase(category: FirebaseCategory)
    suspend fun deleteCategoryFirebase(category: FirebaseCategory)
    suspend fun getAllCategoriesFirebase(userId: String): List<FirebaseCategory>
    fun removeListener()
}