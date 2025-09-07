package com.example.money_manager.presentation.viewmodel.statisticviewmodel

import java.time.LocalDate

data class StatisticUiState(
    val totalExpense: Double = 0.0,
    val totalIncome: Double = 0.0,
    val balance: Double = 0.0,
    val startDate: LocalDate = LocalDate.now().withDayOfMonth(1),
    val endDate: LocalDate = LocalDate.now(),
    val expenseByCategory: Map<String, Double> = emptyMap(),
    val transactionsOverTime: Map<LocalDate, Double> = emptyMap()
)
