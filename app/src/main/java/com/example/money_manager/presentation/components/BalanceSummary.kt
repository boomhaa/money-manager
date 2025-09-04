package com.example.money_manager.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun BalanceSummary(
    balance: Double,
    totalIncome: Double,
    totalExpense: Double,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Баланс", style = MaterialTheme.typography.bodySmall)
            Text(
                "%.2f ₽".format(balance),
                color = if (balance >= 0) Color(0xFF2E7D32) else MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.titleLarge
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Доходы", style = MaterialTheme.typography.bodySmall)
            Text(
                "%.2f ₽".format(totalIncome),
                color = Color(0xFF2E7D32),
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Расходы", style = MaterialTheme.typography.bodySmall)
            Text(
                "%.2f ₽".format(totalExpense),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}