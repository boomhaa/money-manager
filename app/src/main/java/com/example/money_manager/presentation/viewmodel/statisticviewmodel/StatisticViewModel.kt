package com.example.money_manager.presentation.viewmodel.statisticviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.money_manager.domain.usecase.category.GetCategoryByIdUseCase
import com.example.money_manager.domain.usecase.transaction.GetAllTransactionsUseCase
import com.example.money_manager.utils.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class StatisticViewModel @Inject constructor(
    private val getAllTransactionsUseCase: GetAllTransactionsUseCase,
    private val getCategoryByIdUseCase: GetCategoryByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(StatisticUiState())
    val uiState: StateFlow<StatisticUiState> = _uiState.asStateFlow()

    init {
        loadStatistic()
    }

    fun onStartDateChange(date: LocalDate) {
        _uiState.value = _uiState.value.copy(startDate = date)
        loadStatistic()
    }

    fun onEndDateChange(date: LocalDate) {
        _uiState.value = _uiState.value.copy(endDate = date)
        loadStatistic()
    }

    fun loadStatistic() {
        viewModelScope.launch {
            getAllTransactionsUseCase().collect { transactions ->
                val income =
                    transactions.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }
                val expense =
                    transactions.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }
                val balance = income - expense

                val expenseByCategory = transactions
                    .filter { it.type == TransactionType.EXPENSE }
                    .groupBy { getCategoryByIdUseCase(it.categoryId).name }
                    .mapValues { entry -> entry.value.sumOf { it.amount } }

                val filteredTransactions = transactions
                    .filter { it.type == TransactionType.EXPENSE }
                    .filter {
                        val txDate = it.date.toLocalDate()
                        !txDate.isBefore(_uiState.value.startDate) && !txDate.isAfter(_uiState.value.endDate)
                    }

                val transactionOverTime = filteredTransactions
                    .groupBy { it.date.toLocalDate() }
                    .mapValues { entry -> entry.value.sumOf { it.amount } }

                _uiState.value = _uiState.value.copy(
                    totalIncome = income,
                    totalExpense = expense,
                    balance = balance,
                    expenseByCategory = expenseByCategory.mapKeys { it.key.toString() },
                    transactionsOverTime = transactionOverTime
                )
            }
        }
    }
}