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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.money_manager.domain.model.Category
import com.example.money_manager.presentation.components.AmountTextField
import com.example.money_manager.presentation.components.BeautifulButton
import com.example.money_manager.presentation.components.BeautifulCard
import com.example.money_manager.presentation.components.BeautifulTextField
import com.example.money_manager.presentation.components.CategoryDropdown
import com.example.money_manager.presentation.components.DatePickerField
import com.example.money_manager.presentation.components.TransactionTypeSelector
import com.example.money_manager.presentation.viewmodel.edittransactionviewmodel.EditTransactionViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTransactionScreen(
    navController: NavController,
    transactionId: Long,
    viewModel: EditTransactionViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(navController.currentBackStackEntry) {
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<Category>("selectedCategory")
            ?.observeForever { category ->
                viewModel.onCategoryChange(category)
            }
    }

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
                title = { 
                    Text(
                        "Редактировать транзакцию",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack, 
                            contentDescription = "Назад",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = MaterialTheme.colorScheme.background
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
                BeautifulCard(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = 4,
                    cornerRadius = 16
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            "Загрузка транзакции...",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else if (uiState.value.error != null) {
                BeautifulCard(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = 4,
                    cornerRadius = 16
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = uiState.value.error!!,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            } else {
                BeautifulCard(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = 4,
                    cornerRadius = 16
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Информация о транзакции",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        
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

                        BeautifulTextField(
                            value = uiState.value.description,
                            onValueChange = viewModel::onDescriptionChange,
                            label = "Описание",
                            placeholder = "Добавьте описание (необязательно)",
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                BeautifulButton(
                    text = "Сохранить изменения",
                    onClick = viewModel::updateTransaction,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = uiState.value.amount.isNotEmpty() &&
                            uiState.value.selectedCategory != null &&
                            uiState.value.transaction != null
                )
            }
        }
    }
}