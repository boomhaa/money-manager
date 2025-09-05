package com.example.money_manager.presentation.components

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    selectedDate: LocalDateTime,
    onDateSelected: (LocalDateTime) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    )

    // Поле для отображения выбранной даты
    OutlinedTextField(
        value = selectedDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
        onValueChange = {},
        modifier = modifier,
        label = { Text("Дата транзакции") },
        readOnly = true,
        trailingIcon = {
            Button(onClick = { showDatePicker = true }) {
                Text("Выбрать")
            }
        }
    )

    // Диалог выбора даты
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                Button(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val instant = Instant.ofEpochMilli(millis)
                        val date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
                        onDateSelected(date)
                    }
                    showDatePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                Button(onClick = { showDatePicker = false }) {
                    Text("Отмена")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}