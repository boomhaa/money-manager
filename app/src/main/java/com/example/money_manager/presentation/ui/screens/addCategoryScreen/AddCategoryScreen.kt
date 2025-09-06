package com.example.money_manager.presentation.ui.screens.addCategoryScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.money_manager.presentation.components.TransactionTypeSelector
import com.example.money_manager.presentation.viewmodel.addcategoryviewmodel.AddCategoryViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCategoryScreen(
    navController: NavController,
    addCategoryViewModel: AddCategoryViewModel = hiltViewModel()
) {
    val uiState = addCategoryViewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(uiState.value.isSuccess) {
        if (uiState.value.isSuccess) {
            scope.launch {
                navController.popBackStack()
            }
        }
    }

    LaunchedEffect(uiState.value.error) {
        uiState.value.error?.let { error ->
            scope.launch {
                snackbarHostState.showSnackbar(error)
                addCategoryViewModel.clearError()
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Добавить категорию") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = uiState.value.name,
                onValueChange = addCategoryViewModel::onNameChange,
                label = { Text("Название категории") },
                modifier = Modifier.fillMaxWidth()
            )

            TransactionTypeSelector(
                selectedType = uiState.value.type,
                onTypeSelected = addCategoryViewModel::onTransactionTypeChange,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = addCategoryViewModel::addCategory,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                enabled = uiState.value.name.isNotEmpty()
            ) {
                Text("Сохранить")
            }

        }
    }

}