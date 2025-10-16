package com.example.money_manager.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.money_manager.domain.model.TransactionWithCategory
import com.example.money_manager.presentation.theme.Success500
import com.example.money_manager.presentation.theme.Error500
import com.example.money_manager.utils.TransactionType
import com.example.money_manager.utils.toImageVector
import java.time.format.DateTimeFormatter

@SuppressLint("DefaultLocale")
@Composable
fun TransactionItem(
    item: TransactionWithCategory,
    symbol: String,
    onEdit: (Long) -> Unit,
    onDelete: (Long) -> Unit,
    transactionSymbol: String
) {
    var expanded by remember { mutableStateOf(false) }

    val transaction = item.transaction
    val category = item.category
    val formattedDate = transaction.date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))

    val isExpense = transaction.type == TransactionType.EXPENSE
    val amountColor = if (isExpense) Error500 else Success500

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(
                        if (isExpense) 
                            Error500.copy(alpha = 0.1f) 
                        else 
                            Success500.copy(alpha = 0.1f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                category.iconName?.let { icon ->
                    Icon(
                        imageVector = icon.toImageVector(),
                        contentDescription = if (isExpense) "Расход" else "Доход",
                        modifier = Modifier.size(40.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = category.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                transaction.description?.let { description ->
                    if (description.isNotBlank()) {
                        Text(
                            text = description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1
                        )
                    }
                }
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = formattedDate,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "•",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    if (symbol != transactionSymbol) {
                        Text(
                            text = "${String.format("%.2f", transaction.amount)} $transactionSymbol",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "${if (isExpense) "-" else "+"}${String.format("%.2f", transaction.addAmount)} $symbol",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = amountColor
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Box {
                    IconButton(
                        onClick = { expanded = true },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Меню",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(
                            MaterialTheme.colorScheme.surface,
                            RoundedCornerShape(12.dp)
                        )
                    ) {
                        DropdownMenuItem(
                            text = { 
                                Text(
                                    "Редактировать",
                                    style = MaterialTheme.typography.bodyMedium
                                ) 
                            },
                            onClick = {
                                expanded = false
                                onEdit(transaction.id)
                            }
                        )
                        DropdownMenuItem(
                            text = { 
                                Text(
                                    "Удалить",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.error
                                ) 
                            },
                            onClick = {
                                expanded = false
                                onDelete(transaction.id)
                            }
                        )
                    }
                }
            }
        }
    }
}
