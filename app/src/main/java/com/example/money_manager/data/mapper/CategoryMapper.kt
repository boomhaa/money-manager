package com.example.money_manager.data.mapper

import com.example.money_manager.data.local.entity.CategoryEntity
import com.example.money_manager.domain.model.Category

fun CategoryEntity.toCategory(): Category {
    return Category(
        id = id,
        name = name,
        type = type,
        isDefault = isDefault,
        iconName = iconName
    )
}

fun Category.toCategoryEntity(): CategoryEntity {
    return CategoryEntity(
        id = id,
        name = name,
        type = type,
        isDefault = isDefault,
        iconName = iconName
    )
}

fun List<CategoryEntity>.toCategoryList(): List<Category> {
    return map { it.toCategory() }
}

fun List<Category>.toCategoryEntityList(): List<CategoryEntity> {
    return map { it.toCategoryEntity() }
}