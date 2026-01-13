package com.example.money_manager.presentation.viewmodel.addcategoryviewmodel

import com.example.money_manager.utils.helpers.CategoryIcons
import com.example.money_manager.utils.TransactionType

data class AddCategoryUiState(
    val name: String = "",
    val type: TransactionType = TransactionType.EXPENSE,
    val error: String? = null,
    val isSuccess: Boolean = false,
    val icon: CategoryIcons? = null
)
