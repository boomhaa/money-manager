package com.example.money_manager.utils

import com.example.money_manager.data.local.entity.CategoryEntity

object DefaultCategories {
    fun getExpenseCategory(): List<CategoryEntity> = listOf(
        CategoryEntity(name = "Еда", isDefault = true),
        CategoryEntity(name = "Транспорт", isDefault = true),
        CategoryEntity(name = "Жилье", isDefault = true),
        CategoryEntity(name = "Развлечение", isDefault = true),
        CategoryEntity(name = "Здоровье", isDefault = true),
        CategoryEntity(name = "Одежда", isDefault = true),
        CategoryEntity(name = "Образование", isDefault = true),
    )

    fun getIncomeCategory(): List<CategoryEntity> = listOf(
        CategoryEntity(name = "Зарплата", isDefault = true),
        CategoryEntity(name = "Инвестиции", isDefault = true),
        CategoryEntity(name = "Подарки", isDefault = true),
    )

    fun getAllCategories(): List<CategoryEntity> = getExpenseCategory() + getIncomeCategory()
}