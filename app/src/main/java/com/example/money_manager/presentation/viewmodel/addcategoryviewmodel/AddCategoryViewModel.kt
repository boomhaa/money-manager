package com.example.money_manager.presentation.viewmodel.addcategoryviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.money_manager.data.mapper.toFirebaseDto
import com.example.money_manager.domain.model.Category
import com.example.money_manager.domain.usecase.category.InsertCategoryUseCase
import com.example.money_manager.domain.usecase.firebase.categories.InsertCategoryFirebaseUseCase
import com.example.money_manager.utils.helpers.CategoryIcons
import com.example.money_manager.utils.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddCategoryViewModel @Inject constructor(
    private val insertCategoryUseCase: InsertCategoryUseCase,
    private val insertCategoryFirebaseUseCase: InsertCategoryFirebaseUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddCategoryUiState())
    val uiState: StateFlow<AddCategoryUiState> = _uiState.asStateFlow()

    fun onNameChange(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun onTransactionTypeChange(type: TransactionType) {
        _uiState.value = _uiState.value.copy(type = type, icon = null)
    }

    fun onIconChange(icon: CategoryIcons) {
        _uiState.value = _uiState.value.copy(icon = icon)
    }

    fun addCategory() {
        viewModelScope.launch {
            try {
                val category = Category(
                    name = _uiState.value.name,
                    globalId = UUID.randomUUID().toString(),
                    type = _uiState.value.type,
                    iconName = _uiState.value.icon
                    )
                insertCategoryUseCase(category)
                insertCategoryFirebaseUseCase(category.toFirebaseDto())
                _uiState.value = _uiState.value.copy(isSuccess = true)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message ?: "Ошибка добавления")
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}