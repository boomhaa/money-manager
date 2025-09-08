package com.example.money_manager.presentation.viewmodel.categoriesviewmodel

import com.example.money_manager.domain.model.Category

data class CategoriesUiState(
    val categories: List<Category> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = true
)
