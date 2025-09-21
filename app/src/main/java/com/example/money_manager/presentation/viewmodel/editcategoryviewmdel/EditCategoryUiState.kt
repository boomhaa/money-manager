package com.example.money_manager.presentation.viewmodel.editcategoryviewmdel

import com.example.money_manager.domain.model.Category
import com.example.money_manager.utils.CategoryIcons
import com.example.money_manager.utils.TransactionType

data class EditCategoryUiState(
    val category: Category? = null,
    val name: String = "",
    val type: TransactionType = TransactionType.EXPENSE,
    val iconName: CategoryIcons? = null,
    val error: String? = null,
    val isSuccess: Boolean = false,
    val isLoading: Boolean = true
)
