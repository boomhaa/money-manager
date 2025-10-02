package com.example.money_manager.presentation.viewmodel.addtransactionviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.money_manager.data.mapper.toFirebaseDto
import com.example.money_manager.domain.model.Category
import com.example.money_manager.domain.model.Transaction
import com.example.money_manager.domain.usecase.category.GetAllCategoriesUseCase
import com.example.money_manager.domain.usecase.firebase.transactions.InsertTransactionFirebaseUseCase
import com.example.money_manager.domain.usecase.transaction.InsertTransactionUseCase
import com.example.money_manager.utils.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val insertTransactionUseCase: InsertTransactionUseCase,
    private val getCategoriesUseCase: GetAllCategoriesUseCase,
    private val insertTransactionFirebaseUseCase: InsertTransactionFirebaseUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddTransactionUiState())
    val uiState: StateFlow<AddTransactionUiState> = _uiState.asStateFlow()

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            getCategoriesUseCase().collectLatest { categories ->
                _uiState.value = _uiState.value.copy(categories = categories)
            }
        }
    }

    fun onAmountChange(amount: String) {
        _uiState.value = _uiState.value.copy(amount = amount)
    }

    fun onDescriptionChange(description: String) {
        _uiState.value = _uiState.value.copy(description = description)
    }

    fun onTransactionTypeChange(type: TransactionType) {
        _uiState.value = _uiState.value.copy(transactionType = type)
    }

    fun onCategoryChange(category: Category) {
        _uiState.value = _uiState.value.copy(selectedCategory = category)
    }

    fun onDateChange(date: LocalDateTime) {
        _uiState.value = _uiState.value.copy(date = date)
    }

    fun addTransaction() {
        viewModelScope.launch {
            try {
                val amount = _uiState.value.amount.toDoubleOrNull() ?: 0.0
                if (amount > 0 && _uiState.value.selectedCategory != null) {
                    val transaction = Transaction(
                        amount = amount,
                        globalId = UUID.randomUUID().toString(),
                        type = _uiState.value.transactionType,
                        categoryId = _uiState.value.selectedCategory!!.id,
                        date = _uiState.value.date,
                        description = _uiState.value.description.ifEmpty { null }
                    )

                    insertTransactionUseCase(transaction)
                    insertTransactionFirebaseUseCase(transaction.toFirebaseDto())
                    _uiState.value = _uiState.value.copy(isSuccess = true)
                } else {
                    _uiState.value = _uiState.value.copy(error = "Заполните все поля")
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message ?: "Ошибка добавления")
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun resetSuccess() {
        _uiState.value = _uiState.value.copy(isSuccess = false)
    }
}