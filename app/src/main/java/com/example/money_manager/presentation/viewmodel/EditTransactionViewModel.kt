package com.example.money_manager.presentation.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.money_manager.domain.model.Category
import com.example.money_manager.utils.TransactionType
import com.example.money_manager.domain.usecase.category.GetAllCategoriesUseCase
import com.example.money_manager.domain.usecase.transaction.GetTransactionByIdUseCase
import com.example.money_manager.domain.usecase.transaction.UpdateTransactionUseCase
import com.example.money_manager.domain.model.EditTransactionUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class EditTransactionViewModel @Inject constructor(
    private val getTransactionByIdUseCase: GetTransactionByIdUseCase,
    private val updateTransactionUseCase: UpdateTransactionUseCase,
    private val getCategoriesUseCase: GetAllCategoriesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditTransactionUiState(isLoading = true))
    val uiState: StateFlow<EditTransactionUiState> = _uiState.asStateFlow()


    fun loadTransaction(transactionId: Long) {
        viewModelScope.launch {
            try {
                val categories = getCategoriesUseCase().first()
                val transaction = getTransactionByIdUseCase(transactionId)
                val category = _uiState.value.categories.find { it.id == transaction.categoryId }
                _uiState.value = _uiState.value.copy(
                    transaction = transaction,
                    amount = transaction.amount.toString(),
                    description = transaction.description ?: "",
                    transactionType = transaction.type,
                    selectedCategory = category,
                    date = transaction.date,
                    categories = categories,
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

    fun updateTransaction() {
        viewModelScope.launch {
            try {
                val currentTransaction = _uiState.value.transaction
                val amount = _uiState.value.amount.toDoubleOrNull() ?: 0.0

                if (currentTransaction != null && amount > 0 && _uiState.value.selectedCategory != null) {
                    val updatedTransaction = currentTransaction.copy(
                        amount = amount,
                        type = _uiState.value.transactionType,
                        categoryId = _uiState.value.selectedCategory!!.id,
                        date = _uiState.value.date,
                        description = _uiState.value.description.ifEmpty { null }
                    )

                    updateTransactionUseCase(updatedTransaction)
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

