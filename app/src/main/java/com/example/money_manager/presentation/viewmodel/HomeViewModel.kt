package com.example.money_manager.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.money_manager.domain.model.Transaction
import com.example.money_manager.domain.model.TransactionWithCategory
import com.example.money_manager.domain.usecase.category.GetAllCategoriesUseCase
import com.example.money_manager.domain.usecase.transaction.DeleteTransactionUseCase
import com.example.money_manager.domain.usecase.transaction.GetAllTransactionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val transactions: List<TransactionWithCategory> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllTransactionsUseCase: GetAllTransactionsUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val deleteTransactionUseCase: DeleteTransactionUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
        observerTransactions()
    }

    private fun observerTransactions() {
        viewModelScope.launch {
            combine(
                getAllTransactionsUseCase(),
                getAllCategoriesUseCase()
            ) { transactions, categories ->
                transactions.mapNotNull { transaction ->
                    val category = categories.find { it.id == transaction.categoryId }
                    category?.let { TransactionWithCategory(transaction, it) }
                }
            }
                .onStart { _uiState.update { it.copy(isLoading = true) } }
                .catch { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message
                        )
                    }
                }
                .collect { list ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            transactions = list,
                            error = null
                        )
                    }
                }
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            try {
                deleteTransactionUseCase(transaction)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

}