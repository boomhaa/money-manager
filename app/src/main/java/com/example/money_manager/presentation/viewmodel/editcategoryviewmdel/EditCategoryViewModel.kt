package com.example.money_manager.presentation.viewmodel.editcategoryviewmdel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.money_manager.data.mapper.toFirebaseDto
import com.example.money_manager.domain.usecase.category.GetCategoryByIdUseCase
import com.example.money_manager.domain.usecase.category.UpdateCategoryUseCase
import com.example.money_manager.domain.usecase.firebase.categories.UpdateCategoryFirebaseUseCase
import com.example.money_manager.utils.CategoryIcons
import com.example.money_manager.utils.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditCategoryViewModel @Inject constructor(
    private val getCategoryByIdUseCase: GetCategoryByIdUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase,
    private val updateCategoryFirebaseUseCase: UpdateCategoryFirebaseUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(EditCategoryUiState())
    val uiState: StateFlow<EditCategoryUiState> = _uiState.asStateFlow()

    fun loadCategory(categoryId: Long) {
        viewModelScope.launch {
            try {
                val category = getCategoryByIdUseCase(categoryId)
                _uiState.value = _uiState.value.copy(
                    category = category,
                    name = category.name,
                    type = category.type,
                    iconName = category.iconName,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Ошибка загрузки",
                    isLoading = false
                )
            }
        }
    }

    fun onNameChange(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun onTypeChange(type: TransactionType) {
        _uiState.value = _uiState.value.copy(type = type)
    }

    fun onIconChange(icon: CategoryIcons) {
        _uiState.value = _uiState.value.copy(iconName = icon)
    }

    fun updateCategory() {
        viewModelScope.launch {
            try {
                val currentCategory = _uiState.value.category

                if (currentCategory != null && _uiState.value.name != "") {
                    val updatedCategory = currentCategory.copy(
                        name = _uiState.value.name,
                        type = _uiState.value.type,
                        iconName = _uiState.value.iconName
                    )
                    updateCategoryUseCase(updatedCategory)
                    updateCategoryFirebaseUseCase(updatedCategory.toFirebaseDto())
                    _uiState.value = _uiState.value.copy(isSuccess = true)
                } else {
                    _uiState.value = _uiState.value.copy(error = "Заполните все обязательные поля")
                }

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message ?: "Ошибка обновления")
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}