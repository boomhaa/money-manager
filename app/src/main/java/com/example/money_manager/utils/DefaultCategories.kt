package com.example.money_manager.utils

import com.example.money_manager.data.local.entity.CategoryEntity

object DefaultCategories {
    fun getExpenseCategory(): List<CategoryEntity> = listOf(
        CategoryEntity(name = "Еда", type = TransactionType.EXPENSE, isDefault = true, iconName = CategoryIcons.Restaurant),
        CategoryEntity(name = "Транспорт", type = TransactionType.EXPENSE, isDefault = true, iconName = CategoryIcons.DirectionsCar),
        CategoryEntity(name = "Жилье", type = TransactionType.EXPENSE, isDefault = true, iconName = CategoryIcons.Home),
        CategoryEntity(name = "Развлечение", type = TransactionType.EXPENSE, isDefault = true, iconName = CategoryIcons.Movie),
        CategoryEntity(name = "Здоровье", type = TransactionType.EXPENSE, isDefault = true, iconName = CategoryIcons.Favorite),
        CategoryEntity(name = "Одежда", type = TransactionType.EXPENSE, isDefault = true, iconName = CategoryIcons.ShoppingBag),
        CategoryEntity(name = "Образование", type = TransactionType.EXPENSE, isDefault = true, iconName = CategoryIcons.School),
    )

    fun getIncomeCategory(): List<CategoryEntity> = listOf(
        CategoryEntity(name = "Зарплата", type = TransactionType.INCOME, isDefault = true, iconName = CategoryIcons.AttachMoney),
        CategoryEntity(name = "Инвестиции", type = TransactionType.INCOME, isDefault = true, iconName = CategoryIcons.TrendingUp),
        CategoryEntity(name = "Подарки", type = TransactionType.INCOME, isDefault = true, iconName = CategoryIcons.CardGiftcard),
    )

    fun getAllCategories(): List<CategoryEntity> = getExpenseCategory() + getIncomeCategory()
}