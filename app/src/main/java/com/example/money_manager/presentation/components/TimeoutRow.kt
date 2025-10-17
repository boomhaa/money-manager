package com.example.money_manager.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TimeoutRow(
    selected: Int,
    onSelect: (Int) -> Unit
){
    val options = listOf(0 to "Всегда", 30 to "30 сек", 60 to "1 мин", 300 to "5 мин", 600 to "10 мин")
    Column(Modifier.fillMaxWidth()) {
        options.forEach { (sec, label) ->
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                RadioButton(
                    selected = selected == sec,
                    onClick = { onSelect(sec)}
                )
                Spacer(Modifier.width(8.dp))
                Text(label)
            }
        }
    }
}