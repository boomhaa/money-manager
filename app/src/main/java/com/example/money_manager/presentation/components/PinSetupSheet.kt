package com.example.money_manager.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.money_manager.utils.PinStage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PinSetupSheet(
    stage: PinStage,
    pinLength: Int,
    error: String?,
    busy: Boolean,
    onDigit: (Int) -> Unit,
    onBackspace: () -> Unit,
    onClearAll: () -> Unit,
    onDismiss: () -> Unit
) {
    val title = remember(stage) {
        when (stage) {
            PinStage.EnterCurrent -> "Введите текущий PIN"
            PinStage.EnterNew -> "Задайте новый PIN"
            PinStage.ConfirmNew -> "Подтвердить PIN"
            else -> ""
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(12.dp))

            PinDots(count = pinLength, total = 4)

            if (!error.isNullOrEmpty()){
                Spacer(Modifier.height(8.dp))
                Text(error, color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(16.dp))
            NumberPad(
                onDigit = {if (!busy) onDigit(it)},
                onBackspace = {if (!busy) onBackspace()},
                onLongBackspace = {if (!busy) onClearAll()},
            )

            Spacer(Modifier.height(20.dp))
        }
    }
}