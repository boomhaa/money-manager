package com.example.money_manager.utils

import com.example.money_manager.data.local.entity.CategoryEntity
import com.example.money_manager.utils.helpers.CategoryIcons
import java.util.UUID

object DefaultCategories {
    fun getExpenseCategory(): List<CategoryEntity> = listOf(
        CategoryEntity(
            name = "Еда",
            globalId = UUID.randomUUID().toString(),
            type = TransactionType.EXPENSE,
            isDefault = true,
            iconName = CategoryIcons.Restaurant
        ),
        CategoryEntity(
            name = "Транспорт",
            globalId = UUID.randomUUID().toString(),
            type = TransactionType.EXPENSE,
            isDefault = true,
            iconName = CategoryIcons.DirectionsCar
        ),
        CategoryEntity(
            name = "Жилье",
            globalId = UUID.randomUUID().toString(),
            type = TransactionType.EXPENSE,
            isDefault = true,
            iconName = CategoryIcons.Home
        ),
        CategoryEntity(
            name = "Развлечение",
            globalId = UUID.randomUUID().toString(),
            type = TransactionType.EXPENSE,
            isDefault = true,
            iconName = CategoryIcons.Movie
        ),
        CategoryEntity(
            name = "Здоровье",
            globalId = UUID.randomUUID().toString(),
            type = TransactionType.EXPENSE,
            isDefault = true,
            iconName = CategoryIcons.Favorite
        ),
        CategoryEntity(
            name = "Одежда",
            globalId = UUID.randomUUID().toString(),
            type = TransactionType.EXPENSE,
            isDefault = true,
            iconName = CategoryIcons.ShoppingBag
        ),
        CategoryEntity(
            name = "Образование",
            globalId = UUID.randomUUID().toString(),
            type = TransactionType.EXPENSE,
            isDefault = true,
            iconName = CategoryIcons.School
        ),
    )

    fun getIncomeCategory(): List<CategoryEntity> = listOf(
        CategoryEntity(
            name = "Зарплата",
            globalId = UUID.randomUUID().toString(),
            type = TransactionType.INCOME,
            isDefault = true,
            iconName = CategoryIcons.AttachMoney
        ),
        CategoryEntity(
            name = "Инвестиции",
            globalId = UUID.randomUUID().toString(),
            type = TransactionType.INCOME,
            isDefault = true,
            iconName = CategoryIcons.TrendingUp
        ),
        CategoryEntity(
            name = "Подарки",
            globalId = UUID.randomUUID().toString(),
            type = TransactionType.INCOME,
            isDefault = true,
            iconName = CategoryIcons.CardGiftcard
        ),
    )

    fun getAllCategories(): List<CategoryEntity> = getExpenseCategory() + getIncomeCategory()
}