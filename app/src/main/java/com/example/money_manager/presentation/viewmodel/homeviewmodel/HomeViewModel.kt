package com.example.money_manager.presentation.viewmodel.homeviewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.money_manager.data.mapper.toFirebaseDto
import com.example.money_manager.domain.model.Transaction
import com.example.money_manager.domain.model.TransactionWithCategory
import com.example.money_manager.domain.model.TransactionsSummary
import com.example.money_manager.domain.usecase.category.GetAllCategoriesUseCase
import com.example.money_manager.domain.usecase.currency.ConvertCurrencyUseCase
import com.example.money_manager.domain.usecase.firebase.transactions.DeleteTransactionFirebaseUseCase
import com.example.money_manager.domain.usecase.firebase.utlis.RemoveCategoryListenerFirebaseUseCase
import com.example.money_manager.domain.usecase.firebase.utlis.RemoveTransactionListenerFirebaseUseCase
import com.example.money_manager.domain.usecase.transaction.DeleteTransactionUseCase
import com.example.money_manager.domain.usecase.transaction.GetAllTransactionsUseCase
import com.example.money_manager.utils.PreferencesManager
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
    private val removeTransactionListenerFirebaseUseCase: RemoveTransactionListenerFirebaseUseCase,
    private val removeCategoryListenerFirebaseUseCase: RemoveCategoryListenerFirebaseUseCase,
    private val convertCurrencyUseCase: ConvertCurrencyUseCase,
    private val prefs: PreferencesManager
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
                getAllCategoriesUseCase(),
                prefs.selectedCurrencyFlow
            ) { transactions, categories, selectedCurrency ->
                val transactionWithCategory = transactions.mapNotNull { transaction ->
                    val newAmount = try {
                        val result = convertCurrencyUseCase(
                            Triple(
                                transaction.currencyCode,
                                selectedCurrency,
                                transaction.amount
                            )
                        )
                        Log.d("HomeViewModel", result.getOrElse { it.message }.toString())
                        result.getOrElse { transaction.amount }
                    }catch (e: Exception){
                        _uiState.value = _uiState.value.copy(error = e.message)
                        transaction.amount
                    }
                    val category = categories.find { it.id == transaction.categoryId }
                    category?.let { TransactionWithCategory(transaction.copy(amount = newAmount), it) }
                }


                val totalIncome = transactionWithCategory
                    .filter { it.transaction.type == TransactionType.INCOME }
                    .sumOf { it.transaction.amount }

                val totalExpense = transactionWithCategory
                    .filter { it.transaction.type == TransactionType.EXPENSE }
                    .sumOf { it.transaction.amount }

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

    fun getSymbol(): String{
        return prefs.currency.symbol
    }

    override fun onCleared() {
        super.onCleared()
        removeTransactionListenerFirebaseUseCase.execute()
        removeCategoryListenerFirebaseUseCase.execute()
    }
}