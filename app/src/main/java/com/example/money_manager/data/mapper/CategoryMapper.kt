package com.example.money_manager.data.mapper

import com.example.money_manager.data.local.entity.CategoryEntity
import com.example.money_manager.domain.model.Category
import com.example.money_manager.domain.model.FirebaseCategory
import com.example.money_manager.utils.helpers.CategoryIcons
import com.example.money_manager.utils.TransactionType

fun CategoryEntity.toCategory(): Category {
    return Category(
        id = id,
        globalId = globalId,
        name = name,
        type = type,
        isDefault = isDefault,
        iconName = iconName
    )
}

fun Category.toCategoryEntity(): CategoryEntity {
    return CategoryEntity(
        id = id,
        globalId = globalId,
        name = name,
        type = type,
        isDefault = isDefault,
        iconName = iconName
    )
}

fun Category.toFirebaseDto(): FirebaseCategory {
    return FirebaseCategory(
        id = id,
        globalId = globalId,
        name = name,
        type = type.name,
        isDefault = isDefault,
        iconName = iconName?.name
    )
}

fun FirebaseCategory.toDomain(): Category {
    return Category(
        id = id,
        globalId = globalId,
        name = name,
        type = TransactionType.valueOf(type),
        isDefault = isDefault,
        iconName = iconName?.let { CategoryIcons.valueOf(it) }
    )
}

fun List<CategoryEntity>.toCategoryList(): List<Category> {
    return map { it.toCategory() }
}

fun List<Category>.toCategoryEntityList(): List<CategoryEntity> {
    return map { it.toCategoryEntity() }
}