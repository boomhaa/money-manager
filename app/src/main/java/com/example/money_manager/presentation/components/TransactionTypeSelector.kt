package com.example.money_manager.presentation.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import com.example.money_manager.utils.TransactionType

@Composable
fun TransactionTypeSelector(
    selectedType: TransactionType,
    onTypeSelected: (TransactionType) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SegmentedButtons(
            selected = selectedType,
            onSelectionChange = onTypeSelected,
            buttons = listOf(
                TransactionType.EXPENSE to "Расход",
                TransactionType.INCOME to "Доход"
            )
        )
    }
}

@Composable
private fun SegmentedButtons(
    selected: TransactionType,
    onSelectionChange: (TransactionType) -> Unit,
    buttons: List<Pair<TransactionType, String>>
) {
    SingleChoiceSegmentedButtonRow {
        buttons.forEach { (type, label) ->
           SegmentedButton(
                selected = selected == type,
                onClick = { onSelectionChange(type) },
                shape = androidx.compose.material3.SegmentedButtonDefaults.itemShape(
                    index = buttons.indexOf(type to label),
                    count = buttons.size
                )
            ) {
                Text(label)
            }
        }
    }
}