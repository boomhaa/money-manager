package com.example.money_manager.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.money_manager.domain.model.TransactionWithCategory
import com.example.money_manager.utils.TransactionType
import java.time.format.DateTimeFormatter

@Composable
fun TransactionItem(
    item: TransactionWithCategory,
    onEdit: (Long) -> Unit,
    onDelete: (Long) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val transaction = item.transaction
    val category = item.category
    val formattedDate = transaction.date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true },
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = category.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    transaction.description?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        text = formattedDate,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Text(
                    text = (if (transaction.type == TransactionType.EXPENSE) "-" else "+") +
                            transaction.amount.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    color = if (transaction.type == TransactionType.EXPENSE)
                        MaterialTheme.colorScheme.error
                    else
                        MaterialTheme.colorScheme.primary
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Редактировать") },
                onClick = {
                    expanded = false
                    onEdit(transaction.id)
                }
            )
            DropdownMenuItem(
                text = { Text("Удалить") },
                onClick = {
                    expanded = false
                    onDelete(transaction.id)
                }
            )
        }
    }
}
