package com.example.money_manager.utils

import com.example.money_manager.data.local.entity.CategoryEntity

object DefaultCategories {
    fun getExpenseCategory(): List<CategoryEntity> = listOf(
        CategoryEntity(name = "Еда", type = TransactionType.EXPENSE, isDefault = true),
        CategoryEntity(name = "Транспорт", type = TransactionType.EXPENSE, isDefault = true),
        CategoryEntity(name = "Жилье", type = TransactionType.EXPENSE, isDefault = true),
        CategoryEntity(name = "Развлечение", type = TransactionType.EXPENSE, isDefault = true),
        CategoryEntity(name = "Здоровье", type = TransactionType.EXPENSE, isDefault = true),
        CategoryEntity(name = "Одежда", type = TransactionType.EXPENSE, isDefault = true),
        CategoryEntity(name = "Образование", type = TransactionType.EXPENSE, isDefault = true),
    )

    fun getIncomeCategory(): List<CategoryEntity> = listOf(
        CategoryEntity(name = "Зарплата", type = TransactionType.INCOME, isDefault = true),
        CategoryEntity(name = "Инвестиции", type = TransactionType.INCOME, isDefault = true),
        CategoryEntity(name = "Подарки", type = TransactionType.INCOME, isDefault = true),
    )

    fun getAllCategories(): List<CategoryEntity> = getExpenseCategory() + getIncomeCategory()
}