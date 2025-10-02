package com.example.money_manager.presentation.viewmodel.categoriesviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.money_manager.data.mapper.toFirebaseDto
import com.example.money_manager.domain.model.Category
import com.example.money_manager.domain.usecase.category.DeleteCategoryUseCase
import com.example.money_manager.domain.usecase.category.GetAllCategoriesUseCase
import com.example.money_manager.domain.usecase.firebase.categories.DeleteCategoryFirebaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
    private val deleteCategoryFirebaseUseCase: DeleteCategoryFirebaseUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoriesUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {
            try {
                getAllCategoriesUseCase().collect { categories ->
                    _uiState.value = _uiState.value.copy(
                        categories = categories,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Ошибка загрузки категорий",
                    isLoading = false
                )
            }
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            try {
                deleteCategoryUseCase(category)
                deleteCategoryFirebaseUseCase(category.toFirebaseDto())
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }

    }
}