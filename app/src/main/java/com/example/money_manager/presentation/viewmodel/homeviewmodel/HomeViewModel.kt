package com.example.money_manager.presentation.viewmodel.homeviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.money_manager.data.mapper.toFirebaseDto
import com.example.money_manager.domain.model.Transaction
import com.example.money_manager.domain.model.TransactionWithCategory
import com.example.money_manager.domain.model.TransactionsSummary
import com.example.money_manager.domain.usecase.category.GetAllCategoriesUseCase
import com.example.money_manager.domain.usecase.firebase.categories.ObserveCategoriesFirebaseUseCase
import com.example.money_manager.domain.usecase.firebase.transactions.DeleteTransactionFirebaseUseCase
import com.example.money_manager.domain.usecase.firebase.transactions.ObserveTransactionsFirebaseUseCase
import com.example.money_manager.domain.usecase.firebase.utlis.RemoveCategoryListenerFirebaseUseCase
import com.example.money_manager.domain.usecase.firebase.utlis.RemoveTransactionListenerFirebaseUseCase
import com.example.money_manager.domain.usecase.transaction.DeleteTransactionUseCase
import com.example.money_manager.domain.usecase.transaction.GetAllTransactionsUseCase
import com.example.money_manager.utils.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllTransactionsUseCase: GetAllTransactionsUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val deleteTransactionUseCase: DeleteTransactionUseCase,
    private val deleteTransactionFirebaseUseCase: DeleteTransactionFirebaseUseCase,
    private val observeTransactionsFirebaseUseCase: ObserveTransactionsFirebaseUseCase,
    private val removeTransactionListenerFirebaseUseCase: RemoveTransactionListenerFirebaseUseCase,
    private val observeCategoriesFirebaseUseCase: ObserveCategoriesFirebaseUseCase,
    private val removeCategoryListenerFirebaseUseCase: RemoveCategoryListenerFirebaseUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
        observerTransactions()
        viewModelScope.launch {
            observeTransactionsFirebaseUseCase()
            observeCategoriesFirebaseUseCase()
        }
    }

    private fun observerTransactions() {
        viewModelScope.launch {
            combine(
                getAllTransactionsUseCase(),
                getAllCategoriesUseCase()
            ) { transactions, categories ->
                val transactionWithCategory = transactions.mapNotNull { transaction ->
                    val category = categories.find { it.id == transaction.categoryId }
                    category?.let { TransactionWithCategory(transaction, it) }
                }

                val totalIncome = transactions
                    .filter { it.type == TransactionType.INCOME }
                    .sumOf { it.amount }
                val totalExpense = transactions
                    .filter { it.type == TransactionType.EXPENSE }
                    .sumOf { it.amount }
                val totalBalance = totalIncome - totalExpense

                TransactionsSummary(
                    transactionWithCategory,
                    totalIncome,
                    totalExpense,
                    totalBalance
                )
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
                .collect { data ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            transactions = data.transactionsWithCategories,
                            totalIncome = data.totalIncome,
                            totalExpense = data.totalExpense,
                            balance = data.balance,
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
                deleteTransactionFirebaseUseCase(transaction.toFirebaseDto())
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        removeTransactionListenerFirebaseUseCase.execute()
        removeCategoryListenerFirebaseUseCase.execute()
    }
}