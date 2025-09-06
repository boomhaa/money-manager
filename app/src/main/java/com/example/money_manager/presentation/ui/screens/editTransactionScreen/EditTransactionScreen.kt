package com.example.money_manager.presentation.ui.screens.editTransactionScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.money_manager.presentation.components.AmountTextField
import com.example.money_manager.presentation.components.CategoryDropdown
import com.example.money_manager.presentation.components.DatePickerField
import com.example.money_manager.presentation.components.TransactionTypeSelector
import com.example.money_manager.presentation.viewmodel.edittransactionviewmodel.EditTransactionViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTransactionScreen(
    navController: NavHostController,
    transactionId: Long,
    viewModel: EditTransactionViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(transactionId) {
        if (uiState.value.isLoading) {
            viewModel.loadTransaction(transactionId)
        }
    }

    LaunchedEffect(uiState.value.isSuccess) {
        if (uiState.value.isSuccess) {
            scope.launch {
                navController.popBackStack()
            }
        }
    }

    LaunchedEffect(uiState.value.error) {
        uiState.value.error?.let { error ->
            viewModel.clearError()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Редактировать транзакцию") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (uiState.value.isLoading) {
                Text("Загрузка...")
            } else if (uiState.value.error != null) {
                Text(
                    text = uiState.value.error!!,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                TransactionTypeSelector(
                    selectedType = uiState.value.transactionType,
                    onTypeSelected = viewModel::onTransactionTypeChange,
                    modifier = Modifier.fillMaxWidth()
                )

                AmountTextField(
                    value = uiState.value.amount,
                    onValueChange = viewModel::onAmountChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = "Сумма"
                )

                CategoryDropdown(
                    categories = uiState.value.categories,
                    selectedCategory = uiState.value.selectedCategory,
                    onCategorySelected = viewModel::onCategoryChange,
                    modifier = Modifier.fillMaxWidth(),
                    transactionType = uiState.value.transactionType,
                    navController = navController
                )

                DatePickerField(
                    selectedDate = uiState.value.date,
                    onDateSelected = viewModel::onDateChange,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = uiState.value.description,
                    onValueChange = viewModel::onDescriptionChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Описание") },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = viewModel::updateTransaction,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    enabled = uiState.value.amount.isNotEmpty() &&
                            uiState.value.selectedCategory != null &&
                            uiState.value.transaction != null
                ) {
                    Text("Сохранить изменения")
                }
            }
        }
    }
}