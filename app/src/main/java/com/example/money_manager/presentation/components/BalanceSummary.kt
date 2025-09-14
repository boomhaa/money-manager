package com.example.money_manager.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.money_manager.presentation.theme.Success500
import com.example.money_manager.presentation.theme.Error500
import com.example.money_manager.presentation.theme.Primary500

@Composable
fun BalanceSummary(
    balance: Double,
    totalIncome: Double,
    totalExpense: Double,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // Balance Card
            BalanceCard(
                title = "Общий баланс",
                amount = balance,
                icon = Icons.Default.AttachMoney,
                isMain = true
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Income and Expense Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BalanceCard(
                    title = "Доходы",
                    amount = totalIncome,
                    icon = Icons.Default.KeyboardArrowUp,
                    isMain = false,
                    modifier = Modifier.weight(1f)
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                BalanceCard(
                    title = "Расходы",
                    amount = totalExpense,
                    icon = Icons.Default.KeyboardArrowDown,
                    isMain = false,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun BalanceCard(
    title: String,
    amount: Double,
    icon: ImageVector,
    isMain: Boolean,
    modifier: Modifier = Modifier
) {
    val amountColor = when {
        isMain -> if (amount >= 0) Success500 else Error500
        title == "Доходы" -> Success500
        title == "Расходы" -> Error500
        else -> MaterialTheme.colorScheme.onSurface
    }
    
    val backgroundColor = when {
        isMain -> Brush.linearGradient(
            colors = listOf(
                Primary500.copy(alpha = 0.1f),
                Primary500.copy(alpha = 0.05f)
            )
        )
        title == "Доходы" -> Brush.linearGradient(
            colors = listOf(
                Success500.copy(alpha = 0.1f),
                Success500.copy(alpha = 0.05f)
            )
        )
        title == "Расходы" -> Brush.linearGradient(
            colors = listOf(
                Error500.copy(alpha = 0.1f),
                Error500.copy(alpha = 0.05f)
            )
        )
        else -> Brush.linearGradient(
            colors = listOf(
                MaterialTheme.colorScheme.surfaceVariant,
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
        )
    }
    
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = if (isMain) Alignment.CenterHorizontally else Alignment.Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = amountColor,
                    modifier = Modifier.size(if (isMain) 24.dp else 20.dp)
                )
                Text(
                    text = title,
                    style = if (isMain) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Medium
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = formatCurrency(amount),
                style = if (isMain) MaterialTheme.typography.headlineMedium else MaterialTheme.typography.titleLarge,
                color = amountColor,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

private fun formatCurrency(amount: Double): String {
    return "%.2f ₽".format(amount)
}